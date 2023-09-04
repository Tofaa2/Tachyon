package net.tachyon.instance;

import com.google.common.collect.Queues;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.UpdateManager;
import net.tachyon.block.BlockManager;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import net.tachyon.data.Data;
import net.tachyon.data.DataContainer;
import net.tachyon.entity.*;
import net.tachyon.block.Block;
import net.tachyon.block.CustomBlock;
import net.tachyon.entity.pathfinding.PFInstanceSpace;
import net.tachyon.event.Event;
import net.tachyon.event.EventCallback;
import net.tachyon.event.handler.EventHandler;
import net.tachyon.event.instance.AddEntityToInstanceEvent;
import net.tachyon.event.instance.InstanceTickEvent;
import net.tachyon.event.instance.RemoveEntityFromInstanceEvent;
import net.tachyon.event.player.PlayerBlockBreakEvent;
import net.tachyon.network.packet.server.play.BlockActionPacket;
import net.tachyon.network.packet.server.play.TimeUpdatePacket;
import net.tachyon.storage.StorageLocation;
import net.tachyon.thread.ThreadProvider;
import net.tachyon.utils.PacketUtils;
import net.tachyon.world.chunk.ChunkCallback;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.utils.entity.EntityUtils;
import net.tachyon.utils.time.CooldownUtils;
import net.tachyon.utils.time.TimeUnit;
import net.tachyon.utils.time.UpdateOption;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.DimensionType;
import net.tachyon.world.LevelType;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * Instances are what are called "worlds" in Minecraft, you can add an entity in it using {@link TachyonEntity#setInstance(Instance)}.
 * <p>
 * An instance has entities and chunks, each instance contains its own entity list but the
 * chunk implementation has to be defined, see {@link InstanceContainer}.
 * <p>
 * WARNING: when making your own implementation registering the instance manually is required
 * with {@link InstanceManager#registerInstance(Instance)}, and
 * you need to be sure to signal the {@link UpdateManager} of the changes using
 * {@link UpdateManager#signalChunkLoad(Instance, int, int)} and {@link UpdateManager#signalChunkUnload(Instance, int, int)}.
 */
public abstract class Instance implements EventHandler, DataContainer, World {

    protected static final BlockManager BLOCK_MANAGER = Tachyon.getServer().getBlockmanager();
    protected static final UpdateManager UPDATE_MANAGER = MinecraftServer.getUpdateManager();

    private boolean registered;

    private final DimensionType dimensionType;
    private final LevelType levelType;

    private final InstanceWorldBorder worldBorder;

    // Tick since the creation of the instance
    private long worldAge;

    // The time of the instance
    private long time;
    private int timeRate = 1;
    private UpdateOption timeUpdate = new UpdateOption(1, TimeUnit.SECOND);
    private long lastTimeUpdate;

    // Field for tick events
    private long lastTickAge = System.currentTimeMillis();

    private final Map<Class<? extends Event>, Collection<EventCallback>> eventCallbacks = new ConcurrentHashMap<>();
    private final Map<String, Collection<EventCallback<?>>> extensionCallbacks = new ConcurrentHashMap<>();

    // Entities present in this instance
    protected final Set<TachyonEntity> entities = new CopyOnWriteArraySet<>();
    protected final Set<Player> players = new CopyOnWriteArraySet<>();
    protected final Set<EntityCreature> creatures = new CopyOnWriteArraySet<>();
    protected final Set<ObjectEntity> objectEntities = new CopyOnWriteArraySet<>();
    protected final Set<ExperienceOrb> experienceOrbs = new CopyOnWriteArraySet<>();
    // Entities per chunk
    protected final Long2ObjectMap<Set<TachyonEntity>> chunkEntities = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
    private final Object entitiesLock = new Object(); // Lock used to prevent the entities Set and Map to be subject to race condition

    // the uuid of this instance
    protected UUID uniqueId;

    // list of scheduled tasks to be executed during the next instance tick
    protected final Queue<Consumer<Instance>> nextTick = Queues.newConcurrentLinkedQueue();

    // instance custom data
    private Data data;

    // the explosion supplier
    private ExplosionSupplier explosionSupplier;

    // Pathfinder
    private final PFInstanceSpace instanceSpace = new PFInstanceSpace(this);

    /**
     * Creates a new instance.
     *
     * @param uniqueId      the {@link UUID} of the instance
     * @param dimensionType the {@link DimensionType} of the instance
     * @param levelType     the {@link LevelType} of the instance
     */
    public Instance(@NotNull UUID uniqueId, @NotNull DimensionType dimensionType, @NotNull LevelType levelType) {
        this.uniqueId = uniqueId;
        this.dimensionType = dimensionType;
        this.levelType = levelType;

        this.worldBorder = new InstanceWorldBorder(this);
    }

    /**
     * Schedules a task to be run during the next instance tick.
     * It ensures that the task will be executed in the same thread as the instance
     * and its chunks/entities (depending of the {@link ThreadProvider}).
     *
     * @param callback the task to execute during the next instance tick
     */
    public void scheduleNextTick(@NotNull Consumer<Instance> callback) {
        this.nextTick.add(callback);
    }

    /**
     * Used to change the id of the block in a specific {@link Point}.
     * <p>
     * In case of a {@link CustomBlock} it does not remove it but only refresh its visual.
     *
     * @param blockPosition the block position
     * @param blockStateId  the new block state
     */
    public abstract void refreshBlockStateId(@NotNull Point blockPosition, short blockStateId);

    /**
     * Does call {@link PlayerBlockBreakEvent}
     * and send particle packets
     *
     * @param player        the {@link TachyonPlayer} who break the block
     * @param blockPosition the {@link Point} of the broken block
     * @return true if the block has been broken, false if it has been cancelled
     */
    public abstract boolean breakBlock(@NotNull Player player, @NotNull Point blockPosition);

    /**
     * Forces the generation of a {@link TachyonChunk}, even if no file and {@link ChunkGenerator} are defined.
     *
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @param callback optional consumer called after the chunk has been generated,
     *                 the returned chunk will never be null
     */
    public abstract void loadChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback);

    /**
     * Loads the chunk if the chunk is already loaded or if
     * {@link #hasEnabledAutoChunkLoad()} returns true.
     *
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @param callback optional consumer called after the chunk has tried to be loaded,
     *                 contains a chunk if it is successful, null otherwise
     */
    public abstract void loadOptionalChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback);

    /**
     * Schedules the removal of a {@link TachyonChunk}, this method does not promise when it will be done.
     * <p>
     * WARNING: during unloading, all entities other than {@link TachyonPlayer} will be removed.
     * <p>
     * For {@link InstanceContainer} it is done during the next {@link InstanceContainer#tick(long)}.
     *
     * @param chunk the chunk to unload
     */
    public abstract void unloadChunk(@NotNull TachyonChunk chunk);

    /**
     * Gets the loaded {@link TachyonChunk} at a position.
     * <p>
     * WARNING: this should only return already-loaded chunk, use {@link #loadChunk(int, int)} or similar to load one instead.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return the chunk at the specified position, null if not loaded
     */
    @Nullable
    public abstract Chunk getChunk(int chunkX, int chunkZ);

    /**
     * Saves a {@link TachyonChunk} to permanent storage.
     *
     * @param chunk    the {@link TachyonChunk} to save
     * @param callback optional callback called when the {@link TachyonChunk} is done saving
     */
    public abstract void saveChunkToStorage(@NotNull TachyonChunk chunk, @Nullable Runnable callback);

    /**
     * Saves multiple chunks to permanent storage.
     *
     * @param callback optional callback called when the chunks are done saving
     */
    public abstract void saveChunksToStorage(@Nullable Runnable callback);

    /**
     * Gets the instance {@link ChunkGenerator}.
     *
     * @return the {@link ChunkGenerator} of the instance
     */
    @Nullable
    public abstract ChunkGenerator getChunkGenerator();

    /**
     * Changes the instance {@link ChunkGenerator}.
     *
     * @param chunkGenerator the new {@link ChunkGenerator} of the instance
     */
    public abstract void setChunkGenerator(@Nullable ChunkGenerator chunkGenerator);

    /**
     * Gets all the instance's loaded chunks.
     *
     * @return an unmodifiable containing all the instance chunks
     */
    @NotNull
    public abstract Collection<Chunk> getChunks();

    /**
     * Gets the instance {@link StorageLocation}.
     *
     * @return the {@link StorageLocation} of the instance
     */
    @Nullable
    public abstract StorageLocation getStorageLocation();

    /**
     * Changes the instance {@link StorageLocation}.
     *
     * @param storageLocation the new {@link StorageLocation} of the instance
     */
    public abstract void setStorageLocation(@Nullable StorageLocation storageLocation);

    /**
     * Used when a {@link TachyonChunk} is not currently loaded in memory and need to be retrieved from somewhere else.
     * Could be read from disk, or generated from scratch.
     * <p>
     * Be sure to signal the chunk using {@link UpdateManager#signalChunkLoad(Instance, int, int)} and to cache
     * that this chunk has been loaded.
     * <p>
     * WARNING: it has to retrieve a chunk, this is not optional and should execute the callback in all case.
     *
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk X
     * @param callback the optional callback executed once the {@link TachyonChunk} has been retrieved
     */
    protected abstract void retrieveChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback);

    /**
     * Called to generated a new {@link TachyonChunk} from scratch.
     * <p>
     * Be sure to signal the chunk using {@link UpdateManager#signalChunkLoad(Instance, int, int)} and to cache
     * that this chunk has been loaded.
     * <p>
     * This is where you can put your chunk generation code.
     *
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @param callback the optional callback executed with the newly created {@link TachyonChunk}
     */
    protected abstract void createChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback);

    /**
     * When set to true, chunks will load automatically when requested.
     * Otherwise using {@link #loadChunk(int, int)} will be required to even spawn a player
     *
     * @param enable enable the auto chunk load
     */
    public abstract void enableAutoChunkLoad(boolean enable);

    /**
     * Gets if the instance should auto load chunks.
     *
     * @return true if auto chunk load is enabled, false otherwise
     */
    public abstract boolean hasEnabledAutoChunkLoad();

    /**
     * Determines whether a position in the void. If true, entities should take damage and die.
     * <p>
     * Always returning false allow entities to survive in the void.
     *
     * @param position the position in the world
     * @return true iif position is inside the void
     */
    public abstract boolean isInVoid(@NotNull Point position);

    /**
     * Gets if the instance has been registered in {@link InstanceManager}.
     *
     * @return true if the instance has been registered
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * Changes the registered field.
     * <p>
     * WARNING: should only be used by {@link InstanceManager}.
     *
     * @param registered true to mark the instance as registered
     */
    protected void setRegistered(boolean registered) {
        this.registered = registered;
    }

    /**
     * Gets the instance {@link DimensionType}.
     *
     * @return the dimension of the instance
     */
    @NotNull
    public DimensionType getDimensionType() {
        return dimensionType;
    }

    /**
     * Gets the instance {@link LevelType}.
     *
     * @return the level type of the instance
     */
    @NotNull
    public LevelType getLevelType() {
        return levelType;
    }

    /**
     * Gets the age of this instance in tick.
     *
     * @return the age of this instance in tick
     */
    @Override
    public long getWorldAge() {
        return worldAge;
    }

    /**
     * Gets the current time in the instance (sun/moon).
     *
     * @return the time in the instance
     */
    public long getTime() {
        return time;
    }

    /**
     * Changes the current time in the instance, from 0 to 24000.
     * <p>
     * If the time is negative, the vanilla client will not move the sun.
     * <p>
     * 0 = sunrise
     * 6000 = noon
     * 12000 = sunset
     * 18000 = midnight
     * <p>
     * This method is unaffected by {@link #getTimeRate()}
     * <p>
     * It does send the new time to all players in the instance, unaffected by {@link #getTimeUpdate()}
     *
     * @param time the new time of the instance
     */
    public void setTime(long time) {
        this.time = time;
        PacketUtils.sendGroupedPacket(getPlayers(), createTimePacket());
    }

    /**
     * Gets the rate of the time passing, it is 1 by default
     *
     * @return the time rate of the instance
     */
    public int getTimeRate() {
        return timeRate;
    }

    /**
     * Changes the time rate of the instance
     * <p>
     * 1 is the default value and can be set to 0 to be completely disabled (constant time)
     *
     * @param timeRate the new time rate of the instance
     * @throws IllegalStateException if {@code timeRate} is lower than 0
     */
    @Override
    public void setTimeRate(int timeRate) {
        Check.stateCondition(timeRate < 0, "The time rate cannot be lower than 0");
        this.timeRate = timeRate;
    }

    /**
     * Gets the rate at which the client is updated with the current instance time
     *
     * @return the client update rate for time related packet
     */
    @Nullable
    public UpdateOption getTimeUpdate() {
        return timeUpdate;
    }

    /**
     * Changes the rate at which the client is updated about the time
     * <p>
     * Setting it to null means that the client will never know about time change
     * (but will still change server-side)
     *
     * @param timeUpdate the new update rate concerning time
     */
    public void setTimeUpdate(@Nullable UpdateOption timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    /**
     * Creates a {@link TimeUpdatePacket} with the current age and time of this instance
     *
     * @return the {@link TimeUpdatePacket} with this instance data
     */
    @NotNull
    private TimeUpdatePacket createTimePacket() {
        TimeUpdatePacket timeUpdatePacket = new TimeUpdatePacket();
        timeUpdatePacket.worldAge = worldAge;
        timeUpdatePacket.timeOfDay = time;
        return timeUpdatePacket;
    }

    /**
     * Gets the instance {@link InstanceWorldBorder};
     *
     * @return the {@link InstanceWorldBorder} linked to the instance
     */
    @NotNull
    public InstanceWorldBorder getWorldBorder() {
        return worldBorder;
    }

    /**
     * Gets the entities in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the entities in the instance
     */
    @NotNull
    public Set<Entity> getEntities() {
        return Collections.unmodifiableSet(entities);
    }

    /**
     * Gets the players in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the players in the instance
     */
    @NotNull
    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    /**
     * Gets the creatures in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the creatures in the instance
     */
    @NotNull
    public Set<EntityCreature> getCreatures() {
        return Collections.unmodifiableSet(creatures);
    }

    /**
     * Gets the object entities in the instance;
     *
     * @return an unmodifiable {@link Set} containing all the object entities in the instance
     */
    @NotNull
    public Set<ObjectEntity> getObjectEntities() {
        return Collections.unmodifiableSet(objectEntities);
    }

    /**
     * Gets the experience orbs in the instance.
     *
     * @return an unmodifiable {@link Set} containing all the experience orbs in the instance
     */
    @NotNull
    public Set<ExperienceOrb> getExperienceOrbs() {
        return Collections.unmodifiableSet(experienceOrbs);
    }

    /**
     * Gets the entities located in the chunk.
     *
     * @param chunk the chunk to get the entities from
     * @return an unmodifiable {@link Set} containing all the entities in a chunk,
     * if {@code chunk} is unloaded, return an empty {@link HashSet}
     */
    @NotNull
    public Set<TachyonEntity> getChunkEntities(Chunk chunk) {
        if (!ChunkUtils.isLoaded(chunk))
            return new HashSet<>();

        final long index = ChunkUtils.getChunkIndex(chunk.getChunkX(), chunk.getChunkZ());
        final Set<TachyonEntity> entities = getEntitiesInChunk(index);
        return Collections.unmodifiableSet(entities);
    }

    /**
     * Refreshes the visual block id at the position.
     * <p>
     * WARNING: the custom block id at the position will not change.
     *
     * @param x            the X position
     * @param y            the Y position
     * @param z            the Z position
     * @param blockStateId the new block state id
     */
    public void refreshBlockStateId(int x, int y, int z, short blockStateId) {
        refreshBlockStateId(new Vec(x, y, z), blockStateId);
    }

    /**
     * Refreshes the visual block id at the position.
     * <p>
     * WARNING: the custom block id at the position will not change.
     *
     * @param x     the X position
     * @param y     the Y position
     * @param z     the Z position
     * @param block the new visual block
     */
    public void refreshBlockId(int x, int y, int z, @NotNull Block block) {
        refreshBlockStateId(x, y, z, block.toStateId((byte)0));
    }

    /**
     * Refreshes the visual block id at the {@link Point}.
     * <p>
     * WARNING: the custom block id at the position will not change.
     *
     * @param blockPosition the block position
     * @param block         the new visual block
     */
    public void refreshBlockId(@NotNull Point blockPosition, @NotNull Block block) {
        refreshBlockStateId(blockPosition, block.toStateId((byte)0));
    }

    /**
     * Loads the {@link TachyonChunk} at the given position without any callback.
     * <p>
     * WARNING: this is a non-blocking task.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    public void loadChunk(int chunkX, int chunkZ) {
        loadChunk(chunkX, chunkZ, null);
    }

    /**
     * Loads the chunk at the given {@link Point} with a callback.
     *
     * @param position the chunk position
     * @param callback the optional callback to run when the chunk is loaded
     */
    public void loadChunk(@NotNull Point position, @Nullable ChunkCallback callback) {
        final int chunkX = ChunkUtils.getChunkCoordinate(position.getX());
        final int chunkZ = ChunkUtils.getChunkCoordinate(position.getZ());
        loadChunk(chunkX, chunkZ, callback);
    }

    /**
     * Loads a {@link TachyonChunk} (if {@link #hasEnabledAutoChunkLoad()} returns true)
     * at the given {@link Point} with a callback.
     *
     * @param position the chunk position
     * @param callback the optional callback executed when the chunk is loaded (or with a null chunk if not)
     */
    public void loadOptionalChunk(@NotNull Point position, @Nullable ChunkCallback callback) {
        final int chunkX = ChunkUtils.getChunkCoordinate(position.getX());
        final int chunkZ = ChunkUtils.getChunkCoordinate(position.getZ());
        loadOptionalChunk(chunkX, chunkZ, callback);
    }

    /**
     * Unloads the chunk at the given position.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    public void unloadChunk(int chunkX, int chunkZ) {
        final TachyonChunk chunk = (TachyonChunk) getChunk(chunkX, chunkZ);
        Check.notNull(chunk, "The chunk at " + chunkX + ":" + chunkZ + " is already unloaded");
        unloadChunk(chunk);
    }

    /**
     * Gets block from given position.
     *
     * @param position position to get from
     * @return Block at given position.
     */
    public Block getBlock(Point position) {
        return getBlock(position.blockX(), position.blockY(), position.blockZ());
    }

    /**
     * Gets Block type from given coordinates.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     * @return Block at given position.
     */
    public Block getBlock(int x, int y, int z) {
        return Block.fromStateId(getBlockStateId(x, y, z));
    }

    /**
     * Gives the block state id at the given position.
     *
     * @param x the X position
     * @param y the Y position
     * @param z the Z position
     * @return the block state id at the position
     */
    public short getBlockStateId(int x, int y, int z) {
        final Chunk chunk = getChunkAt(x, z);
        Check.notNull(chunk, "The chunk at " + x + ":" + z + " is not loaded");
        synchronized (chunk) {
            return chunk.getBlockStateId(x, y, z);
        }
    }

    /**
     * Gives the block state id at the given position.
     *
     * @param x the X position
     * @param y the Y position
     * @param z the Z position
     * @return the block state id at the position
     */
    public short getBlockStateId(double x, double y, double z) {
        return getBlockStateId((int) Math.round(x), (int) Math.round(y), (int) Math.round(z));
    }

    /**
     * Gives the block state id at the given {@link Point}.
     *
     * @param blockPosition the block position
     * @return the block state id at the position
     */
    public short getBlockStateId(@NotNull Point blockPosition) {
        return getBlockStateId(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    /**
     * Gets the custom block object at the given position.
     *
     * @param x the X position
     * @param y the Y position
     * @param z the Z position
     * @return the custom block object at the position, null if not any
     */
    @Nullable
    public CustomBlock getCustomBlock(int x, int y, int z) {
        final Chunk chunk = getChunkAt(x, z);
        Check.notNull(chunk, "The chunk at " + x + ":" + z + " is not loaded");
        synchronized (chunk) {
            return chunk.getCustomBlock(x, y, z);
        }
    }

    /**
     * Gets the custom block object at the given {@link Point}.
     *
     * @param blockPosition the block position
     * @return the custom block object at the position, null if not any
     */
    @Nullable
    public CustomBlock getCustomBlock(@NotNull Point blockPosition) {
        return getCustomBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ());
    }

    /**
     * Sends a {@link BlockActionPacket} for all the viewers of the specific position.
     *
     * @param blockPosition the block position
     * @param actionId      the action id, depends on the block
     * @param actionParam   the action parameter, depends on the block
     * @see <a href="https://wiki.vg/Protocol#Block_Action">BlockActionPacket</a> for the action id &amp; param
     */
    public void sendBlockAction(@NotNull Point blockPosition, byte actionId, byte actionParam) {
        final short blockStateId = getBlockStateId(blockPosition);
        final Block block = Block.fromStateId(blockStateId);

        BlockActionPacket blockActionPacket = new BlockActionPacket(blockPosition, actionId, actionParam, block.getBlockId());
        final TachyonChunk chunk = getChunkAt(blockPosition);
        Check.notNull(chunk, "The chunk at " + blockPosition + " is not loaded!");
        chunk.sendPacketToViewers(blockActionPacket);
    }

    /**
     * Gets the block data at the given position.
     *
     * @param x the X position
     * @param y the Y position
     * @param z the Z position
     * @return the block data at the position, null if not any
     */
    @Nullable
    public Data getBlockData(int x, int y, int z) {
        final Chunk chunk = getChunkAt(x, z);
        Check.notNull(chunk, "The chunk at " + x + ":" + z + " is not loaded");
        final int index = ChunkUtils.getBlockIndex(x, y, z);
        synchronized (chunk) {
            return chunk.getBlockData(index);
        }
    }

    /**
     * Gets the block {@link Data} at the given {@link Point}.
     *
     * @param blockPosition the block position
     * @return the block data at the position, null if not any
     */
    @Nullable
    public Data getBlockData(@NotNull Point blockPosition) {
        return getBlockData(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ());
    }

    /**
     * Sets the block {@link Data} at the given {@link Point}.
     *
     * @param x    the X position
     * @param y    the Y position
     * @param z    the Z position
     * @param data the data to be set, can be null
     */
    public void setBlockData(int x, int y, int z, @Nullable Data data) {
        final Chunk chunk = getChunkAt(x, z);
        Check.notNull(chunk, "The chunk at " + x + ":" + z + " is not loaded");
        synchronized (chunk) {
            chunk.setBlockData(x, (byte) y, z, data);
        }
    }

    /**
     * Sets the block {@link Data} at the given {@link Point}.
     *
     * @param blockPosition the block position
     * @param data          the data to be set, can be null
     */
    public void setBlockData(@NotNull Point blockPosition, @Nullable Data data) {
        setBlockData(blockPosition.blockX(), (byte) blockPosition.getY(), blockPosition.blockZ(), data);
    }

    /**
     * Gets the {@link TachyonChunk} at the given {@link Point}, null if not loaded.
     *
     * @param x the X position
     * @param z the Z position
     * @return the chunk at the given position, null if not loaded
     */
    @Nullable
    public Chunk getChunkAt(double x, double z) {
        final int chunkX = ChunkUtils.getChunkCoordinate(x);
        final int chunkZ = ChunkUtils.getChunkCoordinate(z);
        return (getChunk(chunkX, chunkZ));
    }

    /**
     * Checks if the {@link TachyonChunk} at the position is loaded.
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return true if the chunk is loaded, false otherwise
     */
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return getChunk(chunkX, chunkZ) != null;
    }

    /**
     * Gets the {@link TachyonChunk} at the given {@link Point}, null if not loaded.
     *
     * @param blockPosition the chunk position
     * @return the chunk at the given position, null if not loaded
     */
    @Nullable
    public TachyonChunk getChunkAt(@NotNull Position blockPosition) {
        return ((TachyonChunk) getChunkAt(blockPosition.getX(), blockPosition.getZ()));
    }

    /**
     * Gets the {@link TachyonChunk} at the given {@link Point}, null if not loaded.
     *
     * @param position the chunk position
     * @return the chunk at the given position, null if not loaded
     */
    @Nullable
    public TachyonChunk getChunkAt(@NotNull Point position) {
        return (TachyonChunk) getChunkAt(position.getX(), position.getZ());
    }

    /**
     * Saves a {@link TachyonChunk} without any callback.
     *
     * @param chunk the chunk to save
     */
    public void saveChunkToStorage(@NotNull TachyonChunk chunk) {
        saveChunkToStorage(chunk, null);
    }

    /**
     * Saves all {@link TachyonChunk} without any callback.
     */
    public void saveChunksToStorage() {
        saveChunksToStorage(null);
    }

    /**
     * Gets the instance unique id.
     *
     * @return the instance unique id
     */
    @NotNull
    @Override
    public UUID getUuid() {
        return uniqueId;
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
    public Map<Class<? extends Event>, Collection<EventCallback>> getEventCallbacksMap() {
        return eventCallbacks;
    }

    // UNSAFE METHODS (need most of time to be synchronized)

    /**
     * Used when called {@link TachyonEntity#setInstance(Instance)}, it is used to refresh viewable chunks
     * and add viewers if {@code entity} is a {@link TachyonPlayer}.
     * <p>
     * Warning: unsafe, you probably want to use {@link TachyonEntity#setInstance(Instance)} instead.
     *
     * @param entity the entity to add
     */
    public void UNSAFE_addEntity(@NotNull TachyonEntity entity) {
        final Instance lastInstance = entity.getInstance();
        if (lastInstance != null && lastInstance != this) {
            lastInstance.UNSAFE_removeEntity(entity); // If entity is in another instance, remove it from there and add it to this
        }
        AddEntityToInstanceEvent event = new AddEntityToInstanceEvent(this, entity);
        callCancellableEvent(AddEntityToInstanceEvent.class, event, () -> {
            final Position entityPosition = entity.getPosition();

            // Send all visible entities
            EntityUtils.forEachRange(this, entityPosition, Tachyon.getServer().getEntityViewDistance(), ent -> {
                if (ent instanceof TachyonPlayer) {
                    if (entity.isAutoViewable())
                        entity.addViewer((TachyonPlayer) ent);
                }
            });

            final TachyonChunk chunk = getChunkAt(entityPosition);
            Check.notNull(chunk, "You tried to spawn an entity in an unloaded chunk, " + entityPosition);
            addEntityToChunk(entity, chunk);
        });
    }

    /**
     * Used when an {@link TachyonEntity} is removed from the instance, it removes all of his viewers.
     * <p>
     * Warning: unsafe, you probably want to set the entity to another instance.
     *
     * @param entity the entity to remove
     */
    public void UNSAFE_removeEntity(@NotNull TachyonEntity entity) {
        final Instance entityInstance = entity.getInstance();
        if (entityInstance != this)
            return;

        RemoveEntityFromInstanceEvent event = new RemoveEntityFromInstanceEvent(this, entity);
        callCancellableEvent(RemoveEntityFromInstanceEvent.class, event, () -> {
            // Remove this entity from players viewable list and send delete entities packet
            entity.getViewers().forEach(entity::removeViewer);

            // Remove the entity from cache
            final TachyonChunk chunk = getChunkAt(entity.getPosition());
            Check.notNull(chunk, "Tried to interact with an unloaded chunk.");
            removeEntityFromChunk(entity, chunk);
        });
    }

    /**
     * Synchronized method to execute {@link #removeEntityFromChunk(TachyonEntity, TachyonChunk)}
     * and {@link #addEntityToChunk(TachyonEntity, TachyonChunk)} simultaneously.
     *
     * @param entity    the entity to change its chunk
     * @param lastChunk the last entity chunk
     * @param newChunk  the new entity chunk
     */
    public synchronized void switchEntityChunk(@NotNull TachyonEntity entity, @NotNull TachyonChunk lastChunk, @NotNull TachyonChunk newChunk) {
        removeEntityFromChunk(entity, lastChunk);
        addEntityToChunk(entity, newChunk);
    }

    /**
     * Adds the specified {@link TachyonEntity} to the instance entities cache.
     * <p>
     * Warning: this is done automatically when the entity move out of his chunk.
     *
     * @param entity the entity to add
     * @param chunk  the chunk where the entity will be added
     */
    public void addEntityToChunk(@NotNull TachyonEntity entity, @NotNull TachyonChunk chunk) {
        Check.notNull(chunk,
                "The chunk " + chunk + " is not loaded, you can make it automatic by using Instance#enableAutoChunkLoad(true)");
        Check.argCondition(!chunk.isLoaded(), "TachyonChunk " + chunk + " has been unloaded previously");
        final long chunkIndex = ChunkUtils.getChunkIndex(chunk.getChunkX(), chunk.getChunkZ());
        synchronized (entitiesLock) {
            Set<TachyonEntity> entities = getEntitiesInChunk(chunkIndex);
            entities.add(entity);

            this.entities.add(entity);
            if (entity instanceof TachyonPlayer) {
                this.players.add((TachyonPlayer) entity);
            } else if (entity instanceof EntityCreature) {
                this.creatures.add((EntityCreature) entity);
            } else if (entity instanceof ObjectEntity) {
                this.objectEntities.add((ObjectEntity) entity);
            } else if (entity instanceof ExperienceOrb) {
                this.experienceOrbs.add((ExperienceOrb) entity);
            }
        }
    }

    /**
     * Removes the specified {@link TachyonEntity} to the instance entities cache.
     * <p>
     * Warning: this is done automatically when the entity move out of his chunk.
     *
     * @param entity the entity to remove
     * @param chunk  the chunk where the entity will be removed
     */
    public void removeEntityFromChunk(@NotNull TachyonEntity entity, @NotNull TachyonChunk chunk) {
        synchronized (entitiesLock) {
            final long chunkIndex = ChunkUtils.getChunkIndex(chunk.getChunkX(), chunk.getChunkZ());
            Set<TachyonEntity> entities = getEntitiesInChunk(chunkIndex);
            entities.remove(entity);

            this.entities.remove(entity);
            if (entity instanceof TachyonPlayer) {
                this.players.remove(entity);
            } else if (entity instanceof EntityCreature) {
                this.creatures.remove(entity);
            } else if (entity instanceof ObjectEntity) {
                this.objectEntities.remove(entity);
            } else if (entity instanceof ExperienceOrb) {
                this.experienceOrbs.remove(entity);
            }
        }
    }

    @NotNull
    private Set<TachyonEntity> getEntitiesInChunk(long index) {
        return chunkEntities.computeIfAbsent(index, i -> new CopyOnWriteArraySet<>());
    }

    /**
     * Schedules a block update at a given {@link Point}.
     * Does nothing if no {@link CustomBlock} is present at {@code position}.
     * <p>
     * Cancelled if the block changes between this call and the actual update.
     *
     * @param duration the duration before the update
     * @param position the location of the block to update
     */
    public abstract void scheduleUpdate(@NotNull Duration duration, @NotNull Point position);

    /**
     * Performs a single tick in the instance, including scheduled tasks from {@link #scheduleNextTick(Consumer)}.
     * <p>
     * Warning: this does not update chunks and entities.
     *
     * @param time the tick time in milliseconds
     */
    public void tick(long time) {
        // Scheduled tasks
        if (!nextTick.isEmpty()) {
            Consumer<Instance> callback;
            while ((callback = nextTick.poll()) != null) {
                callback.accept(this);
            }
        }

        // Time
        {
            this.worldAge++;

            this.time += timeRate;

            // time needs to be send to players
            if (timeUpdate != null && !CooldownUtils.hasCooldown(time, lastTimeUpdate, timeUpdate)) {
                PacketUtils.sendGroupedPacket(getPlayers(), createTimePacket());
                this.lastTimeUpdate = time;
            }

        }

        // Tick event
        {
            // Process tick events
            InstanceTickEvent chunkTickEvent = new InstanceTickEvent(this, time, lastTickAge);
            callEvent(InstanceTickEvent.class, chunkTickEvent);

            // Set last tick age
            lastTickAge = time;
        }

        this.worldBorder.update();
    }

    /**
     * Creates an explosion at the given position with the given strength.
     * The algorithm used to compute damages is provided by {@link #getExplosionSupplier()}.
     *
     * @param centerX  the center X
     * @param centerY  the center Y
     * @param centerZ  the center Z
     * @param strength the strength of the explosion
     * @throws IllegalStateException If no {@link ExplosionSupplier} was supplied
     */
    public void explode(float centerX, float centerY, float centerZ, float strength) {
        explode(centerX, centerY, centerZ, strength, null);
    }

    /**
     * Creates an explosion at the given position with the given strength.
     * The algorithm used to compute damages is provided by {@link #getExplosionSupplier()}.
     *
     * @param centerX        center X of the explosion
     * @param centerY        center Y of the explosion
     * @param centerZ        center Z of the explosion
     * @param strength       the strength of the explosion
     * @param additionalData data to pass to the explosion supplier
     * @throws IllegalStateException If no {@link ExplosionSupplier} was supplied
     */
    public void explode(float centerX, float centerY, float centerZ, float strength, @Nullable Data additionalData) {
        final ExplosionSupplier explosionSupplier = getExplosionSupplier();
        Check.stateCondition(explosionSupplier == null, "Tried to create an explosion with no explosion supplier");
        final Explosion explosion = explosionSupplier.createExplosion(centerX, centerY, centerZ, strength, additionalData);
        explosion.apply(this);
    }

    /**
     * Gets the registered {@link ExplosionSupplier}, or null if none was provided.
     *
     * @return the instance explosion supplier, null if none was provided
     */
    @Nullable
    public ExplosionSupplier getExplosionSupplier() {
        return explosionSupplier;
    }

    /**
     * Registers the {@link ExplosionSupplier} to use in this instance.
     *
     * @param supplier the explosion supplier
     */
    public void setExplosionSupplier(@Nullable ExplosionSupplier supplier) {
        this.explosionSupplier = supplier;
    }

    /**
     * Gets the instance space.
     * <p>
     * Used by the pathfinder for entities.
     *
     * @return the instance space
     */
    @NotNull
    public PFInstanceSpace getInstanceSpace() {
        return instanceSpace;
    }


    @NotNull @Override
    public Set<Player> players() {
        return players;
    }
}