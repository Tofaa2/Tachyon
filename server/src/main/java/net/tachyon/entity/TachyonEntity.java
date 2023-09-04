package net.tachyon.entity;

import com.google.common.collect.Queues;
import net.kyori.adventure.text.Component;
import net.tachyon.Tachyon;
import net.tachyon.collision.BoundingBox;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.utils.CollisionUtils;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.data.Data;
import net.tachyon.data.DataContainer;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.event.Event;
import net.tachyon.event.EventCallback;
import net.tachyon.event.entity.*;
import net.tachyon.event.handler.EventHandler;
import net.tachyon.instance.Instance;
import net.tachyon.instance.InstanceManager;
import net.tachyon.block.CustomBlock;
import net.tachyon.network.packet.server.play.*;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.permission.Permission;
import net.tachyon.permission.PermissionHandler;
import net.tachyon.potion.Potion;
import net.tachyon.potion.PotionEffect;
import net.tachyon.potion.TimedPotion;
import net.tachyon.utils.OptionalCallback;
import net.tachyon.world.chunk.Chunk;
import net.tachyon.world.chunk.ChunkCallback;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.utils.entity.EntityUtils;
import net.tachyon.utils.player.PlayerUtils;
import net.tachyon.utils.time.CooldownUtils;
import net.tachyon.utils.time.TimeUnit;
import net.tachyon.utils.time.UpdateOption;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Could be a player, a monster, or an object.
 * <p>
 * To create your own entity you probably want to extends {@link ObjectEntity} or {@link EntityCreature} instead.
 */
public class TachyonEntity implements Entity,  EventHandler, DataContainer, PermissionHandler {

    private static final Map<Integer, TachyonEntity> entityById = new ConcurrentHashMap<>();
    private static final Map<UUID, TachyonEntity> entityByUuid = new ConcurrentHashMap<>();
    private static final AtomicInteger lastEntityId = new AtomicInteger();

    protected Instance instance;
    protected Position position;
    protected double lastX, lastY, lastZ;
    protected double cacheX, cacheY, cacheZ; // Used to synchronize with #getPosition
    protected float lastYaw, lastPitch;
    protected float cacheYaw, cachePitch;
    protected boolean onGround;

    private BoundingBox boundingBox;

    protected TachyonEntity passenger;
    protected TachyonEntity vehicle;

    // Velocity
    protected Vec velocity = Vec.ZERO; // Movement in block per second
    protected boolean hasPhysics = true;

    protected double gravityDragPerTick;
    protected double gravityAcceleration;
    protected double gravityTerminalVelocity;
    protected int gravityTickCount; // Number of tick where gravity tick was applied

    private boolean autoViewable;
    private final int id;
    protected final Set<Player> viewers = ConcurrentHashMap.newKeySet();
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);
    private Data data;
    private final Set<Permission> permissions = new CopyOnWriteArraySet<>();

    protected UUID uuid;
    private boolean isActive; // False if entity has only been instanced without being added somewhere
    private boolean removed;
    private boolean shouldRemove;
    private long scheduledRemoveTime;

    protected EntityType entityType; // UNSAFE to change, modify at your own risk

    // Network synchronization, send the absolute position of the entity each X milliseconds
    private static final UpdateOption SYNCHRONIZATION_COOLDOWN = new UpdateOption(1500, TimeUnit.MILLISECOND);
    private UpdateOption customSynchronizationCooldown;
    private long lastAbsoluteSynchronizationTime;

    // Events
    private final Map<Class<? extends Event>, Collection<EventCallback>> eventCallbacks = new ConcurrentHashMap<>();
    private final Map<String, Collection<EventCallback<?>>> extensionCallbacks = new ConcurrentHashMap<>();

    protected Metadata metadata = new Metadata(this);
    protected TachyonEntityMeta entityMeta;

    private final List<TimedPotion> effects = new CopyOnWriteArrayList<>();

    // list of scheduled tasks to be executed during the next entity tick
    protected final Queue<Consumer<TachyonEntity>> nextTick = Queues.newConcurrentLinkedQueue();

    // Tick related
    private long ticks;
    private final EntityTickEvent tickEvent = new EntityTickEvent(this);

    /**
     * Lock used to support #switchEntityType
     */
    private final Object entityTypeLock = new Object();

    public TachyonEntity(@NotNull EntityType entityType, @NotNull UUID uuid) {
        this.id = generateId();
        this.entityType = entityType;
        this.uuid = uuid;
        this.position = Position.ZERO;

        setBoundingBox(entityType.getWidth(), entityType.getHeight(), entityType.getWidth());

        this.entityMeta = entityType.getMetaConstructor().apply(this, this.metadata);

        setAutoViewable(true);

        TachyonEntity.entityById.put(id, this);
        TachyonEntity.entityByUuid.put(uuid, this);

        // Always Show Name Tag, we need at least one attribute
        metadata.setIndex((byte) 3, Metadata.Byte((byte) 0));
    }

    public TachyonEntity(@NotNull EntityType entityType) {
        this(entityType, UUID.randomUUID());
    }

    @Deprecated
    public TachyonEntity(@NotNull EntityType entityType, @NotNull UUID uuid, @NotNull Position spawnPosition) {
        this(entityType, uuid);
        this.position = spawnPosition;
        this.lastX = spawnPosition.x();
        this.lastY = spawnPosition.y();
        this.lastZ = spawnPosition.z();
    }

    @Deprecated
    public TachyonEntity(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        this(entityType, UUID.randomUUID(), spawnPosition);
    }

    /**
     * Gets an entity based on its id (from {@link #getEntityId()}).
     * <p>
     * TachyonEntity id are unique server-wide.
     *
     * @param id the entity unique id
     * @return the entity having the specified id, null if not found
     */
    @Nullable
    public static TachyonEntity getEntity(int id) {
        return TachyonEntity.entityById.getOrDefault(id, null);
    }

    /**
     * Gets an entity based on its UUID (from {@link #getUuid()}).
     *
     * @param uuid the entity UUID
     * @return the entity having the specified uuid, null if not found
     */
    @Nullable
    public static TachyonEntity getEntity(@NotNull UUID uuid) {
        return TachyonEntity.entityByUuid.getOrDefault(uuid, null);
    }


    /**
     * Generate and return a new unique entity id.
     * <p>
     * Useful if you want to spawn entities using packet but don't risk to have duplicated id.
     *
     * @return a newly generated entity id
     */
    public static int generateId() {
        return lastEntityId.incrementAndGet();
    }

    /**
     * Called each tick.
     *
     * @param time time of the update in milliseconds
     */
    public void update(long time) {

    }

    /**
     * Called when a new instance is set.
     */
    public void spawn() {

    }

    public boolean isOnGround() {
        return onGround || EntityUtils.isOnGround(this) /* backup for levitating entities */;
    }

    /**
     * Gets metadata of this entity.
     * You may want to cast it to specific implementation.
     *
     * @return metadata of this entity.
     */
    @NotNull
    public TachyonEntityMeta getEntityMeta() {
        return this.entityMeta;
    }

    /**
     * Teleports the entity only if the chunk at {@code position} is loaded or if
     * {@link Instance#hasEnabledAutoChunkLoad()} returns true.
     *
     * @param position the teleport position
     * @param chunks   the chunk indexes to load before teleporting the entity,
     *                 indexes are from {@link ChunkUtils#getChunkIndex(int, int)},
     *                 can be null or empty to only load the chunk at {@code position}
     * @param callback the optional callback executed, even if auto chunk is not enabled
     * @throws IllegalStateException if you try to teleport an entity before settings its instance
     */
    public void teleport(@NotNull Position position, @Nullable long[] chunks, @Nullable Runnable callback) {
        Check.stateCondition(instance == null, "You need to use TachyonEntity#setInstance before teleporting an entity!");

        final ChunkCallback endCallback = (chunk) -> {
            refreshPosition(position);
            refreshView(position.yaw(), position.pitch());

            sendSynchronization();

            OptionalCallback.execute(callback);
        };

        if (chunks == null || chunks.length == 0) {
            instance.loadOptionalChunk(position, endCallback);
        } else {
            ChunkUtils.optionalLoadAll(instance, chunks, null, endCallback);
        }
    }

    public void teleport(@NotNull Position position, @Nullable Runnable callback) {
        teleport(position, null, callback);
    }

    public void teleport(@NotNull Position position) {
        teleport(position, null);
    }

    /**
     * Changes the view of the entity.
     *
     * @param yaw   the new yaw
     * @param pitch the new pitch
     */
    public void setView(float yaw, float pitch) {
        refreshView(yaw, pitch);

        EntityLookPacket entityLookPacket = new EntityLookPacket(getEntityId(), yaw, pitch, onGround);

        EntityHeadLookPacket entityHeadLookPacket = new EntityHeadLookPacket(getEntityId(), yaw);
        sendPacketToViewersAndSelf(entityHeadLookPacket);
        sendPacketToViewersAndSelf(entityLookPacket);
    }

    /**
     * Changes the view of the entity.
     * Only the yaw and pitch are used.
     *
     * @param position the new view
     */
    public void setView(@NotNull Position position) {
        setView(position.yaw(), position.pitch());
    }

    /**
     * When set to true, the entity will automatically get new viewers when they come too close.
     * This can be use to have complete control over which player can see it, without having to deal with
     * raw packets.
     * <p>
     * True by default for all entities.
     * When set to false, it is important to mention that the players will not be removed automatically from its viewers
     * list, you would have to do that manually using {@link #addViewer(Player)} and {@link #removeViewer(Player)}..
     *
     * @return true if the entity is automatically viewable for close players, false otherwise
     */
    public boolean isAutoViewable() {
        return autoViewable;
    }

    /**
     * Makes the entity auto viewable or only manually.
     *
     * @param autoViewable should the entity be automatically viewable for close players
     * @see #isAutoViewable()
     */
    public void setAutoViewable(boolean autoViewable) {
        this.autoViewable = autoViewable;
    }

    @Override
    public final boolean addViewer(@NotNull Player player) {
        synchronized (this.entityTypeLock) {
            return addViewer0(player);
        }
    }

    public boolean addViewer0(@NotNull Player p) {
        TachyonPlayer player = (TachyonPlayer) p;
        if (!this.viewers.add(player)) {
            return false;
        }
        player.viewableEntities.add(this);

        PlayerConnection playerConnection = player.getPlayerConnection();
        playerConnection.sendPacket(getEntityType().getSpawnType().getSpawnPacket(this));
        playerConnection.sendPacket(getVelocityPacket());
        playerConnection.sendPacket(getMetadataPacket());

        if (hasPassenger()) {
            playerConnection.sendPacket(getPassengerPacket());
        }

        return true;
    }

    @Override
    public final boolean removeViewer(@NotNull Player player) {
        synchronized (this.entityTypeLock) {
            return removeViewer0(player);
        }
    }

    public boolean removeViewer0(@NotNull Player p) {
        TachyonPlayer player = (TachyonPlayer) p;
        if (!viewers.remove(player)) {
            return false;
        }

        DestroyEntitiesPacket destroyEntitiesPacket = new DestroyEntitiesPacket(new int[]{getEntityId()});
        player.getPlayerConnection().sendPacket(destroyEntitiesPacket);
        player.viewableEntities.remove(this);
        return true;
    }

    @NotNull
    @Override
    public Set<Player> getViewers() {
        return unmodifiableViewers;
    }

    /**
     * Changes the entity type of this entity.
     * <p>
     * Works by changing the internal entity type field and by calling {@link #removeViewer(Player)}
     * followed by {@link #addViewer(Player)} to all current viewers.
     * <p>
     * Be aware that this only change the visual of the entity, the {@link BoundingBox}
     * will not be modified.
     *
     * @param entityType the new entity type
     */
    public void switchEntityType(@NotNull EntityType entityType) {
        synchronized (entityTypeLock) {
            this.entityType = entityType;
            this.metadata = new Metadata(this);
            this.entityMeta = entityType.getMetaConstructor().apply(this, this.metadata);

            Set<Player> viewers = new HashSet<>(getViewers());
            getViewers().forEach(this::removeViewer0);
            viewers.forEach(this::addViewer0);
        }
    }

    @Override
    public Data getData() {
        return data;
    }

    @Override
    public void setData(@Nullable Data data) {
        this.data = data;
    }

    @NotNull
    @Override
    public Set<Permission> getAllPermissions() {
        return permissions;
    }

    /**
     * Updates the entity, called every tick.
     * <p>
     * Ignored if {@link #getInstance()} returns null.
     *
     * @param time the update time in milliseconds
     */
    public void tick(long time) {
        if (instance == null)
            return;

        // Scheduled remove
        if (scheduledRemoveTime != 0) {
            final boolean finished = time >= scheduledRemoveTime;
            if (finished) {
                remove();
                return;
            }
        }

        // Instant remove
        if (shouldRemove()) {
            remove();
            return;
        }

        // Check if the entity chunk is loaded
        final TachyonChunk currentChunk = getChunk();
        if (!ChunkUtils.isLoaded(currentChunk)) {
            // No update for entities in unloaded chunk
            return;
        }

        // scheduled tasks
        if (!nextTick.isEmpty()) {
            Consumer<TachyonEntity> callback;
            while ((callback = nextTick.poll()) != null) {
                callback.accept(this);
            }
        }

        final boolean isNettyClient = PlayerUtils.isNettyClient(this);

        // Synchronization with updated fields in #getPosition()
        {
            final boolean positionChange = cacheX != position.x() ||
                    cacheY != position.y() ||
                    cacheZ != position.z();
            final boolean viewChange = cacheYaw != position.getYaw() ||
                    cachePitch != position.getPitch();
            final double distance = positionChange ? position.distance(cacheX, cacheY, cacheZ) : 0;

            if (distance >= 4 || (positionChange && isNettyClient)) {
                // Teleport has the priority over everything else
                teleport(position);
            } else if (positionChange && viewChange) {
                EntityLookAndRelativeMove positionAndRotationPacket =
                        EntityLookAndRelativeMove.getPacket(getEntityId(),
                                position, new Position(cacheX, cacheY, cacheZ), isOnGround());

                sendPacketToViewersAndSelf(positionAndRotationPacket);

                refreshPosition(position);

                // Fix head rotation
                EntityHeadLookPacket entityHeadLookPacket = new EntityHeadLookPacket(getEntityId(), position.yaw());

                sendPacketToViewersAndSelf(entityHeadLookPacket);

            } else if (positionChange) {
                EntityRelativeMovePacket entityRelativeMovePacket = EntityRelativeMovePacket.getPacket(getEntityId(),
                        position, new Position(cacheX, cacheY, cacheZ), isOnGround());

                sendPacketToViewersAndSelf(entityRelativeMovePacket);

                refreshPosition(position);

            } else if (viewChange) {
                // Yaw/Pitch
                setView(position);
            }

        }

        // TachyonEntity tick
        {

            // Cache the number of "gravity tick"
            if (!onGround) {
                gravityTickCount++;
            } else {
                gravityTickCount = 0;
            }

            // Velocity
            boolean applyVelocity;
            // Non-player entities with either velocity or gravity enabled
            applyVelocity = !isNettyClient && (hasVelocity() || !hasNoGravity());
            // Players with a velocity applied (client is responsible for gravity)
            applyVelocity |= isNettyClient && hasVelocity();

            if (applyVelocity) {
                final float tps = Tachyon.getServer().getTargetTPS();
                final double newX = position.getX() + velocity.getX() / tps;
                final double newY = position.getY() + velocity.getY() / tps;
                final double newZ = position.getZ() + velocity.getZ() / tps;
                Position newPosition = new Position(newX, newY, newZ);

                Vec newVelocityOut = Vec.ZERO;

                // Gravity force
                final double gravityY = !hasNoGravity() ? Math.min(
                        gravityDragPerTick + (gravityAcceleration * (double) gravityTickCount),
                        gravityTerminalVelocity) : 0;

                final Vec deltaPos = new Vec(
                        getVelocity().getX() / tps,
                        (getVelocity().getY() - gravityY) / tps,
                        getVelocity().getZ() / tps
                );

                if (this.hasPhysics) {
                    this.onGround = CollisionUtils.handlePhysics(this, deltaPos, newPosition, newVelocityOut);
                } else {
                    newVelocityOut = deltaPos;
                }

                // World border collision
                final Position finalVelocityPosition = CollisionUtils.applyWorldBorder(instance, position, newPosition);
                final TachyonChunk finalChunk = instance.getChunkAt(finalVelocityPosition);

                // TachyonEntity shouldn't be updated when moving in an unloaded chunk
                if (!ChunkUtils.isLoaded(finalChunk)) {
                    return;
                }

                // Apply the position if changed
                if (!newPosition.isSimilar(position)) {
                    refreshPosition(finalVelocityPosition);
                }


                // Update velocity
                if (hasVelocity() || !newVelocityOut.isZero()) {
                    this.velocity = newVelocityOut;
                    this.velocity.multiply(tps);

                    float drag;
                    if (onGround) {
                        final Vec blockPosition = position.toVector();
                        final CustomBlock customBlock = finalChunk.getCustomBlock(
                                blockPosition.blockX(),
                                blockPosition.blockY(),
                                blockPosition.blockZ());
                        if (customBlock != null) {
                            // Custom drag
                            drag = customBlock.getDrag(instance, blockPosition);
                        } else {
                            // Default ground drag
                            drag = 0.5f;
                        }

                        // Stop player velocity
                        if (isNettyClient) {
                            this.velocity = Vec.ZERO;
                        }
                    } else {
                        drag = 0.98f; // air drag
                    }

                    this.velocity = new Vec(
                            velocity.getX() * drag,
                            velocity.getY(),
                            velocity.getZ() * drag
                    );
                    if (velocity.equals(Point.ZERO)) {
                        this.velocity = Point.ZERO;
                    }
                }

                // Synchronization and packets...
                if (!isNettyClient) {
                    sendSynchronization();
                }
                // Verify if velocity packet has to be sent
                if (hasVelocity() || (!isNettyClient && gravityTickCount > 0)) {
                    sendPacketToViewersAndSelf(getVelocityPacket());
                }
            }

            // handle block contacts
            // TODO do not call every tick (it is pretty expensive)
            final int minX = (int) Math.floor(boundingBox.getMinX());
            final int maxX = (int) Math.ceil(boundingBox.getMaxX());
            final int minY = (int) Math.floor(boundingBox.getMinY());
            final int maxY = (int) Math.ceil(boundingBox.getMaxY());
            final int minZ = (int) Math.floor(boundingBox.getMinZ());
            final int maxZ = (int) Math.ceil(boundingBox.getMaxZ());
            Vec tmpPosition = new Vec(0, 0, 0); // allow reuse
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        final Chunk chunk = instance.getChunkAt(x, z);
                        if (!ChunkUtils.isLoaded(chunk))
                            continue;

                        final CustomBlock customBlock = chunk.getCustomBlock(x, y, z);
                        if (customBlock != null) {
                            tmpPosition = new Vec(x, y, z);
                            // checks that we are actually in the block, and not just here because of a rounding error
                            if (boundingBox.intersect(tmpPosition.getX(), tmpPosition.getY(), tmpPosition.getZ())) {
                                // TODO: replace with check with custom block bounding box
                                customBlock.handleContact(instance, tmpPosition, this);
                            }
                        }
                    }
                }
            }

            handleVoid();

            // Call the abstract update method
            update(time);

            ticks++;
            callEvent(EntityTickEvent.class, tickEvent); // reuse tickEvent to avoid recreating it each tick

            // remove expired effects
            {
                this.effects.removeIf(timedPotion -> {
                    final long potionTime = (long) timedPotion.potion().getDuration() * Tachyon.getServer().getTickMs();
                    // Remove if the potion should be expired
                    if (time >= timedPotion.startingTime() + potionTime) {
                        // Send the packet that the potion should no longer be applied
                        timedPotion.potion().sendRemovePacket(this);
                        callEvent(EntityPotionRemoveEvent.class, new EntityPotionRemoveEvent(
                                this,
                                timedPotion.potion()
                        ));
                        return true;
                    }
                    return false;
                });
            }
        }

        // Scheduled synchronization
        if (!CooldownUtils.hasCooldown(time, lastAbsoluteSynchronizationTime, getSynchronizationCooldown())) {
            this.lastAbsoluteSynchronizationTime = time;
            sendSynchronization();
        }

        if (shouldRemove() && !Tachyon.getServer().isStopping()) {
            remove();
        }
    }

    /**
     * Gets the number of ticks this entity has been active for.
     *
     * @return the number of ticks this entity has been active for
     */
    public long getAliveTicks() {
        return ticks;
    }

    @ApiStatus.Internal
    public void UNSAFE_modifyPosition(@NotNull Position newPosition) {
        this.position = newPosition;
    }

    /**
     * How does this entity handle being in the void?
     */
    protected void handleVoid() {
        // Kill if in void
        if (getInstance().isInVoid(this.position)) {
            remove();
        }
    }

    @NotNull
    @Override
    public Map<Class<? extends Event>, Collection<EventCallback>> getEventCallbacksMap() {
        return eventCallbacks;
    }

    /**
     * Each entity has an unique id (server-wide) which will change after a restart.
     *
     * @return the unique entity id
     * @see TachyonEntity#getEntity(int) to retrive an entity based on its id
     */
    public int getEntityId() {
        return id;
    }

    /**
     * Returns the entity type.
     *
     * @return the entity type
     */
    @NotNull
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Gets the entity {@link UUID}.
     *
     * @return the entity unique id
     */
    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Changes the internal entity UUID, mostly unsafe.
     *
     * @param uuid the new entity uuid
     */
    public void setUuid(@NotNull UUID uuid) {
        // Refresh internal map
        TachyonEntity.entityByUuid.remove(this.uuid);
        TachyonEntity.entityByUuid.put(uuid, this);

        this.uuid = uuid;
    }

    /**
     * Returns false just after instantiation, set to true after calling {@link #setInstance(Instance)}.
     *
     * @return true if the entity has been linked to an instance, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Is used to check collision with coordinates or other blocks/entities.
     *
     * @return the entity bounding box
     */
    @NotNull
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * Changes the internal entity bounding box.
     * <p>
     * WARNING: this does not change the entity hit-box which is client-side.
     *
     * @param x the bounding box X size
     * @param y the bounding box Y size
     * @param z the bounding box Z size
     */
    public void setBoundingBox(double x, double y, double z) {
        this.boundingBox = new BoundingBox(this, x, y, z);
    }

    /**
     * Changes the internal entity bounding box.
     * <p>
     * WARNING: this does not change the entity hit-box which is client-side.
     *
     * @param boundingBox the new bounding box
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * Convenient method to get the entity current chunk.
     *
     * @return the entity chunk, can be null even if unlikely
     */
    @Nullable
    public TachyonChunk getChunk() {
        return (TachyonChunk) instance.getChunkAt(position.getX(), position.getZ());
    }

    /**
     * Gets the entity current instance.
     *
     * @return the entity instance, can be null if the entity doesn't have an instance yet
     */
    @Nullable
    public Instance getInstance() {
        return instance;
    }

    /**
     * Changes the entity instance, i.e. spawns it.
     *
     * @param instance      the new instance of the entity
     * @param spawnPosition the spawn position for the entity.
     * @throws IllegalStateException if {@code instance} has not been registered in {@link InstanceManager}
     */
    public void setInstance(@NotNull Instance instance, @NotNull Position spawnPosition) {
        Check.stateCondition(!instance.isRegistered(),
                "Instances need to be registered, please use InstanceManager#registerInstance or InstanceManager#registerSharedInstance");

        if (this.instance != null) {
            this.instance.UNSAFE_removeEntity(this);
        }

        this.position = spawnPosition;
        this.lastX = this.position.getX();
        this.lastY = this.position.getY();
        this.lastZ = this.position.getZ();
        this.lastYaw = this.position.getYaw();
        this.lastPitch = this.position.getPitch();

        this.isActive = true;
        this.instance = instance;
        instance.UNSAFE_addEntity(this);
        spawn();
        EntitySpawnEvent entitySpawnEvent = new EntitySpawnEvent(this, instance);
        callEvent(EntitySpawnEvent.class, entitySpawnEvent);
    }

    /**
     * Changes the entity instance.
     *
     * @param instance the new instance of the entity
     * @throws NullPointerException  if {@code instance} is null
     * @throws IllegalStateException if {@code instance} has not been registered in {@link InstanceManager}
     */
    public void setInstance(@NotNull Instance instance) {
        setInstance(instance, this.position);
    }

    /**
     * Gets the entity current velocity.
     *
     * @return the entity current velocity
     */
    @NotNull
    public Vec getVelocity() {
        return velocity;
    }

    /**
     * Changes the entity velocity and calls {@link EntityVelocityEvent}.
     * <p>
     * The final velocity can be cancelled or modified by the event.
     *
     * @param velocity the new entity velocity
     */
    public void setVelocity(@NotNull Vec velocity) {
        EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent(this, velocity);
        callCancellableEvent(EntityVelocityEvent.class, entityVelocityEvent, () -> {
            this.velocity = entityVelocityEvent.getVelocity();
            sendPacketToViewersAndSelf(getVelocityPacket());
        });
    }

    /**
     * Gets if the entity currently has a velocity applied.
     *
     * @return true if velocity is not set to 0
     */
    public boolean hasVelocity() {
        return !velocity.isZero();
    }

    /**
     * Gets the gravity drag per tick.
     *
     * @return the gravity drag per tick in block
     */
    public double getGravityDragPerTick() {
        return gravityDragPerTick;
    }

    @Override
    public double getDistance(@NotNull Entity other) {
        return 0;
    }

    /**
     * Gets the gravity acceleration.
     *
     * @return the gravity acceleration in block
     */
    public double getGravityAcceleration() {
        return gravityAcceleration;
    }

    /**
     * Gets the maximum gravity velocity.
     *
     * @return the maximum gravity velocity in block
     */
    public double getGravityTerminalVelocity() {
        return gravityTerminalVelocity;
    }

    /**
     * Gets the number of tick this entity has been applied gravity.
     *
     * @return the number of tick of which gravity has been consequently applied
     */
    public int getGravityTickCount() {
        return gravityTickCount;
    }

    public boolean hasNoGravity() {
        return false;
    }

    /**
     * Changes the gravity of the entity.
     *
     * @param gravityDragPerTick      the gravity drag per tick in block
     * @param gravityAcceleration     the gravity acceleration in block
     * @param gravityTerminalVelocity the gravity terminal velocity (maximum) in block
     * @see <a href="https://minecraft.gamepedia.com/Entity#Motion_of_entities">Entities motion</a>
     */
    public void setGravity(double gravityDragPerTick, double gravityAcceleration, double gravityTerminalVelocity) {
        this.gravityDragPerTick = gravityDragPerTick;
        this.gravityAcceleration = gravityAcceleration;
        this.gravityTerminalVelocity = gravityTerminalVelocity;
    }

    /**
     * Gets the distance between two entities.
     *
     * @param entity the entity to get the distance from
     * @return the distance between this and {@code entity}
     */
    public double getDistance(@NotNull TachyonEntity entity) {
        return getPosition().distance(entity.getPosition());
    }

    /**
     * Gets the distance squared between two entities.
     *
     * @param entity the entity to get the distance from
     * @return the distance squared between this and {@code entity}
     */
    public double getDistanceSquared(@NotNull TachyonEntity entity) {
        return getPosition().distanceSquared(entity.getPosition());
    }

    /**
     * Gets the entity vehicle or null.
     *
     * @return the entity vehicle, or null if there is not any
     */
    @Nullable
    public TachyonEntity getVehicle() {
        return vehicle;
    }

    /**
     * Sets the passenger of this entity.
     *
     * @param entity the new passenger
     * @throws IllegalStateException if {@link #getInstance()} returns null
     */
    public void setPassenger(@Nullable Entity entity) {
        Check.stateCondition(instance == null, "You need to set an instance using TachyonEntity#setInstance");
        TachyonEntity entity0 = (TachyonEntity) entity;
        if (entity0 != null) {
            if (entity0.vehicle != null) {
                entity0.vehicle.setPassenger(null);
            }

            entity0.vehicle = this;
        }
        else {
            this.passenger.vehicle = null;
        }
        this.passenger = entity0;
        sendPacketToViewersAndSelf(getPassengerPacket());
    }

    @NotNull
    protected AttachEntityPacket getPassengerPacket() {
        return new AttachEntityPacket(
                passenger != null ? passenger.getEntityId() : -1,
                getEntityId(),
                false
        );
    }

    /**
     * Gets if the entity has a passenger.
     *
     * @return true if the entity has a passenger, false otherwise
     */
    public boolean hasPassenger() {
        return this.passenger != null;
    }

    /**
     * Gets the entity's passenger.
     *
     * @return the entity's passenger, or null if the entity doesn't have a passenger
     */
    @Nullable
    public TachyonEntity getPassenger() {
        return passenger;
    }

    /**
     * TachyonEntity statuses can be found <a href="https://wiki.vg/Entity_statuses">here</a>.
     *
     * @param status the status to trigger
     */
    public void triggerStatus(byte status) {
        EntityStatusPacket statusPacket = new EntityStatusPacket(getEntityId(), status);
        sendPacketToViewersAndSelf(statusPacket);
    }

    /**
     * Gets if the entity is on fire.
     *
     * @return true if the entity is in fire, false otherwise
     */
    public boolean isOnFire() {
        return this.entityMeta.isOnFire();
    }

    /**
     * Sets the entity in fire visually.
     * <p>
     * WARNING: if you want to apply damage or specify a duration,
     * see {@link TachyonLivingEntity#setFireForDuration(int, TimeUnit)}.
     *
     * @param fire should the entity be set in fire
     */
    public void setOnFire(boolean fire) {
        this.entityMeta.setOnFire(fire);
    }

    /**
     * Gets if the entity is sneaking.
     * <p>
     * WARNING: this can be bypassed by hacked client, this is only what the client told the server.
     *
     * @return true if the player is sneaking
     */
    public boolean isSneaking() {
        return this.entityMeta.isSneaking();
    }

    /**
     * Makes the entity sneak.
     * <p>
     * WARNING: this will not work for the client itself.
     *
     * @param sneaking true to make the entity sneak
     */
    public void setSneaking(boolean sneaking) {
        this.entityMeta.setSneaking(sneaking);
    }

    /**
     * Gets if the player is sprinting.
     * <p>
     * WARNING: this can be bypassed by hacked client, this is only what the client told the server.
     *
     * @return true if the player is sprinting
     */
    public boolean isSprinting() {
        return this.entityMeta.isSprinting();
    }

    /**
     * Makes the entity sprint.
     * <p>
     * WARNING: this will not work on the client itself.
     *
     * @param sprinting true to make the entity sprint
     */
    public void setSprinting(boolean sprinting) {
        this.entityMeta.setSprinting(sprinting);
    }

    /**
     * Gets if the entity is invisible or not.
     *
     * @return true if the entity is invisible, false otherwise
     */
    public boolean isInvisible() {
        return this.entityMeta.isInvisible();
    }

    /**
     * Changes the internal invisible value and send a {@link EntityMetaDataPacket}
     * to make visible or invisible the entity to its viewers.
     *
     * @param invisible true to set the entity invisible, false otherwise
     */
    public void setInvisible(boolean invisible) {
        this.entityMeta.setInvisible(invisible);
    }

    /**
     * Gets the entity custom name.
     *
     * @return the custom name of the entity, null if there is not
     */
    @Nullable
    public Component getCustomName() {
        return this.entityMeta.getCustomName();
    }

    /**
     * Changes the entity custom name.
     *
     * @param customName the custom name of the entity, null to remove it
     */
    public void setCustomName(@Nullable Component customName) {
        this.entityMeta.setCustomName(customName);
    }

    /**
     * Gets the custom name visible metadata field.
     *
     * @return true if the custom name is visible, false otherwise
     */
    public boolean isCustomNameVisible() {
        return this.entityMeta.isCustomNameVisible();
    }

    /**
     * Changes the internal custom name visible field and send a {@link EntityMetaDataPacket}
     * to update the entity state to its viewers.
     *
     * @param customNameVisible true to make the custom name visible, false otherwise
     */
    public void setCustomNameVisible(boolean customNameVisible) {
        this.entityMeta.setCustomNameVisible(customNameVisible);
    }

    public boolean isSilent() {
        return this.entityMeta.isSilent();
    }

    public void setSilent(boolean silent) {
        this.entityMeta.setSilent(silent);
    }

    /**
     * Used to refresh the entity and its passengers position
     * - put the entity in the right instance chunk
     * - update the viewable chunks (load and unload)
     * - add/remove players from the viewers list if {@link #isAutoViewable()} is enabled
     * <p>
     * WARNING: unsafe, should only be used internally in Minestom. Use {@link #teleport(Position)} instead.
     *
     * @param x new position X
     * @param y new position Y
     * @param z new position Z
     */
    public void refreshPosition(double x, double y, double z) {
        this.position = new Position(x, y, z, this.position.yaw(), this.position.pitch());
        this.cacheX = x;
        this.cacheY = y;
        this.cacheZ = z;

        if (passenger != null) {
            passenger.refreshPosition(x, y, z);
        }

        final Instance instance = getInstance();
        if (instance != null) {
            final TachyonChunk lastChunk = (TachyonChunk) instance.getChunkAt(lastX, lastZ);
            final TachyonChunk newChunk = (TachyonChunk) instance.getChunkAt(x, z);

            Check.notNull(lastChunk, "The entity " + getEntityId() + " was in an unloaded chunk at " + lastX + ";" + lastZ);
            Check.notNull(newChunk, "The entity " + getEntityId() + " tried to move in an unloaded chunk at " + x + ";" + z);

            if (lastChunk != newChunk) {
                instance.switchEntityChunk(this, lastChunk, newChunk);
                if (this instanceof TachyonPlayer player) {
                    // Refresh player view
                    player.refreshVisibleChunks(newChunk);
                    player.refreshVisibleEntities(newChunk);
                }
            }
        }

        this.lastX = position.x();
        this.lastY = position.y();
        this.lastZ = position.z();
    }

    /**
     * @param position the new position
     * @see #refreshPosition(double, double, double)
     */
    public void refreshPosition(@NotNull Position position) {
        refreshPosition(position.x(), position.y(), position.z());
    }

    /**
     * Updates the entity view internally.
     * <p>
     * Warning: you probably want to use {@link #setView(float, float)}.
     *
     * @param yaw   the yaw
     * @param pitch the pitch
     */
    public void refreshView(float yaw, float pitch) {
        this.lastYaw = position.yaw();
        this.lastPitch = position.pitch();
        this.position = new Position(this.position.x(), this.position.y(), this.position.z(), yaw, pitch);
        this.cacheYaw = yaw;
        this.cachePitch = pitch;
    }

    /**
     * Gets the entity position.
     *
     * @return the current position of the entity
     */
    @NotNull
    public Position getPosition() {
        return position;
    }

    @Override
    public @NotNull Position getLastLocation() {
        return null;
    }

    /**
     * Gets the entity eye height.
     * <p>
     * Default to {@link BoundingBox#getHeight()}x0.85
     *
     * @return the entity eye height
     */
    public double getEyeHeight() {
        return boundingBox.getHeight() * 0.85;
    }

    /**
     * Gets all the potion effect of this entity.
     *
     * @return an unmodifiable list of all this entity effects
     */
    @NotNull
    public List<TimedPotion> getActiveEffects() {
        return Collections.unmodifiableList(effects);
    }

    @Override
    public @NotNull World getWorld() {
        return instance;
    }

    /**
     * Adds an effect to an entity.
     *
     * @param potion The potion to add
     */
    public void addEffect(@NotNull Potion potion) {
        removeEffect(potion.getEffect());
        this.effects.add(new TimedPotion(potion, System.currentTimeMillis()));
        potion.sendAddPacket(this);
        callEvent(EntityPotionAddEvent.class, new EntityPotionAddEvent(this, potion));
    }

    /**
     * Removes effect from entity, if it has it.
     *
     * @param effect The effect to remove
     */
    public void removeEffect(@NotNull PotionEffect effect) {
        this.effects.removeIf(timedPotion -> {
            if (timedPotion.potion().getEffect() == effect) {
                timedPotion.potion().sendRemovePacket(this);
                callEvent(EntityPotionRemoveEvent.class, new EntityPotionRemoveEvent(
                        this,
                        timedPotion.potion()
                ));
                return true;
            }
            return false;
        });
    }

    /**
     * Removes all the effects currently applied to the entity.
     */
    public void clearEffects() {
        for (TimedPotion timedPotion : effects) {
            timedPotion.potion().sendRemovePacket(this);
            callEvent(EntityPotionRemoveEvent.class, new EntityPotionRemoveEvent(
                    this,
                    timedPotion.potion()
            ));
        }
        this.effects.clear();
    }

    /**
     * Removes the entity from the server immediately.
     * <p>
     * WARNING: this does not trigger {@link EntityDeathEvent}.
     */
    public void remove() {
        this.removed = true;
        this.shouldRemove = true;
        TachyonEntity.entityById.remove(id);
        TachyonEntity.entityByUuid.remove(uuid);
        if (instance != null)
            instance.UNSAFE_removeEntity(this);
    }

    /**
     * Gets if this entity has been removed.
     *
     * @return true if this entity is removed
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * Triggers {@link #remove()} after the specified time.
     *
     * @param delay    the time before removing the entity,
     *                 0 to cancel the removing
     * @param timeUnit the unit of the delay
     */
    public void scheduleRemove(long delay, @NotNull TimeUnit timeUnit) {
        if (delay == 0) { // Cancel the scheduled remove
            this.scheduledRemoveTime = 0;
            return;
        }
        this.scheduledRemoveTime = System.currentTimeMillis() + timeUnit.toMilliseconds(delay);
    }

    /**
     * Gets if the entity removal has been scheduled with {@link #scheduleRemove(long, TimeUnit)}.
     *
     * @return true if the entity removal has been scheduled
     */
    public boolean isRemoveScheduled() {
        return scheduledRemoveTime != 0;
    }

    @NotNull
    protected Vec getVelocityForPacket() {
        return this.velocity.multiply(8000f / Tachyon.getServer().getTickMs());
    }

    @NotNull
    protected EntityVelocityPacket getVelocityPacket() {
        Vec velocity = getVelocityForPacket();
        return new EntityVelocityPacket(getEntityId(), (short) velocity.x(), (short) velocity.y(), (short) velocity.z());
    }

    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Gets an {@link EntityMetaDataPacket} sent when adding viewers. Used for synchronization.
     *
     * @return The {@link EntityMetaDataPacket} related to this entity
     */
    @NotNull
    public EntityMetaDataPacket getMetadataPacket() {
        EntityMetaDataPacket metaDataPacket = new EntityMetaDataPacket();
        metaDataPacket.entityId = getEntityId();
        metaDataPacket.entries = metadata.getEntries();
        return metaDataPacket;
    }

    protected void sendSynchronization() {
        EntityTeleportPacket entityTeleportPacket = new EntityTeleportPacket(getEntityId(), getPosition(), isOnGround());
        sendPacketToViewers(entityTeleportPacket);
    }

    /**
     * Asks for a synchronization (position) to happen during next entity tick.
     */
    public void askSynchronization() {
        this.lastAbsoluteSynchronizationTime = 0;
    }

    /**
     * Set custom cooldown for position synchronization.
     *
     * @param cooldown custom cooldown for position synchronization.
     */
    public void setCustomSynchronizationCooldown(@Nullable UpdateOption cooldown) {
        this.customSynchronizationCooldown = cooldown;
    }

    private UpdateOption getSynchronizationCooldown() {
        return Objects.requireNonNullElse(this.customSynchronizationCooldown, SYNCHRONIZATION_COOLDOWN);
    }

    protected boolean shouldRemove() {
        return shouldRemove;
    }

    @Override
    public boolean hasPhysics() {
        return hasPhysics;
    }

}
