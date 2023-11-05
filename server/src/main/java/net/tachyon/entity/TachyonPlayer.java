package net.tachyon.entity;

import com.google.common.collect.Queues;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.attribute.Attribute;
import net.tachyon.collision.BoundingBox;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.effects.Effects;
import net.tachyon.entity.damage.DamageType;
import net.tachyon.entity.vehicle.PlayerVehicleInformation;
import net.tachyon.event.inventory.InventoryOpenEvent;
import net.tachyon.event.item.ItemDropEvent;
import net.tachyon.event.item.ItemUpdateStateEvent;
import net.tachyon.event.item.PickupExperienceEvent;
import net.tachyon.event.player.*;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.instance.Instance;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.item.ItemStack;
import net.tachyon.item.Material;
import net.tachyon.item.metadata.WrittenBookMeta;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.ConnectionState;
import net.tachyon.network.PlayerProvider;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.network.packet.client.play.ClientChatMessagePacket;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.login.LoginDisconnectPacket;
import net.tachyon.network.packet.server.play.*;
import net.tachyon.network.player.NettyPlayerConnection;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.resourcepack.ResourcePack;
import net.tachyon.scoreboard.BelowNameTag;
import net.tachyon.scoreboard.Team;
import net.tachyon.sound.SoundEvent;
import net.tachyon.stat.PlayerStatistic;
import net.tachyon.utils.*;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import net.tachyon.utils.OptionalCallback;
import net.tachyon.world.chunk.ChunkCallback;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.utils.entity.EntityUtils;
import net.tachyon.utils.instance.InstanceUtils;
import net.tachyon.utils.time.CooldownUtils;
import net.tachyon.utils.time.TimeUnit;
import net.tachyon.utils.time.UpdateOption;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.DimensionType;
import net.tachyon.world.LevelType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Those are the major actors of the server,
 * <p>
 * You can easily create your own implementation of this and use it with {@link ConnectionManager#setPlayerProvider(PlayerProvider)}.
 */
public class TachyonPlayer extends TachyonLivingEntity implements Player {

    private long lastKeepAliveTime;
    private int lastKeepAlive;
    private boolean answerKeepAlive;

    private String username;
    protected final PlayerConnection playerConnection;
    // All the entities that this player can see
    protected final Set<TachyonEntity> viewableEntities = new CopyOnWriteArraySet<>();

    private int latency;
    private Component displayName;
    private PlayerSkin skin;

    private DimensionType dimensionType;
    private LevelType levelType;
    private GameMode gameMode;
    // Chunks that the player can view
    protected final Set<TachyonChunk> viewableChunks = new CopyOnWriteArraySet<>();

    private final Queue<ClientPlayPacket> packets = Queues.newConcurrentLinkedQueue();
    private final PlayerSettings settings;
    private float exp;
    private int level;

    protected PlayerInventory inventory;
    private Inventory openInventory;
    // Used internally to allow the closing of inventory within the inventory listener
    private boolean didCloseInventory;

    private byte heldSlot;

    private Position respawnPoint;

    private int food;
    private float foodSaturation;
    private long startEatingTime;
    private long defaultEatingTime = 1000L;
    private long eatingTime;
    private boolean isEating;

    // Position synchronization with viewers
    private long lastPlayerSynchronizationTime;
    private double lastPlayerSyncX, lastPlayerSyncY, lastPlayerSyncZ;
    private float lastPlayerSyncYaw, lastPlayerSyncPitch;

    // Experience orb pickup
    protected UpdateOption experiencePickupCooldown = new UpdateOption(10, TimeUnit.TICK);
    private long lastExperiencePickupCheckTime;

    private BelowNameTag belowNameTag;

    private int permissionLevel;

    private boolean reducedDebugScreenInformation;

    // Abilities
    private boolean flying;
    private boolean allowFlying;
    private boolean instantBreak;
    private float flyingSpeed = 0.05f;
    private float fieldViewModifier = 0.1f;

    // Statistics
    private final Map<PlayerStatistic, Integer> statisticValueMap = new Hashtable<>();

    // Vehicle
    private final PlayerVehicleInformation vehicleInformation = new PlayerVehicleInformation();

    // Tick related
    private final PlayerTickEvent playerTickEvent = new PlayerTickEvent(this);

    public TachyonPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(EntityType.PLAYER, uuid);
        this.username = username;
        this.playerConnection = playerConnection;

        setBoundingBox(0.6f, 1.8f, 0.6f);

        setRespawnPoint(new Position(0, 0, 0));

        this.settings = new PlayerSettings();
        this.inventory = new PlayerInventory(this);

        setCanPickupItems(true); // By default

        // Allow the server to send the next keep alive packet
        refreshAnswerKeepAlive(true);

        this.gameMode = GameMode.SURVIVAL;
        this.dimensionType = DimensionType.OVERWORLD; // Default dimension
        this.levelType = LevelType.DEFAULT; // Default level type
        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1f);

        // FakePlayer init its connection there
        playerConnectionInit();
    }

    /**
     * Used when the player is created.
     * Init the player and spawn him.
     * <p>
     * WARNING: executed in the main update thread
     * UNSAFE: Only meant to be used when a netty player connects through the server.
     *
     * @param spawnInstance the player spawn instance (defined in {@link PlayerLoginEvent})
     */
    public void UNSAFE_init(@NotNull Instance spawnInstance) {
        this.dimensionType = spawnInstance.getDimensionType();
        this.levelType = spawnInstance.getLevelType();

        JoinGamePacket joinGamePacket = new JoinGamePacket(getEntityId(), gameMode, dimensionType, Tachyon.getServer().getDifficulty(), 0, levelType, false);
        playerConnection.sendPacket(joinGamePacket);

        // Server brand name
        {
            playerConnection.sendPacket(PluginMessagePacket.getBrandPacket(new TachyonBinaryWriter(4 + Tachyon.getServer().getBrandName().length())));
        }

        ServerDifficultyPacket serverDifficultyPacket = new ServerDifficultyPacket();
        serverDifficultyPacket.difficulty = Tachyon.getServer().getDifficulty();
        playerConnection.sendPacket(serverDifficultyPacket);

        SpawnPositionPacket spawnPositionPacket = new SpawnPositionPacket();
        spawnPositionPacket.x = (int) respawnPoint.getX();
        spawnPositionPacket.y = (int) respawnPoint.getY();
        spawnPositionPacket.z = (int) respawnPoint.getZ();
        playerConnection.sendPacket(spawnPositionPacket);

        refreshAbilities(); // Send abilities packet

        // Add player to list with spawning skin
        PlayerSkinInitEvent skinInitEvent = new PlayerSkinInitEvent(this, skin);
        callEvent(PlayerSkinInitEvent.class, skinInitEvent);
        this.skin = skinInitEvent.getSkin();
        // FIXME: when using Geyser, this line remove the skin of the client
        playerConnection.sendPacket(getAddPlayerToList());

        // Some client update
        this.playerConnection.sendPacket(getPropertiesPacket()); // Send default properties
        refreshHealth(); // Heal and send health packet
        getInventory().update();
    }

    /**
     * Used to initialize the player connection
     */
    protected void playerConnectionInit() {
        ((NettyPlayerConnection)this.playerConnection).setPlayer(this);
    }

    @Override
    public void update(long time) {
        // Network tick
        this.playerConnection.update();

        // Process received packets
        ClientPlayPacket packet;
        while ((packet = packets.poll()) != null) {
            packet.process(this);
        }

        super.update(time); // Super update (item pickup/fire management)

        // Experience orb pickup
        if (!CooldownUtils.hasCooldown(time, lastExperiencePickupCheckTime, experiencePickupCooldown)) {
            this.lastExperiencePickupCheckTime = time;

            final TachyonChunk chunk = getChunk(); // TODO check surrounding chunks
            final Set<TachyonEntity> entities = instance.getChunkEntities(chunk);
            for (TachyonEntity entity : entities) {
                if (entity instanceof ExperienceOrb) {
                    final ExperienceOrb experienceOrb = (ExperienceOrb) entity;
                    final BoundingBox itemBoundingBox = experienceOrb.getBoundingBox();
                    if (expandedBoundingBox.intersect(itemBoundingBox)) {
                        if (experienceOrb.shouldRemove() || experienceOrb.isRemoveScheduled())
                            continue;
                        PickupExperienceEvent pickupExperienceEvent = new PickupExperienceEvent(experienceOrb);
                        callCancellableEvent(PickupExperienceEvent.class, pickupExperienceEvent, () -> {
                            short experienceCount = pickupExperienceEvent.getExperienceCount(); // TODO give to player
                            entity.remove();
                        });
                    }
                }
            }
        }

        // Eating animation
        if (isEating()) {
            if (time - startEatingTime >= eatingTime) {
                refreshEating(false);

                triggerStatus((byte) 9); // Mark item use as finished
                ItemUpdateStateEvent itemUpdateStateEvent = callItemUpdateStateEvent(true);

                Check.notNull(itemUpdateStateEvent, "#callItemUpdateStateEvent returned null.");

                final ItemStack foodItem = itemUpdateStateEvent.getItemStack();
                final boolean isFood = foodItem.material().isFood();

                if (isFood) {
                    PlayerEatEvent playerEatEvent = new PlayerEatEvent(this, foodItem);
                    callEvent(PlayerEatEvent.class, playerEatEvent);
                }
            }
        }

        // Tick event
        callEvent(PlayerTickEvent.class, playerTickEvent);

        // Multiplayer sync
        if (!viewers.isEmpty()) {
            this.lastPlayerSynchronizationTime = time;

            final boolean positionChanged = position.getX() != lastPlayerSyncX ||
                    position.getY() != lastPlayerSyncY ||
                    position.getZ() != lastPlayerSyncZ;
            final boolean viewChanged = position.getYaw() != lastPlayerSyncYaw ||
                    position.getPitch() != lastPlayerSyncPitch;

            if (positionChanged || viewChanged) {
                // Player moved since last time

                ServerPacket updatePacket;
                ServerPacket optionalUpdatePacket = null;
                if (positionChanged && viewChanged) {
                    updatePacket = EntityLookAndRelativeMove.getPacket(getEntityId(),
                            position, new Position(lastPlayerSyncX, lastPlayerSyncY, lastPlayerSyncZ), onGround);
                } else if (positionChanged) {
                    updatePacket = EntityRelativeMovePacket.getPacket(getEntityId(),
                            position, new Position(lastPlayerSyncX, lastPlayerSyncY, lastPlayerSyncZ), onGround);
                } else {
                    // View changed
                    updatePacket = new EntityLookPacket(getEntityId(),
                            position.getYaw(), position.getPitch(), onGround);
                }

                if (viewChanged) {
                    // Yaw from the rotation packet seems to be ignored, which is why this is required
                    EntityHeadLookPacket entityHeadLookPacket = new EntityHeadLookPacket(getEntityId(), position.yaw());
                    optionalUpdatePacket = entityHeadLookPacket;
                }

                // Send the update packet
                if (optionalUpdatePacket != null) {
                    sendPacketsToViewers(updatePacket, optionalUpdatePacket);
                } else {
                    sendPacketToViewers(updatePacket);
                }

            }

            // Update sync data
            if (positionChanged) {
                lastPlayerSyncX = position.getX();
                lastPlayerSyncY = position.getY();
                lastPlayerSyncZ = position.getZ();
            }
            if (viewChanged) {
                lastPlayerSyncYaw = position.getYaw();
                lastPlayerSyncPitch = position.getPitch();
            }
        }

    }

    @Override
    public void kill() {
        if (!isDead()) {

            String deathText;
            Component chatMessage;

            // get death screen text to the killed player
            {
                if (lastDamageSource != null) {
                    deathText = lastDamageSource.buildDeathScreenText(this);
                } else { // may happen if killed by the server without applying damage
                    deathText = "Killed by poor programming.";
                }
            }

            // get death message to chat
            {
                if (lastDamageSource != null) {
                    chatMessage = lastDamageSource.buildDeathMessage(this);
                } else { // may happen if killed by the server without applying damage
                    chatMessage = Component.text(getUsername() + " was killed by poor programming.");
                }
            }

            // Call player death event
            PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(this, deathText, chatMessage);
            callEvent(PlayerDeathEvent.class, playerDeathEvent);

            deathText = playerDeathEvent.getDeathText();
            chatMessage = playerDeathEvent.getChatMessage();

            // #buildDeathScreenText can return null, check here
            if (deathText != null) {
                CombatEventPacket deathPacket = CombatEventPacket.death(this, null, deathText);
                playerConnection.sendPacket(deathPacket);
            }

            // #buildDeathMessage can return null, check here
            if (chatMessage != null) {
                Tachyon.getServer().getConnectionManager().sendMessage(chatMessage, MessageType.SYSTEM);
            }

        }
        super.kill();
    }

    /**
     * Respawns the player by sending a {@link RespawnPacket} to the player and teleporting him
     * to {@link #getRespawnPoint()}. It also resets fire and health.
     */
    public void respawn() {
        if (!isDead())
            return;

        setFireForDuration(0);
        setOnFire(false);
        refreshHealth();
        RespawnPacket respawnPacket = new RespawnPacket();
        respawnPacket.dimensionType = getDimensionType();
        respawnPacket.difficulty = Tachyon.getServer().getDifficulty();
        respawnPacket.levelType = getLevelType();
        respawnPacket.gameMode = getGameMode();
        getPlayerConnection().sendPacket(respawnPacket);
        PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(this);
        callEvent(PlayerRespawnEvent.class, respawnEvent);
        refreshIsDead(false);

        // Runnable called when teleportation is successful (after loading and sending necessary chunk)
        teleport(respawnEvent.getRespawnPosition(), this::refreshAfterTeleport);
    }

    @Override
    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void remove() {
        callEvent(PlayerDisconnectEvent.class, new PlayerDisconnectEvent(this));

        super.remove();
        this.packets.clear();
        if (getOpenInventory() != null)
            getOpenInventory().removeViewer(this);

//        // Item ownership cache
//        {
//            ItemStack[] itemStacks = inventory.getItemStacks();
//            for (ItemStack itemStack : itemStacks) {
//                TachyonItemStack.DATA_OWNERSHIP.clearCache(itemStack.getIdentifier());
//            }
//        }

        // Clear all viewable entities
        this.viewableEntities.forEach(entity -> entity.removeViewer(this));
        // Clear all viewable chunks
        this.viewableChunks.forEach(chunk -> {
            if (chunk.isLoaded())
                chunk.removeViewer(this);
        });
        playerConnection.disconnect();
    }

    @Override
    public boolean addViewer0(@NotNull Player p) {
        TachyonPlayer player = (TachyonPlayer) p;
        if (player == this || !this.viewers.add(player)) {
            return false;
        }
        player.viewableEntities.add(this);

        PlayerConnection viewerConnection = player.getPlayerConnection();
        showPlayer(viewerConnection);
        return true;
    }

    @Override
    public boolean removeViewer0(@NotNull Player p) {
        TachyonPlayer player = (TachyonPlayer) p;
        if (player == this || !super.removeViewer0(player)) {
            return false;
        }

        PlayerConnection viewerConnection = player.getPlayerConnection();
        viewerConnection.sendPacket(getRemovePlayerToList());

        // Team
        if (this.getTeam() != null && this.getTeam().getMembers().size() == 1) {// If team only contains "this" player
            viewerConnection.sendPacket(this.getTeam().createTeamDestructionPacket());
        }
        return true;
    }

    public boolean addViewableEntity(@NotNull TachyonEntity entity) {
        return this.viewableEntities.add(entity);
    }

    /**
     * Changes the player instance and load surrounding chunks if needed.
     * <p>
     * Be aware that because chunk operations are expensive,
     * it is possible for this method to be non-blocking when retrieving chunks is required.
     *
     * @param instance      the new player instance
     * @param spawnPosition the new position of the player
     */
    @Override
    public void setInstance(@NotNull Instance instance, @NotNull Position spawnPosition) {
        Check.argCondition(this.instance == instance, "Instance should be different than the current one");

        // true if the chunks need to be sent to the client, can be false if the instances share the same chunks (eg SharedInstance)
        final boolean needWorldRefresh = !InstanceUtils.areLinked(this.instance, instance) ||
                !spawnPosition.inSameChunk(this.position);

        if (needWorldRefresh) {
            final boolean firstSpawn = this.instance == null; // TODO: Handle player reconnections, must be false in that case too

            // Send the new dimension if player isn't in any instance or if the dimension is different
            final DimensionType instanceDimensionType = instance.getDimensionType();
            final boolean dimensionChange = dimensionType != instanceDimensionType;
            if (dimensionChange) {
                sendDimension(instanceDimensionType, instance.getLevelType());
            } else if (!firstSpawn) {
                DimensionType changeDimensionType = DimensionType.OVERWORLD;
                if (dimensionType == DimensionType.OVERWORLD) {
                    changeDimensionType = DimensionType.NETHER;
                }

                // It's 1.8, so to respawn we need to switch between dimensions twice
                // to make sure everything works after a dimension change.
                sendDimension(changeDimensionType, LevelType.DEFAULT);
                sendDimension(instanceDimensionType, instance.getLevelType());
            }

            // Load all the required chunks
            final long[] visibleChunks = ChunkUtils.getChunksInRange(spawnPosition, getChunkRange());

            final ChunkCallback endCallback = chunk -> {
                // This is the last chunk to be loaded , spawn player
                spawnPlayer(instance, spawnPosition, firstSpawn, true, true);
            };

            // TachyonChunk 0;0 always needs to be loaded
            instance.loadChunk(0, 0, chunk ->
                    // Load all the required chunks
                    ChunkUtils.optionalLoadAll(instance, visibleChunks, null, endCallback));

        } else {
            // The player already has the good version of all the chunks.
            // We just need to refresh his entity viewing list and add him to the instance
            spawnPlayer(instance, spawnPosition, false, false, false);
        }
    }

    /**
     * Changes the player instance without changing its position (defaulted to {@link #getRespawnPoint()}
     * if the player is not in any instance).
     *
     * @param instance the new player instance
     * @see #setInstance(Instance, Position)
     */
    @Override
    public void setInstance(@NotNull Instance instance) {
        setInstance(instance, this.instance != null ? getPosition() : getRespawnPoint());
    }

    /**
     * Used to spawn the player once the client has all the required chunks.
     * <p>
     * Does add the player to {@code instance}, remove all viewable entities and call {@link PlayerSpawnEvent}.
     * <p>
     * UNSAFE: only called with {@link #setInstance(Instance, Position)}.
     *
     * @param spawnPosition the position to teleport the player
     * @param firstSpawn    true if this is the player first spawn
     */
    private void spawnPlayer(@NotNull Instance instance, @NotNull Position spawnPosition,
                             boolean firstSpawn, boolean updateChunks, boolean dimensionChange) {
        // Clear previous instance elements
        if (!firstSpawn) {
            this.viewableChunks.forEach(chunk -> chunk.removeViewer(this));
            this.viewableEntities.forEach(entity -> entity.removeViewer(this));
        }

        super.setInstance(instance, spawnPosition);

        if (!position.isSimilar(spawnPosition) && !firstSpawn) {
            // Player changed instance at a different position
            teleport(spawnPosition);
        } else if (updateChunks) {
            // Send newly visible chunks to player once spawned in the instance
            refreshVisibleChunks();
        }

        instance.getWorldBorder().init(this);

        // Send all visible entities
        EntityUtils.forEachRange(instance, position, Tachyon.getServer().getEntityViewDistance(), ent -> {
            if (ent.isAutoViewable())
                ent.addViewer(this);
        });

        if (dimensionChange || firstSpawn) {
            updatePlayerPosition(); // So the player doesn't get stuck
            this.inventory.update();
        }

        PlayerSpawnEvent spawnEvent = new PlayerSpawnEvent(this, instance, firstSpawn);
        callEvent(PlayerSpawnEvent.class, spawnEvent);
    }

    /**
     * Sends a plugin message to the player.
     *
     * @param channel the message channel
     * @param data    the message data
     */
    public void sendPluginMessage(@NotNull String channel, @NotNull byte[] data) {
        playerConnection.sendPacket(new PluginMessagePacket(channel, data));
    }

    /**
     * Sends a plugin message to the player.
     * <p>
     * Message encoded to UTF-8.
     *
     * @param channel the message channel
     * @param message the message
     */
    public void sendPluginMessage(@NotNull String channel, @NotNull String message) {
        final byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        sendPluginMessage(channel, bytes);
    }

    @Override
    public void sendMessage(@NonNull Identity source, @NonNull Component message, @NonNull MessageType type) {
        ChatMessagePacket chatMessagePacket = new ChatMessagePacket(message, type == MessageType.CHAT ? ChatMessagePacket.Position.CHAT : ChatMessagePacket.Position.SYSTEM_MESSAGE);
        playerConnection.sendPacket(chatMessagePacket);
    }

    @Override
    public void playSound(net.kyori.adventure.sound.@NonNull Sound sound, double x, double y, double z) {
        SoundEffectPacket soundEffectPacket = new SoundEffectPacket();
        soundEffectPacket.soundName = sound.name().value();
        soundEffectPacket.position = new Position(x, y, z);
        soundEffectPacket.volume = sound.volume();
        soundEffectPacket.pitch = sound.pitch();
        playerConnection.sendPacket(soundEffectPacket);
    }

    /**
     * Makes the player send a message (can be used for commands).
     *
     * @param message the message that the player will send
     */
    public void chat(@NotNull String message) {
        ClientChatMessagePacket chatMessagePacket = new ClientChatMessagePacket();
        chatMessagePacket.message = message;
        addPacketToQueue(chatMessagePacket);
    }

    /**
     * Plays a sound from the {@link SoundEvent} enum.
     *
     * @param sound         the sound to play
     * @param x             the effect X
     * @param y             the effect Y
     * @param z             the effect Z
     * @param volume        the volume of the sound (1 is 100%)
     * @param pitch         the pitch of the sound, between 0.5 and 2.0
     */
    public void playSound(@NotNull SoundEvent sound, int x, int y, int z, float volume, float pitch) {
        SoundEffectPacket soundEffectPacket = new SoundEffectPacket();
        soundEffectPacket.soundName = sound.getId();
        soundEffectPacket.position = new Position(x, y, z);
        soundEffectPacket.volume = volume;
        soundEffectPacket.pitch = pitch;
        playerConnection.sendPacket(soundEffectPacket);
    }

    /**
     * Plays a sound from an identifier (represents a custom sound in a resource pack).
     *
     * @param identifier    the identifier of the sound to play
     * @param x             the effect X
     * @param y             the effect Y
     * @param z             the effect Z
     * @param volume        the volume of the sound (1 is 100%)
     * @param pitch         the pitch of the sound, between 0.5 and 2.0
     */
    public void playSound(@NotNull String identifier, int x, int y, int z, float volume, float pitch) {
        SoundEffectPacket soundEffectPacket = new SoundEffectPacket();
        soundEffectPacket.soundName = identifier;
        soundEffectPacket.position = new Position(x, y, z);
        soundEffectPacket.volume = volume;
        soundEffectPacket.pitch = pitch;
        playerConnection.sendPacket(soundEffectPacket);
    }

    /**
     * Plays a given effect at the given position for this player.
     *
     * @param effect                the effect to play
     * @param x                     x position of the effect
     * @param y                     y position of the effect
     * @param z                     z position of the effect
     * @param data                  data for the effect
     * @param disableRelativeVolume disable volume scaling based on distance
     */
    public void playEffect(@NotNull Effects effect, int x, int y, int z, int data, boolean disableRelativeVolume) {
        EffectPacket packet = new EffectPacket(effect.getId(), new Vec(x, y, z), data, disableRelativeVolume);
        playerConnection.sendPacket(packet);
    }

    @Override
    public void showTitle(@NonNull Title title) {
        TitlePacket titlePacket = new TitlePacket();
        titlePacket.action = TitlePacket.Action.SET_TITLE;
        titlePacket.titleText = title.title();
        playerConnection.sendPacket(titlePacket);

        TitlePacket subtitlePacket = new TitlePacket();
        subtitlePacket.action = TitlePacket.Action.SET_SUBTITLE;
        subtitlePacket.titleText = title.subtitle();
        playerConnection.sendPacket(subtitlePacket);

        Title.Times times = title.times();
        if (times != null) {
            TitlePacket timePacket = new TitlePacket();
            timePacket.action = TitlePacket.Action.SET_TIMES_AND_DISPLAY;
            timePacket.fadeIn = (int) (times.fadeIn().toMillis() / Tachyon.getServer().getTickMs());
            timePacket.stay =  (int) (times.stay().toMillis() / Tachyon.getServer().getTickMs());
            timePacket.fadeOut =  (int) (times.fadeOut().toMillis() / Tachyon.getServer().getTickMs());
            playerConnection.sendPacket(timePacket);
        }
    }

    @Override
    public void clearTitle() {
        TitlePacket titlePacket = new TitlePacket();
        titlePacket.action = TitlePacket.Action.HIDE;
        playerConnection.sendPacket(titlePacket);
    }

    @Override
    public void resetTitle() {
        TitlePacket titlePacket = new TitlePacket();
        titlePacket.action = TitlePacket.Action.RESET;
        playerConnection.sendPacket(titlePacket);
    }

    @Override
    public void sendActionBar(@NonNull Component message) {
        ChatMessagePacket chatMessagePacket = new ChatMessagePacket(message, ChatMessagePacket.Position.GAME_INFO);
        playerConnection.sendPacket(chatMessagePacket);
    }

    @Override
    public void sendPlayerListHeaderAndFooter(@NonNull Component header, @NonNull Component footer) {
        PlayerListHeaderAndFooterPacket playerListHeaderAndFooterPacket = new PlayerListHeaderAndFooterPacket(header, footer);
        playerConnection.sendPacket(playerListHeaderAndFooterPacket);
    }

    @Override
    public void openBook(@NonNull Book book) {
        WrittenBookMeta meta = new WrittenBookMeta();
        meta.setGeneration(WrittenBookMeta.WrittenBookGeneration.ORIGINAL);
        meta.setAuthor(book.author());
        meta.setTitle(book.title());
        meta.setPages(book.pages());

        openBook(meta);
    }

    /**
     * Opens a book ui for the player with the given book metadata.
     *
     * @param bookMeta The metadata of the book to open
     */
    public void openBook(@NotNull WrittenBookMeta bookMeta) {
        // Set book in main hand
        ItemStack writtenBook = ItemStack.of(Material.WRITTEN_BOOK, (byte) 1);
        writtenBook = writtenBook.replaceMeta(bookMeta);
        final SetSlotPacket setSlotPacket = new SetSlotPacket();
        setSlotPacket.windowId = 0;
        setSlotPacket.slot = 44;
        setSlotPacket.itemStack = writtenBook;
        this.playerConnection.sendPacket(setSlotPacket);

        HeldItemChangePacket heldItemChangePacket = new HeldItemChangePacket((byte)8);
        this.playerConnection.sendPacket(heldItemChangePacket);

        // Open the book
        final PluginMessagePacket pluginMessagePacket = new PluginMessagePacket("MC|BOpen", new byte[0]);
        this.playerConnection.sendPacket(pluginMessagePacket);

        // Update inventory to remove book (which the actual inventory does not have)
        this.inventory.update();

        // Change back to the original held item
        heldItemChangePacket = new HeldItemChangePacket((byte) heldSlot);
        this.playerConnection.sendPacket(heldItemChangePacket);
    }

    @Override
    public boolean isImmune(@NotNull DamageType type) {
        if (!getGameMode().canTakeDamage()) {
            return type != DamageType.VOID;
        }
        return super.isImmune(type);
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(health);
        sendUpdateHealthPacket();
    }

    /**
     * Gets the player additional hearts.
     *
     * @return the player additional hearts
     */
    public float getAdditionalHearts() {
        return metadata.getIndex((byte) 17, 0f);
    }

    /**
     * Changes the amount of additional hearts shown.
     *
     * @param additionalHearts the count of additional hearts
     */
    public void setAdditionalHearts(float additionalHearts) {
        this.metadata.setIndex((byte) 17, Metadata.Float(additionalHearts));
    }

    /**
     * Gets the player food.
     *
     * @return the player food
     */
    public int getFood() {
        return food;
    }

    /**
     * Sets and refresh client food bar.
     *
     * @param food the new food value
     * @throws IllegalArgumentException if {@code food} is not between 0 and 20
     */
    public void setFood(int food) {
        Check.argCondition(!MathUtils.isBetween(food, 0, 20), "Food has to be between 0 and 20");
        this.food = food;
        sendUpdateHealthPacket();
    }

    public float getFoodSaturation() {
        return foodSaturation;
    }

    /**
     * Sets and refresh client food saturation.
     *
     * @param foodSaturation the food saturation
     * @throws IllegalArgumentException if {@code foodSaturation} is not between 0 and 5
     */
    public void setFoodSaturation(float foodSaturation) {
        Check.argCondition(!MathUtils.isBetween(foodSaturation, 0, 5), "Food saturation has to be between 0 and 5");
        this.foodSaturation = foodSaturation;
        sendUpdateHealthPacket();
    }

    /**
     * Gets if the player is eating.
     *
     * @return true if the player is eating, false otherwise
     */
    public boolean isEating() {
        return isEating;
    }

    /**
     * Gets the player default eating time.
     *
     * @return the player default eating time
     */
    public long getDefaultEatingTime() {
        return defaultEatingTime;
    }

    /**
     * Used to change the default eating time animation.
     *
     * @param defaultEatingTime the default eating time in milliseconds
     */
    public void setDefaultEatingTime(long defaultEatingTime) {
        this.defaultEatingTime = defaultEatingTime;
    }

    /**
     * Gets the player display name in the tab-list.
     *
     * @return the player display name, null means that {@link #getUsername()} is displayed
     */
    @Nullable
    public Component getDisplayName() {
        return displayName;
    }

    /**
     * Changes the player display name in the tab-list.
     * <p>
     * Sets to null to show the player username.
     *
     * @param displayName the display name, null to display the username
     */
    public void setDisplayName(@Nullable Component displayName) {
        this.displayName = displayName;

        PlayerListItemPacket infoPacket = new PlayerListItemPacket(PlayerListItemPacket.Action.UPDATE_DISPLAY_NAME);
        infoPacket.playerInfos().add(new PlayerListItemPacket.UpdateDisplayName(getUuid(), displayName));
        sendPacketToViewersAndSelf(infoPacket);
    }

    /**
     * Gets the player skin.
     *
     * @return the player skin object,
     * null means that the player has his {@link #getUuid()} default skin
     */
    @Nullable
    public PlayerSkin getSkin() {
        return skin;
    }

    /**
     * Changes the player skin.
     * <p>
     * This does remove the player for all viewers to spawn it again with the correct new skin.
     *
     * @param skin the player skin, null to reset it to his {@link #getUuid()} default skin
     * @see PlayerSkinInitEvent if you want to apply the skin at connection
     */
    public synchronized void setSkin(@Nullable PlayerSkin skin) {
        this.skin = skin;

        if (instance == null)
            return;

        DestroyEntitiesPacket destroyEntitiesPacket = new DestroyEntitiesPacket(new int[]{getEntityId()});

        final PlayerListItemPacket removePlayerPacket = getRemovePlayerToList();
        final PlayerListItemPacket addPlayerPacket = getAddPlayerToList();

        RespawnPacket respawnPacket = new RespawnPacket();
        respawnPacket.dimensionType = getDimensionType();
        respawnPacket.gameMode = getGameMode();

        playerConnection.sendPacket(removePlayerPacket);
        playerConnection.sendPacket(destroyEntitiesPacket);
        playerConnection.sendPacket(respawnPacket);
        playerConnection.sendPacket(addPlayerPacket);

        {
            // Remove player
            sendPacketToViewers(removePlayerPacket);
            sendPacketToViewers(destroyEntitiesPacket);

            // Show player again
            getViewers().forEach(player -> showPlayer(((TachyonPlayer)player).getPlayerConnection()));
        }

        getInventory().update();
        teleport(getPosition());
    }

    /**
     * Gets the player username.
     *
     * @return the player username
     */
    @NotNull
    public String getUsername() {
        return username;
    }

    /**
     * Changes the internal player name, used for the {@link AsyncPlayerPreLoginEvent}
     * mostly unsafe outside of it.
     *
     * @param username the new player name
     */
    public void setUsernameField(@NotNull String username) {
        this.username = username;
    }

    private void sendChangeGameStatePacket(@NotNull ChangeGameStatePacket.Reason reason, float value) {
        playerConnection.sendPacket(new ChangeGameStatePacket(reason, value));
    }

    /**
     * Calls an {@link ItemDropEvent} with a specified item.
     * <p>
     * Returns false if {@code item} is air.
     *
     * @param item the item to drop
     * @return true if player can drop the item (event not cancelled), false otherwise
     */
    public boolean dropItem(@NotNull ItemStack item) {
        if (item.isAir()) {
            return false;
        }

        ItemDropEvent itemDropEvent = new ItemDropEvent(this, item);
        callEvent(ItemDropEvent.class, itemDropEvent);
        return !itemDropEvent.isCancelled();
    }

    /**
     * Sets the player resource pack.
     *
     * @param resourcePack the resource pack
     */
    public void setResourcePack(@NotNull ResourcePack resourcePack) {
        final String url = resourcePack.url();
        final String hash = resourcePack.hash();

        ResourcePackSendPacket resourcePackSendPacket = new ResourcePackSendPacket();
        resourcePackSendPacket.url = url;
        resourcePackSendPacket.hash = hash;
        playerConnection.sendPacket(resourcePackSendPacket);
    }

    /**
     * Sets the camera at {@code entity} eyes.
     *
     * @param entity the entity to spectate
     */
    public void spectate(@NotNull TachyonEntity entity) {
        playerConnection.sendPacket(new CameraPacket(entity.getEntityId()));
    }

    /**
     * Resets the camera at the player.
     */
    public void stopSpectating() {
        spectate(this);
    }

    /**
     * Used to retrieve the default spawn point.
     * <p>
     * Can be altered by the {@link PlayerRespawnEvent#setRespawnPosition(Position)}.
     *
     * @return a copy of the default respawn point
     */
    @NotNull
    public Position getRespawnPoint() {
        return respawnPoint;
    }

    /**
     * Changes the default spawn point.
     *
     * @param respawnPoint the player respawn point
     */
    public void setRespawnPoint(@NotNull Position respawnPoint) {
        this.respawnPoint = respawnPoint;
    }

    /**
     * Called after the player teleportation to refresh his position
     * and send data to his new viewers.
     */
    protected void refreshAfterTeleport() {
        getInventory().update();

        sendPacketsToViewers(getEntityType().getSpawnType().getSpawnPacket(this));

        // Update for viewers
        sendPacketToViewersAndSelf(getVelocityPacket());
        sendPacketToViewersAndSelf(getMetadataPacket());
        sendPacketToViewersAndSelf(getPropertiesPacket());
        for (EntityEquipmentPacket entityEquipmentPacket : getEquipmentsPacket()) {
            sendPacketToViewersAndSelf(entityEquipmentPacket);
        }

        {
            // Send new chunks
            final TachyonChunk chunk =(TachyonChunk)  instance.getChunk(position.blockX() >> 4, position.blockZ() >> 4);
            Check.notNull(chunk, "Tried to interact with an unloaded chunk.");
            refreshVisibleChunks(chunk);
        }
    }

    /**
     * Sets the player food and health values to their maximum.
     */
    protected void refreshHealth() {
        this.food = 20;
        this.foodSaturation = 5;
        // refresh health and send health packet
        heal();
    }

    /**
     * Sends an {@link UpdateHealthPacket} to refresh client-side information about health and food.
     */
    protected void sendUpdateHealthPacket() {
        UpdateHealthPacket updateHealthPacket = new UpdateHealthPacket();
        updateHealthPacket.health = getHealth();
        updateHealthPacket.food = food;
        updateHealthPacket.foodSaturation = foodSaturation;
        playerConnection.sendPacket(updateHealthPacket);
    }

    /**
     * Gets the percentage displayed in the experience bar.
     *
     * @return the exp percentage 0-1
     */
    public float getExp() {
        return exp;
    }

    /**
     * Used to change the percentage experience bar.
     * This cannot change the displayed level, see {@link #setLevel(int)}.
     *
     * @param exp a percentage between 0 and 1
     * @throws IllegalArgumentException if {@code exp} is not between 0 and 1
     */
    public void setExp(float exp) {
        Check.argCondition(!MathUtils.isBetween(exp, 0, 1), "Exp should be between 0 and 1");

        this.exp = exp;
        sendExperienceUpdatePacket();
    }

    /**
     * Gets the level of the player displayed in the experience bar.
     *
     * @return the player level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Used to change the level of the player
     * This cannot change the displayed percentage bar see {@link #setExp(float)}
     *
     * @param level the new level of the player
     */
    public void setLevel(int level) {
        this.level = level;
        sendExperienceUpdatePacket();
    }

    /**
     * Sends a {@link SetExperiencePacket} to refresh client-side information about the experience bar.
     */
    protected void sendExperienceUpdatePacket() {
        SetExperiencePacket setExperiencePacket = new SetExperiencePacket();
        setExperiencePacket.percentage = exp;
        setExperiencePacket.level = level;
        playerConnection.sendPacket(setExperiencePacket);
    }

    /**
     * Called when the player changes chunk (move from one to another).
     * Can also be used to refresh the list of chunks that the client should see based on {@link #getChunkRange()}.
     * <p>
     * It does remove and add the player from the chunks viewers list when removed or added.
     * It also calls the events {@link PlayerChunkUnloadEvent} and {@link PlayerChunkLoadEvent}.
     *
     * @param newChunk the current/new player chunk (can be the current one)
     */
    public void refreshVisibleChunks(@NotNull TachyonChunk newChunk) {
        // Previous chunks indexes
        final long[] lastVisibleChunks = viewableChunks.stream().mapToLong(viewableChunks ->
                ChunkUtils.getChunkIndex(viewableChunks.getChunkX(), viewableChunks.getChunkZ())
        ).toArray();

        // New chunks indexes
        final long[] updatedVisibleChunks = ChunkUtils.getChunksInRange(newChunk.toPosition(), getChunkRange());

        // Find the difference between the two arrays
        final int[] oldChunks = ArrayUtils.getDifferencesBetweenArray(lastVisibleChunks, updatedVisibleChunks);
        final int[] newChunks = ArrayUtils.getDifferencesBetweenArray(updatedVisibleChunks, lastVisibleChunks);

        // Unload old chunks
        for (int index : oldChunks) {
            final long chunkIndex = lastVisibleChunks[index];
            final int chunkX = ChunkUtils.getChunkCoordX(chunkIndex);
            final int chunkZ = ChunkUtils.getChunkCoordZ(chunkIndex);

            // TODO prevent the client from getting lag spikes when re-loading large chunks
            // Probably by having a distinction between visible and loaded (cache) chunks
            /*ChunkDataPacket chunkDataPacket = new ChunkDataPacket(null, 0);
            chunkDataPacket.chunkX = chunkX;
            chunkDataPacket.chunkZ = chunkZ;
            chunkDataPacket.fullChunk = false;
            chunkDataPacket.unloadChunk = true;
            chunkDataPacket.skylight = newChunk.getHasSky();
            playerConnection.sendPacket(chunkDataPacket);*/

            final TachyonChunk chunk = (TachyonChunk) instance.getChunk(chunkX, chunkZ);
            if (chunk != null)
                chunk.removeViewer(this);
        }

        // Load new chunks
        for (int index : newChunks) {
            final long chunkIndex = updatedVisibleChunks[index];
            final int chunkX = ChunkUtils.getChunkCoordX(chunkIndex);
            final int chunkZ = ChunkUtils.getChunkCoordZ(chunkIndex);

            this.instance.loadOptionalChunk(chunkX, chunkZ, chunk -> {
                if (chunk == null) {
                    // Cannot load chunk (auto load is not enabled)
                    return;
                }
                chunk.addViewer(this);
            });
        }
    }

    public void refreshVisibleChunks() {
        final TachyonChunk chunk = getChunk();
        if (chunk != null) {
            refreshVisibleChunks(chunk);
        }
    }

    /**
     * Refreshes the list of entities that the player should be able to see based on {@link MinecraftServer#getEntityViewDistance()}
     * and {@link TachyonEntity#isAutoViewable()}.
     *
     * @param newChunk the new chunk of the player (can be the current one)
     */
    public void refreshVisibleEntities(@NotNull TachyonChunk newChunk) {
        final int entityViewDistance = Tachyon.getServer().getEntityViewDistance();
        final float maximalDistance = entityViewDistance * TachyonChunk.CHUNK_SECTION_SIZE;

        // Manage already viewable entities
        this.viewableEntities.forEach(entity -> {
            final double distance = entity.getDistance(this);
            if (distance > maximalDistance) {
                // TachyonEntity shouldn't be viewable anymore
                if (isAutoViewable()) {
                    entity.removeViewer(this);
                }
                if (entity instanceof TachyonPlayer && entity.isAutoViewable()) {
                    removeViewer((TachyonPlayer) entity);
                }
            }
        });

        // Manage entities in unchecked chunks
        EntityUtils.forEachRange(instance, newChunk.toPosition(), entityViewDistance, entity -> {
            if (entity.isAutoViewable() && !entity.viewers.contains(this)) {
                entity.addViewer(this);
            }

            if (entity instanceof TachyonPlayer && isAutoViewable() && !viewers.contains(entity)) {
                addViewer((TachyonPlayer) entity);
            }
        });

    }

    @Override
    public void teleport(@NotNull Position position, @Nullable long[] chunks, @Nullable Runnable callback) {
        super.teleport(position, chunks, () -> {
            updatePlayerPosition();
            OptionalCallback.execute(callback);
        });
    }

    @Override
    public void teleport(@NotNull Position position, @Nullable Runnable callback) {
        final boolean sameChunk = getPosition().inSameChunk(position);
        final long[] chunks = sameChunk ? null :
                ChunkUtils.getChunksInRange(position, getChunkRange());
        teleport(position, chunks, callback);
    }

    @Override
    public void teleport(@NotNull Position position) {
        teleport(position, null);
    }

    /**
     * Gets the player connection.
     * <p>
     * Used to send packets and get stuff related to the connection.
     *
     * @return the player connection
     */
    @NotNull
    public PlayerConnection getPlayerConnection() {
        return playerConnection;
    }

    /**
     * Gets if the player is online or not.
     *
     * @return true if the player is online, false otherwise
     */
    public boolean isOnline() {
        return playerConnection.isOnline();
    }

    @Override
    public void sendPacket(@NotNull ServerPacket packet) {
        this.playerConnection.sendPacket(packet);
    }

    /**
     * Gets the player settings.
     *
     * @return the player settings
     */
    @NotNull
    public PlayerSettings getSettings() {
        return settings;
    }

    /**
     * Gets the player dimension.
     *
     * @return the player current dimension
     */
    public DimensionType getDimensionType() {
        return dimensionType;
    }

    /**
     * Gets the player level type.
     *
     * @return the player current level type
     */
    public LevelType getLevelType() {
        return levelType;
    }

    @NotNull
    public PlayerInventory getInventory() {
        return inventory;
    }

    /**
     * Used to get the player latency,
     * computed by seeing how long it takes the client to answer the {@link KeepAlivePacket} packet.
     *
     * @return the player latency
     */
    public int getLatency() {
        return latency;
    }

    /**
     * Gets the player {@link GameMode}.
     *
     * @return the player current gamemode
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Changes the player {@link GameMode}.
     *
     * @param gameMode the new player GameMode
     */
    public void setGameMode(@NotNull GameMode gameMode) {
        this.gameMode = gameMode;

        // Condition to prevent sending the packets before spawning the player
        if (isActive()) {
            sendChangeGameStatePacket(ChangeGameStatePacket.Reason.CHANGE_GAMEMODE, gameMode.getId());

            PlayerListItemPacket infoPacket = new PlayerListItemPacket(PlayerListItemPacket.Action.UPDATE_GAMEMODE);
            infoPacket.playerInfos().add(new PlayerListItemPacket.UpdateGamemode(getUuid(), gameMode));
            sendPacketToViewersAndSelf(infoPacket);
        }
    }

    /**
     * Gets if this player is in creative. Used for code readability.
     *
     * @return true if the player is in creative mode
     */
    public boolean isCreative() {
        return gameMode == GameMode.CREATIVE;
    }

    /**
     * Changes the dimension of the player.
     * Mostly unsafe since it requires sending chunks after.
     *
     * @param dimensionType the new player dimension
     */
    protected void sendDimension(@NotNull DimensionType dimensionType, @NotNull LevelType levelType) {
        Check.argCondition(dimensionType.equals(getDimensionType()), "The dimension needs to be different than the current one!");

        this.dimensionType = dimensionType;
        this.levelType = levelType;
        RespawnPacket respawnPacket = new RespawnPacket();
        respawnPacket.dimensionType = dimensionType;
        respawnPacket.difficulty = Tachyon.getServer().getDifficulty();
        respawnPacket.gameMode = gameMode;
        respawnPacket.levelType = levelType;
        playerConnection.sendPacket(respawnPacket);
    }

    /**
     * Kicks the player with a reason.
     *
     * @param text the kick reason
     */
    public void kick(@NotNull Component text) {
        final ConnectionState connectionState = playerConnection.getConnectionState();

        // Packet type depends on the current player connection state
        final ServerPacket disconnectPacket;
        if (connectionState == ConnectionState.LOGIN) {
            disconnectPacket = new LoginDisconnectPacket(text);
        } else {
            disconnectPacket = new DisconnectPacket(text);
        }

        ((NettyPlayerConnection) playerConnection).writeAndFlush(disconnectPacket);
        playerConnection.disconnect();
    }

    /**
     * Kicks the player with a reason.
     *
     * @param message the kick reason
     */
    public void kick(@NotNull String message) {
        kick(Component.text(message));
    }

    /**
     * Changes the current held slot for the player.
     *
     * @param slot the slot that the player has to held
     * @throws IllegalArgumentException if {@code slot} is not between 0 and 8
     */
    public void setHeldItemSlot(byte slot) {
        Check.argCondition(!MathUtils.isBetween(slot, 0, 8), "Slot has to be between 0 and 8");

        HeldItemChangePacket heldItemChangePacket = new HeldItemChangePacket(slot);
        playerConnection.sendPacket(heldItemChangePacket);
        refreshHeldSlot(slot);
    }

    /**
     * Gets the player held slot (0-8).
     *
     * @return the current held slot for the player
     */
    public byte getHeldSlot() {
        return heldSlot;
    }

    public void setTeam(Team team) {
        super.setTeam(team);
        if (team != null)
            PacketUtils.sendGroupedPacket(Tachyon.getServer().getConnectionManager().getOnlinePlayers(), team.createTeamsCreationPacket());
    }

    /**
     * Changes the tag below the name.
     *
     * @param belowNameTag The new below name tag
     */
    public void setBelowNameTag(BelowNameTag belowNameTag) {
        if (this.belowNameTag == belowNameTag) return;

        if (this.belowNameTag != null) {
            this.belowNameTag.removeViewer(this);
        }

        this.belowNameTag = belowNameTag;
    }

    /**
     * Gets the player open inventory.
     *
     * @return the currently open inventory, null if there is not (player inventory is not detected)
     */
    @Nullable
    public Inventory getOpenInventory() {
        return openInventory;
    }

    /**
     * Opens the specified Inventory, close the previous inventory if existing.
     *
     * @param inventory the inventory to open
     * @return true if the inventory has been opened/sent to the player, false otherwise (cancelled by event)
     */
    public boolean openInventory(@NotNull Inventory inventory) {

        InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent(inventory, this);

        callCancellableEvent(InventoryOpenEvent.class, inventoryOpenEvent, () -> {

            if (getOpenInventory() != null) {
                closeInventory();
            }

            Inventory newInventory = inventoryOpenEvent.getInventory();

            if (newInventory == null) {
                // just close the inventory
                return;
            }

            OpenWindowPacket openWindowPacket = new OpenWindowPacket(newInventory.getWindowId(), newInventory.getInventoryType().getWindowType(), newInventory.getTitle(), (byte) newInventory.getSize(), -1);
            playerConnection.sendPacket(openWindowPacket);
            newInventory.addViewer(this);
            this.openInventory = newInventory;

        });

        return !inventoryOpenEvent.isCancelled();
    }

    /**
     * Closes the current inventory if there is any.
     * It closes the player inventory (when opened) if {@link #getOpenInventory()} returns null.
     */
    public void closeInventory() {
        Inventory openInventory = getOpenInventory();

        // Drop cursor item when closing inventory
        ItemStack cursorItem;
        if (openInventory == null) {
            cursorItem = getInventory().getCursorItem();
            getInventory().setCursorItem(ItemStack.AIR);
        } else {
            cursorItem = openInventory.getCursorItem(this);
            openInventory.setCursorItem(this, ItemStack.AIR);
        }
        if (!cursorItem.isAir()) {
            // Add item to inventory if he hasn't been able to drop it
            if (!dropItem(cursorItem)) {
                getInventory().addItemStack(cursorItem);
            }
        }

        int windowId;
        if (openInventory == null) {
            windowId = 0;
        }
        else {
            windowId = openInventory.getWindowId();
            openInventory.removeViewer(this);
            this.openInventory = null;
        }
        CloseWindowPacket closeWindowPacket = new CloseWindowPacket((byte) windowId);
        playerConnection.sendPacket(closeWindowPacket);
        inventory.update();
        this.didCloseInventory = true;
    }

    /**
     * Used internally to prevent an inventory click to be processed
     * when the inventory listeners closed the inventory.
     * <p>
     * Should only be used within an inventory listener (event or condition).
     *
     * @return true if the inventory has been closed, false otherwise
     */
    public boolean didCloseInventory() {
        return didCloseInventory;
    }

    /**
     * Used internally to reset the didCloseInventory field.
     * <p>
     * Shouldn't be used externally without proper understanding of its consequence.
     *
     * @param didCloseInventory the new didCloseInventory field
     */
    public void UNSAFE_changeDidCloseInventory(boolean didCloseInventory) {
        this.didCloseInventory = didCloseInventory;
    }

    /**
     * Gets the player viewable chunks.
     * <p>
     * WARNING: adding or removing a chunk there will not load/unload it,
     * use {@link TachyonChunk#addViewer(Player)} or {@link TachyonChunk#removeViewer(Player)}.
     *
     * @return a {@link Set} containing all the chunks that the player sees
     */
    public Set<TachyonChunk> getViewableChunks() {
        return viewableChunks;
    }

    /**
     * Used for synchronization purpose, mainly for teleportation
     */
    protected void updatePlayerPosition() {
        PlayerPositionAndLookPacket positionAndLookPacket = new PlayerPositionAndLookPacket(position, (byte) 0x00);
        playerConnection.sendPacket(positionAndLookPacket);
    }

    /**
     * Gets the player permission level.
     *
     * @return the player permission level
     */
    public int getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Changes the player permission level.
     *
     * @param permissionLevel the new player permission level
     * @throws IllegalArgumentException if {@code permissionLevel} is not between 0 and 4
     */
    public void setPermissionLevel(int permissionLevel) {
        Check.argCondition(!MathUtils.isBetween(permissionLevel, 0, 4), "permissionLevel has to be between 0 and 4");

        this.permissionLevel = permissionLevel;

        // Magic values: https://wiki.vg/Entity_statuses#Player
        // TODO remove magic values
        final byte permissionLevelStatus = (byte) (24 + permissionLevel);
        triggerStatus(permissionLevelStatus);
    }

    /**
     * Sets or remove the reduced debug screen.
     *
     * @param reduced should the player has the reduced debug screen
     */
    public void setReducedDebugScreenInformation(boolean reduced) {
        this.reducedDebugScreenInformation = reduced;

        // Magic values: https://wiki.vg/Entity_statuses#Player
        // TODO remove magic values
        final byte debugScreenStatus = (byte) (reduced ? 22 : 23);
        triggerStatus(debugScreenStatus);
    }

    /**
     * Gets if the player has the reduced debug screen.
     *
     * @return true if the player has the reduced debug screen, false otherwise
     */
    public boolean hasReducedDebugScreenInformation() {
        return reducedDebugScreenInformation;
    }

    /**
     * The invulnerable field appear in the {@link PlayerAbilitiesPacket} packet.
     *
     * @return true if the player is invulnerable, false otherwise
     */
    public boolean isInvulnerable() {
        return super.isInvulnerable();
    }

    /**
     * This do update the {@code invulnerable} field in the packet {@link PlayerAbilitiesPacket}
     * and prevent the player from receiving damage.
     *
     * @param invulnerable should the player be invulnerable
     */
    public void setInvulnerable(boolean invulnerable) {
        super.setInvulnerable(invulnerable);
        refreshAbilities();
    }

    /**
     * Gets if the player is currently flying.
     *
     * @return true if the player if flying, false otherwise
     */
    public boolean isFlying() {
        return flying;
    }

    /**
     * Sets the player flying.
     *
     * @param flying should the player fly
     */
    public void setFlying(boolean flying) {
        this.flying = flying;
        refreshAbilities();
    }

    /**
     * Updates the internal flying field.
     * <p>
     * Mostly unsafe since there is nothing to backup the value, used internally for creative players.
     *
     * @param flying the new flying field
     * @see #setFlying(boolean) instead
     */
    public void refreshFlying(boolean flying) {
        this.flying = flying;
    }

    /**
     * Gets if the player is allowed to fly.
     *
     * @return true if the player if allowed to fly, false otherwise
     */
    public boolean isAllowFlying() {
        return allowFlying;
    }

    /**
     * Allows or forbid the player to fly.
     *
     * @param allowFlying should the player be allowed to fly
     */
    public void setAllowFlying(boolean allowFlying) {
        this.allowFlying = allowFlying;
        refreshAbilities();
    }

    public boolean isInstantBreak() {
        return instantBreak;
    }

    /**
     * Changes the player ability "Creative Mode".
     * <a href="https://wiki.vg/Protocol#Player_Abilities_.28clientbound.29">see</a>
     *
     * @param instantBreak true to allow instant break
     */
    public void setInstantBreak(boolean instantBreak) {
        this.instantBreak = instantBreak;
        refreshAbilities();
    }

    /**
     * Gets the player flying speed.
     *
     * @return the flying speed of the player
     */
    public float getFlyingSpeed() {
        return flyingSpeed;
    }

    /**
     * Updates the internal field and send a {@link PlayerAbilitiesPacket} with the new flying speed.
     *
     * @param flyingSpeed the new flying speed of the player
     */
    public void setFlyingSpeed(float flyingSpeed) {
        this.flyingSpeed = flyingSpeed;
        refreshAbilities();
    }

    public float getFieldViewModifier() {
        return fieldViewModifier;
    }

    public void setFieldViewModifier(float fieldViewModifier) {
        this.fieldViewModifier = fieldViewModifier;
        refreshAbilities();
    }

    /**
     * This is the map used to send the statistic packet.
     * It is possible to add/remove/change statistic value directly into it.
     *
     * @return the modifiable statistic map
     */
    @NotNull
    public Map<PlayerStatistic, Integer> getStatisticValueMap() {
        return statisticValueMap;
    }

    /**
     * Gets the player vehicle information.
     *
     * @return the player vehicle information
     */
    @NotNull
    public PlayerVehicleInformation getVehicleInformation() {
        return vehicleInformation;
    }

    /**
     * Sends to the player a {@link PlayerAbilitiesPacket} with all the updated fields.
     */
    protected void refreshAbilities() {
        PlayerAbilitiesPacket playerAbilitiesPacket = new PlayerAbilitiesPacket(invulnerable, flying, allowFlying, instantBreak, flyingSpeed, fieldViewModifier);
        playerConnection.sendPacket(playerAbilitiesPacket);
    }

    /**
     * All packets in the queue are executed in the {@link #update(long)} method
     * It is used internally to add all received packet from the client.
     * Could be used to "simulate" a received packet, but to use at your own risk.
     *
     * @param packet the packet to add in the queue
     */
    public void addPacketToQueue(@NotNull ClientPlayPacket packet) {
        this.packets.add(packet);
    }

    /**
     * Changes the storage player latency and update its tab value.
     *
     * @param latency the new player latency
     */
    public void refreshLatency(int latency) {
        this.latency = latency;
        PlayerListItemPacket playerListItemPacket = new PlayerListItemPacket(PlayerListItemPacket.Action.UPDATE_LATENCY);
        playerListItemPacket.playerInfos().add(new PlayerListItemPacket.UpdateLatency(getUuid(), latency));
        sendPacketToViewersAndSelf(playerListItemPacket);
    }

    public void refreshOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    /**
     * Used to change internally the last sent last keep alive id.
     * <p>
     * Warning: could lead to have the player kicked because of a wrong keep alive packet.
     *
     * @param lastKeepAlive the new lastKeepAlive id
     */
    public void refreshKeepAlive(long lastKeepAliveTime, int lastKeepAlive) {
        this.lastKeepAliveTime = lastKeepAliveTime;
        this.lastKeepAlive = lastKeepAlive;
        this.answerKeepAlive = false;
    }

    public boolean didAnswerKeepAlive() {
        return answerKeepAlive;
    }

    public void refreshAnswerKeepAlive(boolean answerKeepAlive) {
        this.answerKeepAlive = answerKeepAlive;
    }

    /**
     * Changes the held item for the player viewers
     * Also cancel eating if {@link #isEating()} was true.
     * <p>
     * Warning: the player will not be noticed by this chance, only his viewers,
     * see instead: {@link #setHeldItemSlot(byte)}.
     *
     * @param slot the new held slot
     */
    public void refreshHeldSlot(byte slot) {
        this.heldSlot = slot;
        syncEquipment(EntityEquipmentPacket.Slot.HAND);

        refreshEating(false);
    }

    public void refreshEating(boolean isEating, long eatingTime) {
        this.isEating = isEating;
        if (isEating) {
            this.startEatingTime = System.currentTimeMillis();
            this.eatingTime = eatingTime;
        } else {
            this.startEatingTime = 0;
        }
    }

    public void refreshEating(boolean isEating) {
        refreshEating(isEating, defaultEatingTime);
    }

    /**
     * Used to call {@link ItemUpdateStateEvent} with the proper item
     * It does check which hand to get the item to update.
     *
     * @param allowFood true if food should be updated, false otherwise
     * @return the called {@link ItemUpdateStateEvent},
     * null if there is no item to update the state
     */
    @Nullable
    public ItemUpdateStateEvent callItemUpdateStateEvent(boolean allowFood) {
        final Material handMat = getItemInHand().material();

        final ItemStack updatedItem = handMat.hasState() ? getItemInHand() : null;
        if (updatedItem == null) // No item with state, cancel
            return null;

        final boolean isFood = updatedItem.material().isFood();

        if (isFood && !allowFood)
            return null;

        ItemUpdateStateEvent itemUpdateStateEvent = new ItemUpdateStateEvent(this, updatedItem);
        callEvent(ItemUpdateStateEvent.class, itemUpdateStateEvent);

        return itemUpdateStateEvent;
    }

    public void refreshVehicleSteer(float sideways, float forward, boolean jump, boolean unmount) {
        this.vehicleInformation.refresh(sideways, forward, jump, unmount);
    }

    /**
     * @return the chunk range of the viewers,
     * which is {@link MinecraftServer#getChunkViewDistance()} or {@link PlayerSettings#getViewDistance()}
     * based on which one is the lowest
     */
    public int getChunkRange() {
        final int playerRange = getSettings().viewDistance;
        if (playerRange < 1) {
            // Didn't receive settings packet yet (is the case on login)
            // In this case we send the smallest amount of chunks possible
            // Will be updated in PlayerSettings#refresh.
            // Non-compliant clients might also be stuck with this view
            return 3;
        } else {
            final int serverRange = Tachyon.getServer().getChunkViewDistance();
            return Math.min(playerRange, serverRange);
        }
    }

    /**
     * Gets the last sent keep alive id.
     *
     * @return the last keep alive id sent to the player
     */
    public int getLastKeepAlive() {
        return lastKeepAlive;
    }

    /**
     * Gets the last time at which a keep alive was sent.
     *
     * @return the last keep alive time a keep alive was sent to the player
     */
    public long getLastKeepAliveTime() {
        return lastKeepAliveTime;
    }

    /**
     * Gets the packet to add the player from the tab-list.
     *
     * @return a {@link PlayerListItemPacket} to add the player
     */
    @NotNull
    protected PlayerListItemPacket getAddPlayerToList() {
        PlayerListItemPacket playerListItemPacket = new PlayerListItemPacket(PlayerListItemPacket.Action.ADD_PLAYER);

        PlayerListItemPacket.AddPlayer addPlayer =
                new PlayerListItemPacket.AddPlayer(getUuid(), getUsername(), getGameMode(), getLatency());
        addPlayer.displayName = displayName;

        // Skin support
        if (skin != null) {
            final String textures = skin.getTextures();
            final String signature = skin.getSignature();

            PlayerListItemPacket.AddPlayer.Property prop =
                    new PlayerListItemPacket.AddPlayer.Property("textures", textures, signature);
            addPlayer.properties.add(prop);
        }

        playerListItemPacket.playerInfos().add(addPlayer);
        return playerListItemPacket;
    }

    /**
     * Gets the packet to remove the player from the tab-list.
     *
     * @return a {@link PlayerListItemPacket} to remove the player
     */
    @NotNull
    protected PlayerListItemPacket getRemovePlayerToList() {
        PlayerListItemPacket playerListItemPacket = new PlayerListItemPacket(PlayerListItemPacket.Action.REMOVE_PLAYER);

        PlayerListItemPacket.RemovePlayer removePlayer =
                new PlayerListItemPacket.RemovePlayer(getUuid());

        playerListItemPacket.playerInfos().add(removePlayer);
        return playerListItemPacket;
    }

    /**
     * Sends all the related packet to have the player sent to another with related data
     * (create player, spawn position, velocity, metadata, equipments, passengers, team).
     * <p>
     * WARNING: this alone does not sync the player, please use {@link #addViewer(Player)}.
     *
     * @param connection the connection to show the player to
     */
    protected void showPlayer(@NotNull PlayerConnection connection) {
        connection.sendPacket(getAddPlayerToList());

        connection.sendPacket(getEntityType().getSpawnType().getSpawnPacket(this));
        connection.sendPacket(getVelocityPacket());
        connection.sendPacket(getMetadataPacket());
        for (EntityEquipmentPacket entityEquipmentPacket : getEquipmentsPacket()) {
            connection.sendPacket(entityEquipmentPacket);
        }

        // Team
        if (this.getTeam() != null)
            connection.sendPacket(this.getTeam().createTeamsCreationPacket());

        EntityHeadLookPacket entityHeadLookPacket = new EntityHeadLookPacket(getEntityId(), position.getYaw());
        connection.sendPacket(entityHeadLookPacket);
    }

    @NotNull
    @Override
    public ItemStack getItemInHand() {
        return inventory.getItemInHand();
    }

    @Override
    public void setItemInHand(@NotNull ItemStack itemStack) {
        inventory.setItemInHand(itemStack);
    }

    @NotNull
    @Override
    public ItemStack getHelmet() {
        return inventory.getHelmet();
    }

    @Override
    public void setHelmet(@NotNull ItemStack itemStack) {
        inventory.setHelmet(itemStack);
    }

    @NotNull
    @Override
    public ItemStack getChestplate() {
        return inventory.getChestplate();
    }

    @Override
    public void setChestplate(@NotNull ItemStack itemStack) {
        inventory.setChestplate(itemStack);
    }

    @NotNull
    @Override
    public ItemStack getLeggings() {
        return inventory.getLeggings();
    }

    @Override
    public void setLeggings(@NotNull ItemStack itemStack) {
        inventory.setLeggings(itemStack);
    }

    @NotNull
    @Override
    public ItemStack getBoots() {
        return inventory.getBoots();
    }

    @Override
    public void setBoots(@NotNull ItemStack itemStack) {
        inventory.setBoots(itemStack);
    }

    public enum FacePoint {
        FEET,
        EYE
    }

    // Settings enum

    public enum ChatMode {
        ENABLED,
        COMMANDS_ONLY,
        HIDDEN
    }

    public class PlayerSettings {

        private String locale;
        private byte viewDistance;
        private ChatMode chatMode;
        private boolean chatColors;
        private byte displayedSkinParts;

        private boolean firstRefresh = true;

        /**
         * The player game language.
         *
         * @return the player locale
         */
        public String getLocale() {
            return locale;
        }

        /**
         * Gets the player view distance.
         *
         * @return the player view distance
         */
        public byte getViewDistance() {
            return viewDistance;
        }

        /**
         * Gets the player chat mode.
         *
         * @return the player chat mode
         */
        public ChatMode getChatMode() {
            return chatMode;
        }

        /**
         * Gets if the player has chat colors enabled.
         *
         * @return true if chat colors are enabled, false otherwise
         */
        public boolean hasChatColors() {
            return chatColors;
        }

        public byte getDisplayedSkinParts() {
            return displayedSkinParts;
        }

        /**
         * Changes the player settings internally.
         * <p>
         * WARNING: the player will not be noticed by this change, probably unsafe.
         *
         * @param locale             the player locale
         * @param viewDistance       the player view distance
         * @param chatMode           the player chat mode
         * @param chatColors         the player chat colors
         * @param displayedSkinParts the player displayed skin parts
         */
        public void refresh(String locale, byte viewDistance, ChatMode chatMode, boolean chatColors,
                            byte displayedSkinParts) {

            final boolean viewDistanceChanged = this.viewDistance != viewDistance;

            this.locale = locale;
            this.viewDistance = viewDistance;
            this.chatMode = chatMode;
            this.chatColors = chatColors;
            this.displayedSkinParts = displayedSkinParts;

            metadata.setIndex((byte) 10, Metadata.Byte(displayedSkinParts));

            this.firstRefresh = false;

            // Client changed his view distance in the settings
            if (viewDistanceChanged) {
                refreshVisibleChunks();
            }
        }

    }

}
