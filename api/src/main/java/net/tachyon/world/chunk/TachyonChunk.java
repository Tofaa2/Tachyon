package net.tachyon.world.chunk;

import net.tachyon.Tachyon;
import net.tachyon.binary.BinaryReader;
import net.tachyon.block.BlockManager;
import net.tachyon.coordinate.Position;
import net.tachyon.data.Data;
import net.tachyon.entity.Player;
import net.tachyon.entity.pathfinding.PFColumnarSpace;
import net.tachyon.event.player.PlayerChunkLoadEvent;
import net.tachyon.event.player.PlayerChunkUnloadEvent;
import net.tachyon.block.Block;
import net.tachyon.block.CustomBlock;
import net.tachyon.network.packet.server.play.ChunkDataPacket;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.utils.MathUtils;
import net.tachyon.world.World;
import net.tachyon.world.batch.Batch;
import net.tachyon.world.biome.BiomeManager;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

// TODO light data & API

/**
 * A chunk is a part of a {@link World}, limited by a size of 16x256x16 blocks and subdivided in 16 sections of 16 blocks height.
 * Should contain all the blocks located at those positions and manage their tick updates.
 * Be aware that implementations do not need to be thread-safe, all chunks are guarded by their own instance ('this').
 * <p>
 * Chunks can be serialized using {@link #getSerializedData()} and deserialized back with {@link #readChunk(net.tachyon.binary.BinaryReader, ChunkCallback)},
 * allowing you to implement your own storage solution if needed.
 * <p>
 * You can create your own implementation of this class by extending it
 * and create the objects in {@link World#setChunkSupplier(ChunkSupplier)}.
 * <p>
 * You generally want to avoid storing references of this object as this could lead to a huge memory leak,
 * you should store the chunk coordinates instead.
 */
public abstract class TachyonChunk implements Chunk {

    protected static final BlockManager BLOCK_MANAGER = Tachyon.getServer().getBlockmanager();
    protected static final BiomeManager BIOME_MANAGER = Tachyon.getServer().getBiomeManager();

    private final UUID identifier;

    @NotNull
    protected final Biome[] biomes;
    protected final int chunkX, chunkZ;
    protected final boolean hasSky;

    // Options
    private final boolean shouldGenerate;
    private boolean readOnly;

    protected volatile boolean loaded = true;
    protected final Set<Player> viewers = new CopyOnWriteArraySet<>();
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);

    // Path finding
    protected PFColumnarSpace columnarSpace;

    // Data
    protected Data data;

    public TachyonChunk(@Nullable Biome[] biomes, int chunkX, int chunkZ, boolean hasSky, boolean shouldGenerate) {
        this.identifier = UUID.randomUUID();
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.hasSky = hasSky;
        this.shouldGenerate = shouldGenerate;

        if (biomes != null && biomes.length == BIOME_COUNT) {
            this.biomes = biomes;
        } else {
            this.biomes = new Biome[BIOME_COUNT];
        }
    }

    /**
     * Sets a block at a position.
     * <p>
     * This is used when the previous block has to be destroyed/replaced, meaning that it clears the previous data and update method.
     * <p>
     * WARNING: this method is not thread-safe (in order to bring performance improvement with {@link Batch}s)
     * The thread-safe version is {@link World#setSeparateBlocks(int, int, int, short, short, Data)} (or any similar instance methods)
     * Otherwise, you can simply do not forget to have this chunk synchronized when this is called.
     *
     * @param x             the block X
     * @param y             the block Y
     * @param z             the block Z
     * @param blockStateId  the block state id
     * @param customBlockId the custom block id, 0 if not
     * @param data          the {@link Data} of the block, can be null
     * @param updatable     true if the block has an update method
     *                      Warning: <code>customBlockId</code> cannot be 0 in this case and needs to be valid since the update delay and method
     *                      will be retrieved from the associated {@link CustomBlock} object
     */
    public abstract void UNSAFE_setBlock(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data, boolean updatable);

    /**
     * Executes a chunk tick.
     * <p>
     * Should be used to update all the blocks in the chunk.
     * <p>
     * WARNING: this method doesn't necessary have to be thread-safe, proceed with caution.
     *
     * @param time     the time of the update in milliseconds
     * @param instance the {@link World} linked to this chunk
     */
    public abstract void tick(long time, @NotNull World instance);

    /**
     * Gets the block state id at a position.
     *
     * @param x the block X
     * @param y the block Y
     * @param z the block Z
     * @return the block state id at the position
     */
    public abstract short getBlockStateId(int x, int y, int z);

    /**
     * Gets the custom block id at a position.
     *
     * @param x the block X
     * @param y the block Y
     * @param z the block Z
     * @return the custom block id at the position
     */
    public abstract short getCustomBlockId(int x, int y, int z);

    /**
     * Changes the block state id and the custom block id at a position.
     *
     * @param x             the block X
     * @param y             the block Y
     * @param z             the block Z
     * @param blockStateId  the new block state id
     * @param customBlockId the new custom block id
     */
    public abstract void refreshBlockValue(int x, int y, int z, short blockStateId, short customBlockId);

    /**
     * Changes the block state id at a position (the custom block id stays the same).
     *
     * @param x            the block X
     * @param y            the block Y
     * @param z            the block Z
     * @param blockStateId the new block state id
     */
    @Override
    public abstract void refreshBlockStateId(int x, int y, int z, short blockStateId);

    /**
     * Gets the {@link Data} at a block index.
     *
     * @param index the block index
     * @return the {@link Data} at the block index, null if none
     */
    @Nullable
    public abstract Data getBlockData(int index);

    /**
     * Sets the {@link Data} at a position.
     *
     * @param x    the block X
     * @param y    the block Y
     * @param z    the block Z
     * @param data the new data can be null
     */
    public abstract void setBlockData(int x, int y, int z, @Nullable Data data);

    /**
     * Gets all the block entities in this chunk.
     *
     * @return the block entities in this chunk
     */
    @NotNull
    public abstract Set<Integer> getBlockEntities();

    /**
     * Gets the last time that this chunk changed.
     * <p>
     * "Change" means here data used in {@link ChunkDataPacket}.
     * It is necessary to see if the cached version of this chunk can be used
     * instead of re writing and compressing everything.
     *
     * @return the last change time in milliseconds
     */
    public abstract long getLastChangeTime();

    /**
     * Serializes the chunk into bytes.
     *
     * @return the serialized chunk, can be null if this chunk cannot be serialized
     * @see #readChunk(net.tachyon.binary.BinaryReader, ChunkCallback) which should be able to read what is written in this method
     */
    public abstract byte[] getSerializedData();

    /**
     * Reads the chunk from binary.
     * <p>
     * Used if the chunk is loaded from file.
     *
     * @param reader   the data reader
     *                 WARNING: the data will not necessary be read directly in the same thread,
     *                 be sure that the data is only used for this reading.
     * @param callback the optional callback to execute once the chunk is done reading
     *                 WARNING: this need to be called to notify the instance.
     * @see #getSerializedData() which is responsible for the serialized data given
     */
    public abstract void readChunk(@NotNull BinaryReader reader, @Nullable ChunkCallback callback);

    /**
     * Creates a {@link ChunkDataPacket} with this chunk data ready to be written.
     *
     * @return a new chunk data packet
     */
    @NotNull
    protected abstract ChunkDataPacket createFreshPacket();

    /**
     * Creates a copy of this chunk, including blocks state id, custom block id, biomes, update data.
     * <p>
     * The chunk position (X/Z) can be modified using the given arguments.
     *
     * @param chunkX the chunk X of the copy
     * @param chunkZ the chunk Z of the copy
     * @return a copy of this chunk with a potentially new instance and position
     */
    @NotNull
    public abstract TachyonChunk copy(int chunkX, int chunkZ);

    /**
     * Resets the chunk, this means clearing all the data making it empty.
     */
    public abstract void reset();

    /**
     * Gets the {@link CustomBlock} at a position.
     *
     * @param x the block X
     * @param y the block Y
     * @param z the block Z
     * @return the {@link CustomBlock} at the position
     */
    @Nullable
    public CustomBlock getCustomBlock(int x, int y, int z) {
        final short customBlockId = getCustomBlockId(x, y, z);
        return customBlockId != 0 ? BLOCK_MANAGER.getCustomBlock(customBlockId) : null;
    }

    /**
     * Gets the {@link CustomBlock} at a block index.
     *
     * @param index the block index
     * @return the {@link CustomBlock} at the block index
     */
    @Nullable
    public CustomBlock getCustomBlock(int index) {
        final int x = ChunkUtils.blockIndexToChunkPositionX(index);
        final int y = ChunkUtils.blockIndexToChunkPositionY(index);
        final int z = ChunkUtils.blockIndexToChunkPositionZ(index);
        return getCustomBlock(x, y, z);
    }

    /**
     * Gets the unique identifier of this chunk.
     * <p>
     * WARNING: this UUID is not persistent but randomized once the object is instantiate.
     *
     * @return the chunk identifier
     */
    @NotNull
    public UUID getUuid() {
        return identifier;
    }

    public Biome[] getBiomes() {
        return biomes;
    }

    /**
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    public int getChunkX() {
        return chunkX;
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public boolean hasSky() {
        return hasSky;
    }

    /**
     * Creates a {@link Position} object based on this chunk.
     *
     * @return the position of this chunk
     */
    @NotNull
    public Position toPosition() {
        return new Position(CHUNK_SIZE_Z * getChunkX(), 0, CHUNK_SIZE_Z * getChunkZ());
    }

    /**
     * Gets if this chunk will or had been loaded with a {@link ChunkGenerator}.
     * <p>
     * If false, the chunk will be entirely empty when loaded.
     *
     * @return true if this chunk is affected by a {@link ChunkGenerator}
     */
    public boolean shouldGenerate() {
        return shouldGenerate;
    }

    /**
     * Gets if this chunk is read-only.
     * <p>
     * Being read-only should prevent block placing/breaking and setting block from an {@link World}.
     * It does not affect {@link IChunkLoader} and {@link ChunkGenerator}.
     *
     * @return true if the chunk is read-only
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Changes the read state of the chunk.
     * <p>
     * Being read-only should prevent block placing/breaking and setting block from an {@link World}.
     * It does not affect {@link IChunkLoader} and {@link ChunkGenerator}.
     *
     * @param readOnly true to make the chunk read-only, false otherwise
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Changes this chunk columnar space.
     *
     * @param columnarSpace the new columnar space
     */
    public void setColumnarSpace(PFColumnarSpace columnarSpace) {
        this.columnarSpace = columnarSpace;
    }

    /**
     * Gets a {@link ChunkDataPacket} which should contain the full chunk.
     *
     * @return a fresh full chunk data packet
     */
    public ChunkDataPacket getFreshFullDataPacket() {
        ChunkDataPacket fullDataPacket = createFreshPacket();
        fullDataPacket.fullChunk = true;
        return fullDataPacket;
    }

    /**
     * Gets a {@link ChunkDataPacket} which should contain the non-full chunk.
     *
     * @return a fresh non-full chunk data packet
     */
    @NotNull
    public ChunkDataPacket getFreshPartialDataPacket() {
        ChunkDataPacket fullDataPacket = createFreshPacket();
        fullDataPacket.fullChunk = false;
        return fullDataPacket;
    }

    /**
     * Used to verify if the chunk should still be kept in memory.
     *
     * @return true if the chunk is loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + chunkX + ":" + chunkZ + "]";
    }

    /**
     * Sends the chunk to {@code player} and add it to the player viewing chunks collection
     * and send a {@link PlayerChunkLoadEvent}.
     *
     * @param player the viewer to add
     * @return true if the player has just been added to the viewer collection
     */
    @Override
    public boolean addViewer(@NotNull Player player) {
        final boolean result = this.viewers.add(player);
        // Add to the viewable chunks set
        Tachyon.getUnsafe().addViewableChunk(player, this);
        // Send the chunk data & light packets to the player
        sendChunk(player);
        if (result) {
            PlayerChunkLoadEvent playerChunkLoadEvent = new PlayerChunkLoadEvent(player, chunkX, chunkZ);
            player.callEvent(PlayerChunkLoadEvent.class, playerChunkLoadEvent);
        }

        return result;
    }

    /**
     * Removes the chunk to the player viewing chunks collection
     * and send a {@link PlayerChunkUnloadEvent}.
     *
     * @param player the viewer to remove
     * @return true if the player has just been removed to the viewer collection
     */
    @Override
    public boolean removeViewer(@NotNull Player player) {
        final boolean result = this.viewers.remove(player);

        // Remove from the viewable chunks set
        Tachyon.getUnsafe().removeViewableChunk(player, this);

        if (result) {
            PlayerChunkUnloadEvent playerChunkUnloadEvent = new PlayerChunkUnloadEvent(player, chunkX, chunkZ);
            player.callEvent(PlayerChunkUnloadEvent.class, playerChunkUnloadEvent);
        }

        return result;
    }

    @NotNull
    @Override
    public Set<Player> getViewers() {
        return unmodifiableViewers;
    }

    @Nullable
    @Override
    public Data getData() {
        return data;
    }

    @Override
    public void setData(@Nullable Data data) {
        this.data = data;
    }

    /**
     * Sends the chunk data to {@code player}.
     *
     * @param player the player
     */
    public synchronized void sendChunk(@NotNull Player player) {
        // Only send loaded chunk
        if (!isLoaded())
            return;

        final PlayerConnection playerConnection = player.getPlayerConnection();

        // Retrieve & send the buffer to the connection
        playerConnection.sendPacket(getFreshFullDataPacket());
    }

    public synchronized void sendChunk() {
        if (!isLoaded()) {
            return;
        }

        sendPacketToViewers(getFreshFullDataPacket());
    }

    /**
     * Sends a full {@link ChunkDataPacket} to {@code player}.
     *
     * @param player the player to update the chunk to
     */
    public synchronized void sendChunkUpdate(@NotNull Player player) {
        final PlayerConnection playerConnection = player.getPlayerConnection();
        playerConnection.sendPacket(getFreshFullDataPacket());
    }

    /**
     * Sends a full {@link ChunkDataPacket} to all chunk viewers.
     */
    public synchronized void sendChunkUpdate() {
        Tachyon.getServer().sendGroupedPacket(getViewers(), getFreshPartialDataPacket());
    }

    /**
     * Sends a chunk section update packet to {@code player}.
     *
     * @param section the section to update
     * @param player  the player to send the packet to
     * @throws IllegalArgumentException if {@code section} is not a valid section
     */
    public synchronized void sendChunkSectionUpdate(int section, @NotNull Player player) {
        Check.argCondition(!MathUtils.isBetween(section, 0, CHUNK_SECTION_COUNT),
                "The chunk section " + section + " does not exist");

        player.getPlayerConnection().sendPacket(createChunkSectionUpdatePacket(section));
    }

    /**
     * Gets the {@link ChunkDataPacket} to update a single chunk section.
     *
     * @param section the chunk section to update
     * @return the {@link ChunkDataPacket} to update a single chunk section
     */
    @NotNull
    protected ChunkDataPacket createChunkSectionUpdatePacket(int section) {
        ChunkDataPacket chunkDataPacket = getFreshPartialDataPacket();
        chunkDataPacket.fullChunk = false;
        int[] sections = new int[CHUNK_SECTION_COUNT];
        sections[section] = 1;
        chunkDataPacket.sections = sections;
        return chunkDataPacket;
    }

    /**
     * Sets the chunk as "unloaded".
     */
    public void unload() {
        this.loaded = false;
    }

    /**
     * Gets if a block state id represents a block entity.
     *
     * @param blockStateId the block state id to check
     * @return true if {@code blockStateId} represents a block entity
     */
    protected boolean isBlockEntity(short blockStateId) {
        final Block block = Block.fromStateId(blockStateId);
        return block.hasBlockEntity();
    }

    /**
     * Gets the index of a position, used to store blocks.
     *
     * @param x the block X
     * @param y the block Y
     * @param z the block Z
     * @return the block index
     */
    public int getBlockIndex(int x, int y, int z) {
        return ChunkUtils.getBlockIndex(x, y, z);
    }
}