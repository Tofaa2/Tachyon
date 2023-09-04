package net.tachyon.instance.batch;

import net.tachyon.data.Data;
import net.tachyon.instance.*;
import net.tachyon.utils.block.CustomBlockUtils;
import net.tachyon.world.chunk.ChunkCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChunkGenerationBatch extends ChunkBatch {
    private final InstanceContainer instance;
    private final TachyonChunk chunk;

    public ChunkGenerationBatch(InstanceContainer instance, TachyonChunk chunk) {
        super(null, null, new BatchOption());

        this.instance = instance;
        this.chunk = chunk;
    }

    @Override
    public void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data) {
        chunk.UNSAFE_setBlock(x, y, z, blockStateId, customBlockId, data, CustomBlockUtils.hasUpdate(customBlockId));
    }

    public void generate(@NotNull ChunkGenerator chunkGenerator, @Nullable ChunkCallback callback) {
        BLOCK_BATCH_POOL.execute(() -> {
            synchronized (chunk) {
                final List<ChunkPopulator> populators = chunkGenerator.getPopulators();
                final boolean hasPopulator = populators != null && !populators.isEmpty();

                chunkGenerator.generateChunkData(this, chunk.getChunkX(), chunk.getChunkZ());

                if (hasPopulator) {
                    for (ChunkPopulator chunkPopulator : populators) {
                        chunkPopulator.populateChunk(this, chunk);
                    }
                }

                // Update the chunk.
                this.chunk.sendChunk();
                this.instance.refreshLastBlockChangeTime();
                if (callback != null)
                    this.instance.scheduleNextTick(inst -> callback.accept(this.chunk));
            }
        });
    }

    @Override
    public void clear() {
        throw new IllegalStateException("#clear is not supported for chunk generation batch.");
    }

    @Override
    protected ChunkBatch apply(@NotNull Instance instance, @NotNull TachyonChunk chunk, @Nullable ChunkCallback callback, boolean safeCallback) {
        throw new IllegalStateException("#apply is not supported for chunk generation batch.");
    }
}
