package net.tachyon.instance;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.tachyon.Tachyon;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Vec;
import net.tachyon.data.Data;
import net.tachyon.data.SerializableData;
import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.instance.InstanceChunkLoadEvent;
import net.tachyon.event.instance.InstanceChunkUnloadEvent;
import net.tachyon.event.player.PlayerBlockBreakEvent;
import net.tachyon.instance.batch.ChunkGenerationBatch;
import net.tachyon.block.Block;
import net.tachyon.block.CustomBlock;
import net.tachyon.block.rule.BlockPlacementRule;
import net.tachyon.network.packet.server.play.BlockChangePacket;
import net.tachyon.network.packet.server.play.ChunkDataPacket;
import net.tachyon.network.packet.server.play.EffectPacket;
import net.tachyon.utils.PacketUtils;
import net.tachyon.utils.block.CustomBlockUtils;
import net.tachyon.utils.OptionalCallback;
import net.tachyon.world.chunk.ChunkCallback;
import net.tachyon.world.chunk.ChunkSupplier;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.DimensionType;
import net.tachyon.world.LevelType;
import net.tachyon.world.biome.Biome;
import net.tachyon.UpdateManager;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * InstanceContainer is an instance that contains chunks in contrary to SharedInstance.
 */
public class InstanceContainer extends Instance {

    private static final String UUID_KEY = "uuid";
    private static final String DATA_KEY = "data";


    // the shared instances assigned to this instance
    private final List<SharedInstance> sharedInstances = new CopyOnWriteArrayList<>();

    // the chunk generator used, can be null
    private ChunkGenerator chunkGenerator;
    // (chunk index -> chunk) map, contains all the chunks in the instance
    private final Long2ObjectMap<TachyonChunk> chunks = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
    // contains all the chunks to remove during the next instance tick, should be synchronized
    protected final Set<Chunk> scheduledChunksToRemove = new HashSet<>();

    private final ReadWriteLock changingBlockLock = new ReentrantReadWriteLock();
    private final Map<Point, Block> currentlyChangingBlocks = new HashMap<>();

    // the chunk loader, used when trying to load/save a chunk from another source
    private IChunkLoader chunkLoader;

    // used to automatically enable the chunk loading or not
    private boolean autoChunkLoad = true;

    // used to supply a new chunk object at a position when requested
    private ChunkSupplier chunkSupplier;

    // Fields for instance copy
    protected InstanceContainer srcInstance; // only present if this instance has been created using a copy
    private long lastBlockChangeTime; // Time at which the last block change happened (#setBlock)

    /**
     * Creates an {@link InstanceContainer}.
     *
     * @param uniqueId        the unique id of the instance
     * @param dimensionType   the dimension type of the instance
     * @param levelType       the level type of the instance
     *                        can be null if you do not wish to save the instance later on
     */
    public InstanceContainer(@NotNull UUID uniqueId, @NotNull DimensionType dimensionType, @NotNull LevelType levelType) {
        super(uniqueId, dimensionType, levelType);

        // Set the default chunk supplier using DynamicChunk
        setChunkSupplier(DynamicChunk::new);

        // Set the default chunk loader which use the instance's StorageLocation and ChunkSupplier to save and load chunks
        setChunkLoader(new BasicChunkLoader(this));
    }

    @Override
    public void setBlockStateId(int x, int y, int z, short blockStateId, @Nullable Data data) {
        setBlock(x, y, z, blockStateId, null, data);
    }

    @Override
    public void setCustomBlock(int x, int y, int z, short customBlockId, @Nullable Data data) {
        final CustomBlock customBlock = BLOCK_MANAGER.getCustomBlock(customBlockId);
        Check.notNull(customBlock, "The custom block with the id " + customBlockId + " does not exist.");
        setBlock(x, y, z, customBlock.getDefaultBlockStateId(), customBlock, data);
    }

    @Override
    public void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data) {
        final CustomBlock customBlock = BLOCK_MANAGER.getCustomBlock(customBlockId);
        setBlock(x, y, z, blockStateId, customBlock, data);
    }

    /**
     * Set a block at the position
     * <p>
     * Verifies if the {@link TachyonChunk} at the position is loaded, place the block if yes.
     * Otherwise, check if {@link #hasEnabledAutoChunkLoad()} is true to load the chunk automatically and place the block.
     *
     * @param x            the block X
     * @param y            the block Y
     * @param z            the block Z
     * @param blockStateId the block state id
     * @param customBlock  the {@link CustomBlock}, null if none
     * @param data         the {@link Data}, null if none
     */
    private synchronized void setBlock(int x, int y, int z, short blockStateId,
                                       @Nullable CustomBlock customBlock, @Nullable Data data) {
        final Chunk chunk = getChunkAt(x, z);
        if (ChunkUtils.isLoaded(chunk)) {
            UNSAFE_setBlock((TachyonChunk)chunk, x, y, z, blockStateId, customBlock, data);
        } else {
            Check.stateCondition(!hasEnabledAutoChunkLoad(),
                    "Tried to set a block to an unloaded chunk with auto chunk load disabled");
            final int chunkX = ChunkUtils.getChunkCoordinate(x);
            final int chunkZ = ChunkUtils.getChunkCoordinate(z);
            loadChunk(chunkX, chunkZ, c -> UNSAFE_setBlock((TachyonChunk)c, x, y, z, blockStateId, customBlock, data));
        }
    }

    /**
     * Sets a block at the specified position.
     * <p>
     * Unsafe because the method is not synchronized and it does not verify if the chunk is loaded or not.
     *
     * @param chunk        the {@link TachyonChunk} which should be loaded
     * @param x            the block X
     * @param y            the block Y
     * @param z            the block Z
     * @param blockStateId the block state id
     * @param customBlock  the {@link CustomBlock}, null if none
     * @param data         the {@link Data}, null if none
     */
    private void UNSAFE_setBlock(@NotNull TachyonChunk chunk, int x, int y, int z, short blockStateId,
                                 @Nullable CustomBlock customBlock, @Nullable Data data) {

        // Cannot place block in a read-only chunk
        if (chunk.isReadOnly()) {
            return;
        }

        synchronized (chunk) {

            // Refresh the last block change time
            this.lastBlockChangeTime = System.currentTimeMillis();

            final boolean isCustomBlock = customBlock != null;

            final Point Point = new Vec(x, y, z);

            if (isAlreadyChanged(Point, blockStateId)) { // do NOT change the block again.
                // Avoids StackOverflowExceptions when onDestroy tries to destroy the block itself
                // This can happen with nether portals which break the entire frame when a portal block is broken
                return;
            }
            setAlreadyChanged(Point, blockStateId);

            final int index = ChunkUtils.getBlockIndex(x, y, z);

            final CustomBlock previousBlock = chunk.getCustomBlock(index);
            final Data previousBlockData = previousBlock != null ? chunk.getBlockData(index) : null;

            // Change id based on neighbors
            blockStateId = executeBlockPlacementRule(blockStateId, Point);

            // Retrieve custom block values
            short customBlockId = 0;
            boolean hasUpdate = false;
            if (isCustomBlock) {
                customBlockId = customBlock.getCustomBlockId();
                data = customBlock.createData(this, Point, data);
                hasUpdate = CustomBlockUtils.hasUpdate(customBlock);
            }

            // Set the block
            chunk.UNSAFE_setBlock(x, y, z, blockStateId, customBlockId, data, hasUpdate);

            // Refresh neighbors since a new block has been placed
            executeNeighboursBlockPlacementRule(Point);

            // Refresh player chunk block
            sendBlockChange(chunk, Point, blockStateId);

            // Call the destroy listener for the previously destroyed block
            if (previousBlock != null) {
                callBlockDestroy(previousBlock, previousBlockData, Point);
            }

            // Call the place listener for newly placed custom block
            if (isCustomBlock) {
                callBlockPlace(chunk, index, Point);
            }
        }
    }

    private void setAlreadyChanged(@NotNull Point Point, short blockStateId) {
        currentlyChangingBlocks.put(Point, Block.fromStateId(blockStateId));
    }

    /**
     * Has this block already changed since last update?
     * Prevents StackOverflow with blocks trying to modify their position in onDestroy or onPlace.
     *
     * @param Point the block position
     * @param blockStateId  the block state id
     * @return true if the block changed since the last update
     */
    private boolean isAlreadyChanged(@NotNull Point Point, short blockStateId) {
        final Block changedBlock = currentlyChangingBlocks.get(Point);
        if (changedBlock == null)
            return false;
        return changedBlock.toStateId((byte)0) == blockStateId;
    }

    @Override
    public void refreshBlockStateId(@NotNull Point point, short blockStateId) {
        final TachyonChunk chunk =(TachyonChunk) getChunkAt(point.getX(), point.getZ());
        Check.notNull(chunk, "You cannot refresh a block in a null chunk!");
        synchronized (chunk) {
            chunk.refreshBlockStateId(point.blockX(), point.blockY(), point.blockZ(), blockStateId);

            sendBlockChange(chunk, point, blockStateId);
        }
    }

    /**
     * Calls {@link CustomBlock#onDestroy(net.tachyon.world.World, Point, Data)} for {@code previousBlock}.
     * <p>
     * WARNING {@code chunk} needs to be synchronized.
     *
     * @param previousBlock     the block which has been destroyed
     * @param previousBlockData the data of the destroyed block
     * @param Point     the block position
     */
    private void callBlockDestroy(@NotNull CustomBlock previousBlock, @Nullable Data previousBlockData,
                                  @NotNull Point Point) {
        previousBlock.onDestroy(this, Point, previousBlockData);
    }

    /**
     * Calls {@link CustomBlock#onPlace(net.tachyon.world.World, Point, Data)} for the current custom block at the position.
     * <p>
     * WARNING {@code chunk} needs to be synchronized.
     *
     * @param chunk         the chunk where the block is
     * @param index         the block index
     * @param Point the block position
     */
    private void callBlockPlace(@NotNull TachyonChunk chunk, int index, @NotNull Point Point) {
        final CustomBlock actualBlock = chunk.getCustomBlock(index);
        if (actualBlock == null)
            return;
        final Data previousData = chunk.getBlockData(index);
        actualBlock.onPlace(this, Point, previousData);
    }

    /**
     * Calls the {@link BlockPlacementRule} for the specified block state id.
     *
     * @param blockStateId  the block state id to modify
     * @param Point the block position
     * @return the modified block state id
     */
    private short executeBlockPlacementRule(short blockStateId, @NotNull Point Point) {
        final BlockPlacementRule blockPlacementRule = BLOCK_MANAGER.getBlockPlacementRule(blockStateId);
        if (blockPlacementRule != null) {
            return blockPlacementRule.blockUpdate(this, Point, blockStateId);
        }
        return blockStateId;
    }

    /**
     * Executed when a block is modified, this is used to modify the states of neighbours blocks.
     * <p>
     * For example, this can be used for redstone wires which need an understanding of its neighborhoods to take the right shape.
     *
     * @param point the position of the modified block
     */
    private void executeNeighboursBlockPlacementRule(@NotNull Point point) {
        for (int offsetX = -1; offsetX < 2; offsetX++) {
            for (int offsetY = -1; offsetY < 2; offsetY++) {
                for (int offsetZ = -1; offsetZ < 2; offsetZ++) {
                    if (offsetX == 0 && offsetY == 0 && offsetZ == 0)
                        continue;
                    final int neighborX = point.blockX() + offsetX;
                    final int neighborY = point.blockY() + offsetY;
                    final int neighborZ = point.blockZ() + offsetZ;
                    final TachyonChunk chunk = (TachyonChunk)getChunkAt(neighborX, neighborZ);

                    // Do not try to get neighbour in an unloaded chunk
                    if (chunk == null)
                        continue;

                    final short neighborStateId = chunk.getBlockStateId(neighborX, neighborY, neighborZ);
                    final BlockPlacementRule neighborBlockPlacementRule = BLOCK_MANAGER.getBlockPlacementRule(neighborStateId);
                    if (neighborBlockPlacementRule != null) {
                        final Point neighborPosition = new Vec(neighborX, neighborY, neighborZ);
                        final short newNeighborId = neighborBlockPlacementRule.blockUpdate(this,
                                neighborPosition, neighborStateId);
                        if (neighborStateId != newNeighborId) {
                            refreshBlockStateId(neighborPosition, newNeighborId);
                        }
                    }

                    // Update neighbors
                    final CustomBlock customBlock = getCustomBlock(neighborX, neighborY, neighborZ);
                    if (customBlock != null) {
                        boolean directNeighbor = false; // only if directly connected to neighbor (no diagonals)
                        if (offsetX != 0 ^ offsetZ != 0) {
                            directNeighbor = offsetY == 0;
                        } else if (offsetX == 0 && offsetZ == 0) {
                            directNeighbor = true;
                        }
                        customBlock.updateFromNeighbor(this, new Vec(neighborX, neighborY, neighborZ), point, directNeighbor);
                    }
                }
            }
        }
    }

    @Override
    public boolean breakBlock(@NotNull Player player, @NotNull Point point) {
        final TachyonChunk chunk = getChunkAt(point);
        Check.notNull(chunk, "You cannot break blocks in a null chunk!");

        // Cancel if the chunk is read-only
        if (chunk.isReadOnly()) {
            return false;
        }

        // TachyonChunk unloaded, stop here
        if (!ChunkUtils.isLoaded(chunk))
            return false;

        final int x = point.blockX();
        final int y = point.blockY();
        final int z = point.blockZ();

        final short blockStateId = getBlockStateId(x, y, z);

        // The player probably have a wrong version of this chunk section, send it
        if (blockStateId == 0) {
            chunk.sendChunkSectionUpdate(ChunkUtils.getSectionAt(y), (TachyonPlayer) player);
            return false;
        }

        final CustomBlock customBlock = getCustomBlock(x, y, z);

        PlayerBlockBreakEvent blockBreakEvent = new PlayerBlockBreakEvent(player, point, blockStateId, customBlock, (short) 0, (short) 0);
        ((TachyonPlayer)player).callEvent(PlayerBlockBreakEvent.class, blockBreakEvent);
        final boolean allowed = !blockBreakEvent.isCancelled();
        if (allowed) {
            // Break or change the broken block based on event result
            final short resultState = blockBreakEvent.getResultBlockStateId();
            final short resultCustom = blockBreakEvent.getResultCustomBlockId();
            setSeparateBlocks(x, y, z, resultState, resultCustom);

            // Send the block break effect packet
            {
                EffectPacket effectPacket = new EffectPacket(2001, point, blockStateId, false);
                PacketUtils.sendGroupedPacket(chunk.getViewers(), effectPacket,
                        (viewer) -> {
                            // Prevent the block breaker to play the particles and sound two times
                            return !viewer.equals(player);
                        });
            }

        }

        return allowed;
    }

    @Override
    public void loadChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        final TachyonChunk chunk = getChunk(chunkX, chunkZ);
        if (chunk != null) {
            // TachyonChunk already loaded
            OptionalCallback.execute(callback, chunk);
        } else {
            // Retrieve chunk from somewhere else (file or create a new one using the ChunkGenerator)
            retrieveChunk(chunkX, chunkZ, callback);
        }
    }

    @Override
    public void loadOptionalChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        final TachyonChunk chunk = getChunk(chunkX, chunkZ);
        if (chunk != null) {
            // TachyonChunk already loaded
            OptionalCallback.execute(callback, chunk);
        } else {
            if (hasEnabledAutoChunkLoad()) {
                // Load chunk from StorageLocation or with ChunkGenerator
                retrieveChunk(chunkX, chunkZ, callback);
            } else {
                // TachyonChunk not loaded, return null
                OptionalCallback.execute(callback, null);
            }
        }
    }

    @Override
    public void unloadChunk(@NotNull TachyonChunk chunk) {
        // Already unloaded chunk
        if (!ChunkUtils.isLoaded(chunk)) {
            return;
        }
        // Schedule the chunk removal
        synchronized (this.scheduledChunksToRemove) {
            this.scheduledChunksToRemove.add(chunk);
        }
    }

    @Override
    public TachyonChunk getChunk(int chunkX, int chunkZ) {
        final long index = ChunkUtils.getChunkIndex(chunkX, chunkZ);
        final TachyonChunk chunk = chunks.get(index);
        return ChunkUtils.isLoaded(chunk) ? chunk : null;
    }

    /**
     * Saves the instance ({@link #getUuid()} {@link #getData()}) and call {@link #saveChunksToStorage(Runnable)}.
     * <p>
     * WARNING: {@link #getData()} needs to be a {@link SerializableData} in order to be saved.
     *
     * @param callback the optional callback once the saving is done
     */
    public void saveInstance(@Nullable Runnable callback) {
//        Check.notNull(getStorageLocation(), "You cannot save the instance if no StorageLocation has been defined");
//
//        this.storageLocation.set(UUID_KEY, getUuid(), UUID.class);
//        final Data data = getData();
//        if (data != null) {
//            // Save the instance data
//            Check.stateCondition(!(data instanceof SerializableData),
//                    "Instance#getData needs to be a SerializableData in order to be saved");
//            this.storageLocation.set(DATA_KEY, (SerializableData) getData(), SerializableData.class);
//        }
//
//        saveChunksToStorage(callback);
    }

    /**
     * Saves the instance without callback.
     *
     * @see #saveInstance(Runnable)
     */
    public void saveInstance() {
        saveInstance(null);
    }

    @Override
    public void saveChunkToStorage(@NotNull TachyonChunk chunk, Runnable callback) {
        this.chunkLoader.saveChunk(chunk, callback);
    }

    @Override
    public void saveChunksToStorage(@Nullable Runnable callback) {
        Collection<TachyonChunk> chunksCollection = chunks.values();
        this.chunkLoader.saveChunks(chunksCollection, callback);
    }

    @Override
    protected void retrieveChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        final boolean loaded = chunkLoader.loadChunk(this, chunkX, chunkZ, chunk -> {
            cacheChunk((TachyonChunk)chunk);
            UPDATE_MANAGER.signalChunkLoad(this, chunkX, chunkZ);
            // Execute callback and event in the instance thread
            scheduleNextTick(instance -> {
                callChunkLoadEvent(chunkX, chunkZ);
                OptionalCallback.execute(callback, chunk);
            });
        });

        if (!loaded) {
            // Not found, create a new chunk
            createChunk(chunkX, chunkZ, callback);
        }
    }

    @Override
    protected void createChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        Biome[] biomes = new Biome[TachyonChunk.BIOME_COUNT];
        if (chunkGenerator == null) {
            Arrays.fill(biomes, Tachyon.getServer().getBiomeManager().getById(0));
        } else {
            chunkGenerator.fillBiomes(biomes, chunkX, chunkZ);
        }

        final TachyonChunk chunk = (TachyonChunk)chunkSupplier.createChunk(biomes, chunkX, chunkZ, getDimensionType().getHasSky());
        Check.notNull(chunk, "Chunks supplied by a ChunkSupplier cannot be null.");

        cacheChunk(chunk);

        if (chunkGenerator != null && chunk.shouldGenerate()) {
            // Execute the chunk generator to populate the chunk
            final ChunkGenerationBatch chunkBatch = new ChunkGenerationBatch(this, chunk);

            chunkBatch.generate(chunkGenerator, callback);
        } else {
            // No chunk generator, execute the callback with the empty chunk
            OptionalCallback.execute(callback, chunk);
        }

        UPDATE_MANAGER.signalChunkLoad(this, chunkX, chunkZ);
        callChunkLoadEvent(chunkX, chunkZ);
    }

    @Override
    public void enableAutoChunkLoad(boolean enable) {
        this.autoChunkLoad = enable;
    }


    @Override
    public boolean hasEnabledAutoChunkLoad() {
        return autoChunkLoad;
    }

    @Override
    public boolean isInVoid(@NotNull Point position) {
        // TODO: customizable
        return position.getY() < -64;
    }

    /**
     * Changes which type of {@link TachyonChunk} implementation to use once one needs to be loaded.
     * <p>
     * Uses {@link DynamicChunk} by default.
     * <p>
     * WARNING: if you need to save this instance's chunks later,
     * the code needs to be predictable for {@link IChunkLoader#loadChunk(Instance, int, int, ChunkCallback)}
     * to create the correct type of {@link TachyonChunk}. tl;dr: Need chunk save = no random type.
     *
     * @param chunkSupplier the new {@link ChunkSupplier} of this instance, chunks need to be non-null
     * @throws NullPointerException if {@code chunkSupplier} is null
     */
    public void setChunkSupplier(@NotNull ChunkSupplier chunkSupplier) {
        this.chunkSupplier = chunkSupplier;
    }

    /**
     * Gets the current {@link ChunkSupplier}.
     * <p>
     * You shouldn't use it to generate a new chunk, but as a way to view which one is currently in use.
     *
     * @return the current {@link ChunkSupplier}
     */
    public ChunkSupplier getChunkSupplier() {
        return chunkSupplier;
    }

    /**
     * Gets all the {@link SharedInstance} linked to this container.
     *
     * @return an unmodifiable {@link List} containing all the {@link SharedInstance} linked to this container
     */
    public List<SharedInstance> getSharedInstances() {
        return Collections.unmodifiableList(sharedInstances);
    }

    /**
     * Gets if this instance has {@link SharedInstance} linked to it.
     *
     * @return true if {@link #getSharedInstances()} is not empty
     */
    public boolean hasSharedInstances() {
        return !sharedInstances.isEmpty();
    }

    /**
     * Assigns a {@link SharedInstance} to this container.
     * <p>
     * Only used by {@link InstanceManager}, mostly unsafe.
     *
     * @param sharedInstance the shared instance to assign to this container
     */
    protected void addSharedInstance(SharedInstance sharedInstance) {
        this.sharedInstances.add(sharedInstance);
    }

    /**
     * Copies all the chunks of this instance and create a new instance container with all of them.
     * <p>
     * Chunks are copied with {@link TachyonChunk#copy(int, int)},
     * {@link UUID} is randomized, {@link DimensionType} is passed over.
     *
     * @return an {@link InstanceContainer} with the exact same chunks as 'this'
     * @see #getSrcInstance() to retrieve the "creation source" of the copied instance
     */
    public synchronized InstanceContainer copy() {
        InstanceContainer copiedInstance = new InstanceContainer(UUID.randomUUID(), getDimensionType(), getLevelType());
        copiedInstance.srcInstance = this;
        copiedInstance.lastBlockChangeTime = lastBlockChangeTime;

        for (TachyonChunk chunk : chunks.values()) {
            final int chunkX = chunk.getChunkX();
            final int chunkZ = chunk.getChunkZ();

            final TachyonChunk copiedChunk = chunk.copy(chunkX, chunkZ);

            copiedInstance.cacheChunk(copiedChunk);
            UPDATE_MANAGER.signalChunkLoad(copiedInstance, chunkX, chunkZ);
        }

        return copiedInstance;
    }

    /**
     * Gets the instance from which this one has been copied.
     * <p>
     * Only present if this instance has been created with {@link InstanceContainer#copy()}.
     *
     * @return the instance source, null if not created by a copy
     * @see #copy() to create a copy of this instance with 'this' as the source
     */
    @Nullable
    public InstanceContainer getSrcInstance() {
        return srcInstance;
    }

    /**
     * Gets the last time at which a block changed.
     *
     * @return the time at which the last block changed in milliseconds, 0 if never
     */
    public long getLastBlockChangeTime() {
        return lastBlockChangeTime;
    }

    /**
     * Signals the instance that a block changed.
     * <p>
     * Useful if you change blocks values directly using a {@link TachyonChunk} object.
     */
    public void refreshLastBlockChangeTime() {
        this.lastBlockChangeTime = System.currentTimeMillis();
    }

    /**
     * Adds a {@link TachyonChunk} to the internal instance map.
     * <p>
     * WARNING: the chunk will not automatically be sent to players and
     * {@link UpdateManager#signalChunkLoad(Instance, int, int)} must be called manually.
     *
     * @param chunk the chunk to cache
     */
    public void cacheChunk(@NotNull TachyonChunk chunk) {
        final long index = ChunkUtils.getChunkIndex(chunk.getChunkX(), chunk.getChunkZ());
        this.chunks.put(index, chunk);
    }

    @Override
    public ChunkGenerator getChunkGenerator() {
        return chunkGenerator;
    }

    @Override
    public void setChunkGenerator(ChunkGenerator chunkGenerator) {
        this.chunkGenerator = chunkGenerator;
    }

    /**
     * Gets all the instance chunks.
     *
     * @return the chunks of this instance
     */
    @NotNull
    public Collection<Chunk> getChunks() {
        return Collections.unmodifiableCollection(chunks.values());
    }

    /**
     * Gets the {@link IChunkLoader} of this instance.
     *
     * @return the {@link IChunkLoader} of this instance
     */
    public IChunkLoader getChunkLoader() {
        return chunkLoader;
    }

    /**
     * Changes the {@link IChunkLoader} of this instance (to change how chunks are retrieved when not already loaded).
     *
     * @param chunkLoader the new {@link IChunkLoader}
     */
    public void setChunkLoader(IChunkLoader chunkLoader) {
        this.chunkLoader = chunkLoader;
    }

    /**
     * Sends a {@link BlockChangePacket} at the specified {@link Point} to set the block as {@code blockStateId}.
     * <p>
     * WARNING: this does not change the internal block data, this is strictly visual for the players.
     *
     * @param chunk         the chunk where the block is
     * @param point the block position
     * @param blockStateId  the new state of the block
     */
    private void sendBlockChange(@NotNull TachyonChunk chunk, @NotNull Point point, short blockStateId) {
        chunk.sendPacketToViewers(new BlockChangePacket(point, blockStateId));
    }

    @Override
    public void scheduleUpdate(@NotNull Duration duration, @NotNull Point position) {
        final CustomBlock toUpdate = getCustomBlock(position);
        if (toUpdate == null) {
            return;
        }

        Tachyon.getServer().getSchedulerManager().buildTask(() -> {
            final CustomBlock currentBlock = getCustomBlock(position);
            if (currentBlock == null)
                return;
            if (currentBlock.getCustomBlockId() != toUpdate.getCustomBlockId()) { // block changed
                return;
            }
            currentBlock.scheduledUpdate(this, position, getBlockData(position));
        }).delay(duration).schedule();
    }

    @Override
    public void tick(long time) {
        // Unload all waiting chunks
        UNSAFE_unloadChunks();

        // Time/world border
        super.tick(time);

        Lock wrlock = changingBlockLock.writeLock();
        wrlock.lock();
        currentlyChangingBlocks.clear();
        wrlock.unlock();
    }

    /**
     * Unloads all waiting chunks.
     * <p>
     * Unsafe because it has to be done on the same thread as the instance/chunks tick update.
     */
    protected void UNSAFE_unloadChunks() {
        synchronized (scheduledChunksToRemove) {
            for (Chunk chunk : scheduledChunksToRemove) {
                final int chunkX = chunk.getChunkX();
                final int chunkZ = chunk.getChunkZ();

                final long index = ChunkUtils.getChunkIndex(chunkX, chunkZ);

                ChunkDataPacket chunkDataPacket = new ChunkDataPacket(null, 0);
                chunkDataPacket.chunkX = chunkX;
                chunkDataPacket.chunkZ = chunkZ;
                chunkDataPacket.fullChunk = false;
                chunkDataPacket.unloadChunk = true;
                chunkDataPacket.skylight = chunk.hasSky();
                chunk.sendPacketToViewers(chunkDataPacket);

                for (Player viewer : chunk.getViewers()) {
                    chunk.removeViewer(viewer);
                }

                callChunkUnloadEvent(chunkX, chunkZ);

                // Remove all entities in chunk
                getChunkEntities(chunk).forEach(entity -> {
                    if (!(entity instanceof Player))
                        entity.remove();
                });

                // Clear cache
                this.chunks.remove(index);
                this.chunkEntities.remove(index);

                chunk.unload();

                UPDATE_MANAGER.signalChunkUnload(this, chunkX, chunkZ);
            }
            this.scheduledChunksToRemove.clear();
        }
    }

    private void callChunkLoadEvent(int chunkX, int chunkZ) {
        InstanceChunkLoadEvent chunkLoadEvent = new InstanceChunkLoadEvent(this, chunkX, chunkZ);
        callEvent(InstanceChunkLoadEvent.class, chunkLoadEvent);
    }

    private void callChunkUnloadEvent(int chunkX, int chunkZ) {
        InstanceChunkUnloadEvent chunkUnloadEvent = new InstanceChunkUnloadEvent(this, chunkX, chunkZ);
        callEvent(InstanceChunkUnloadEvent.class, chunkUnloadEvent);
    }
}