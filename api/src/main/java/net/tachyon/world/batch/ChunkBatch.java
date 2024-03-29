package net.tachyon.world.batch;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.tachyon.Tachyon;
import net.tachyon.coordinate.Point;
import net.tachyon.data.Data;
import net.tachyon.world.World;
import net.tachyon.world.chunk.TachyonChunk;
import net.tachyon.network.packet.server.play.ChunkDataPacket;
import net.tachyon.network.packet.server.play.MultiBlockChangePacket;
import net.tachyon.utils.block.CustomBlockUtils;
import net.tachyon.utils.OptionalCallback;
import net.tachyon.world.chunk.ChunkCallback;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * A Batch used when all the block changed are contained inside a single chunk.
 * If more than one chunk is needed, use an {@link AbsoluteBlockBatch} instead.
 * <p>
 * The batch can be placed in any chunk in any instance, however it will always remain
 * aligned to a chunk border. If completely translatable block changes are needed, use a
 * {@link RelativeBlockBatch} instead.
 * <p>
 * Coordinates are relative to the chunk (0-15) instead of world coordinates.
 *
 * @see Batch
 */
public class ChunkBatch implements Batch<ChunkCallback> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkBatch.class);

    // Need to be synchronized manually
    // Format: blockIndex/blockStateId/customBlockId (32/16/16 bits)
    private final LongList blocks;

    // Need to be synchronized manually
    // block index - data
    private final Int2ObjectMap<Data> blockDataMap;

    // Available for other implementations to handle.
    protected final CountDownLatch readyLatch;
    private final BatchOption options;

    public ChunkBatch() {
        this(new BatchOption());
    }

    public ChunkBatch(BatchOption options) {
        this(new LongArrayList(), new Int2ObjectOpenHashMap<>(), options);
    }

    protected ChunkBatch(LongList blocks, Int2ObjectMap<Data> blockDataMap, BatchOption options) {
        this(blocks, blockDataMap, options, true);
    }

    private ChunkBatch(LongList blocks, Int2ObjectMap<Data> blockDataMap, BatchOption options, boolean ready) {
        this.blocks = blocks;
        this.blockDataMap = blockDataMap;

        this.readyLatch = new CountDownLatch(ready ? 0 : 1);
        this.options = options;
    }

    @Override
    public void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data) {
        // Cache the entry to be placed later during flush
        final int index = ChunkUtils.getBlockIndex(x, y, z);
        long value = index;
        value = (value << 16) | blockStateId;
        value = (value << 16) | customBlockId;

        synchronized (blocks) {
            this.blocks.add(value);
        }

        if (data != null) {
            synchronized (blockDataMap) {
                this.blockDataMap.put(index, data);
            }
        }
    }

    @Override
    public void clear() {
        synchronized (blocks) {
            this.blocks.clear();
        }
    }

    @Override
    public boolean isReady() {
        return this.readyLatch.getCount() == 0;
    }

    @Override
    public void awaitReady() {
        try {
            this.readyLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("#awaitReady interrupted!", e);
        }
    }

    /**
     * Apply this batch to chunk (0, 0).
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    @Override
    public ChunkBatch apply(@NotNull World instance, @Nullable ChunkCallback callback) {
        return apply(instance, 0, 0, callback);
    }

    /**
     * Apply this batch to the given chunk.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunkX   The x chunk coordinate of the target chunk
     * @param chunkZ   The z chunk coordinate of the target chunk
     * @param callback The callback to be executed when the batch is applied.
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    public ChunkBatch apply(@NotNull World instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        final Chunk chunk = instance.getChunk(chunkX, chunkZ);
        if (chunk == null) {
            LOGGER.warn("Unable to apply ChunkBatch to unloaded chunk ({}, {}) in {}.",
                    chunkX, chunkZ, instance.getUuid());
            return null;
        }
        return apply(instance, chunk, callback);
    }

    /**
     * Apply this batch to the given chunk.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunk    The target chunk
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    public ChunkBatch apply(@NotNull World instance, @NotNull Chunk chunk, @Nullable ChunkCallback callback) {
        return apply(instance, chunk, callback, true);
    }

    /**
     * Apply this batch to the given chunk, and execute the callback
     * immediately when the blocks have been applied, in an unknown thread.
     *
     * @param instance The instance in which the batch should be applied
     * @param chunk    The target chunk
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    public ChunkBatch unsafeApply(@NotNull World instance, @NotNull Chunk chunk, @Nullable ChunkCallback callback) {
        return apply(instance, chunk, callback, false);
    }

    /**
     * Apply this batch to the given chunk, and execute the callback depending on safeCallback.
     *
     * @param instance     The instance in which the batch should be applied
     * @param chunk        The target chunk
     * @param callback     The callback to be executed when the batch is applied
     * @param safeCallback If true, the callback will be executed in the next instance update.
     *                     Otherwise it will be executed immediately upon completion
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    protected ChunkBatch apply(@NotNull World instance,
                               @NotNull Chunk chunk, @Nullable ChunkCallback callback,
                               boolean safeCallback) {
        if (!this.options.isUnsafeApply()) this.awaitReady();

        final ChunkBatch inverse = this.options.shouldCalculateInverse() ? new ChunkBatch(new LongArrayList(), new Int2ObjectOpenHashMap<>(), options, false) : null;
        BLOCK_BATCH_POOL.execute(() -> singleThreadFlush(instance, chunk, inverse, callback, safeCallback));
        return inverse;
    }

    /**
     * Applies this batch in the current thread, executing the callback upon completion.
     */
    private void singleThreadFlush(World instance, Chunk chunk, @Nullable ChunkBatch inverse,
                                   @Nullable ChunkCallback callback, boolean safeCallback) {
        try {
            if (!chunk.isLoaded()) {
                LOGGER.warn("Unable to apply ChunkBatch to unloaded chunk ({}, {}) in {}.",
                        chunk.getChunkX(), chunk.getChunkZ(), instance.getUuid());
                return;
            }

            if (this.options.isFullChunk())
                chunk.reset();

            if (blocks.isEmpty()) {
                OptionalCallback.execute(callback, chunk);
                return;
            }

            final IntSet sections = new IntArraySet();
            synchronized (blocks) {
                for (long block : blocks) {
                    final int section = apply(chunk, block, inverse);
                    sections.add(section);
                }
            }

            if (inverse != null) inverse.readyLatch.countDown();
            updateChunk(instance, chunk, sections, callback, safeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies a single block change given a chunk and a value in the described format.
     *
     * @param chunk The chunk to apply the change
     * @param value block index|state id|custom block id (32|16|16 bits)
     * @return The chunk section which the block was placed
     */
    private int apply(@NotNull Chunk chunk, long value, @Nullable ChunkBatch inverse) {
        final short customBlockId = (short) (value & 0xFFFF);
        final short blockId = (short) ((value >> 16) & 0xFFFF);
        final int index = (int) ((value >> 32) & 0xFFFFFFFFL);

        Data data = null;
        if (!blockDataMap.isEmpty()) {
            synchronized (blockDataMap) {
                data = blockDataMap.get(index);
            }
        }

        final int x = ChunkUtils.blockIndexToChunkPositionX(index);
        final int y = ChunkUtils.blockIndexToChunkPositionY(index);
        final int z = ChunkUtils.blockIndexToChunkPositionZ(index);

        if (inverse != null)
            inverse.setSeparateBlocks(x, y, z, chunk.getBlockStateId(x, y, z), chunk.getCustomBlockId(x, y, z), chunk.getBlockData(index));

        Tachyon.getUnsafe().setBlock(chunk, x, y, z, blockId, customBlockId, data, CustomBlockUtils.hasUpdate(customBlockId));
        return ChunkUtils.getSectionAt(y);
    }

    /**
     * Updates the given chunk for all of its viewers, and executes the callback.
     */
    private void updateChunk(@NotNull World instance, Chunk chunk, IntSet updatedSections, @Nullable ChunkCallback callback, boolean safeCallback) {
        int changedBlocksSize = Integer.MAX_VALUE;
        if (blocks != null) {
            synchronized (blocks) {
                changedBlocksSize = blocks.size();
            }
        }

        // Refresh chunk for viewers
        if (changedBlocksSize < 1024) {
            MultiBlockChangePacket packet = new MultiBlockChangePacket();
            packet.chunkX = chunk.getChunkX();
            packet.chunkZ = chunk.getChunkZ();

            synchronized (blocks) {
                packet.blockChanges = new MultiBlockChangePacket.BlockChange[blocks.size()];

                for (int i = 0; i < blocks.size(); i++) {
                    long value = blocks.getLong(i);

                    final short blockId = (short) ((value >> 16) & 0xFFFF);
                    final int index = (int) ((value >> 32) & 0xFFFFFFFFL);

                    MultiBlockChangePacket.BlockChange change = new MultiBlockChangePacket.BlockChange();
                    Point position = ChunkUtils.getBlockPosition(index, packet.chunkX, packet.chunkZ);
                    change.positionX = position.blockX();
                    change.positionY = position.blockY();
                    change.positionZ = position.blockZ();
                    change.blockStateId = blockId;
                    packet.blockChanges[i] = change;
                }
            }

            chunk.sendPacketsToViewers(packet);
        } else {
            ChunkDataPacket chunkDataPacket = ((TachyonChunk)chunk).getFreshPartialDataPacket();
            int[] sections = new int[Chunk.CHUNK_SECTION_COUNT];
            for (int section : updatedSections)
                sections[section] = 1;
            chunkDataPacket.sections = sections;
            Tachyon.getServer().sendGroupedPacket(chunk.getViewers(), chunkDataPacket);
        }

        Tachyon.getUnsafe().refreshLastBlockChangeTime(instance);
        if (callback != null) {
            if (safeCallback) {
                (instance).scheduleNextTick(inst -> callback.accept(chunk));
            } else {
                callback.accept(chunk);
            }
        }
    }
}