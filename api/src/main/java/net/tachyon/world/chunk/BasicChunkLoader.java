package net.tachyon.world.chunk;

import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link IChunkLoader} used by {@link World}
 * <p>
 * It simply save chunk serialized data from {@link TachyonChunk#getSerializedData()}
 * and deserialize it later with {@link TachyonChunk#readChunk(net.tachyon.binary.BinaryReader, ChunkCallback)}.
 * <p>
 */
public class BasicChunkLoader implements IChunkLoader {

    private final static Logger LOGGER = LoggerFactory.getLogger(BasicChunkLoader.class);
    private final World instanceContainer;

    /**
     * <p>
     * The {@link ChunkSupplier} is used to customize which type of {@link TachyonChunk} this loader should use for loading.
     * <p>
     * WARNING: {@link TachyonChunk} implementations do not need to have the same serializing format, be careful.
     *
     * @param instanceContainer the {@link World} linked to this loader
     */
    public BasicChunkLoader(World instanceContainer) {
        this.instanceContainer = instanceContainer;
    }

    @Override
    public void saveChunk(@NotNull TachyonChunk chunk, @Nullable Runnable callback) {
    }

    @Override
    public boolean loadChunk(@NotNull World instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        return false;
    }

    @Override
    public boolean supportsParallelSaving() {
        return true;
    }

    @Override
    public boolean supportsParallelLoading() {
        return true;
    }

    /**
     *
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @return the chunk key
     */
    private static String getChunkKey(int chunkX, int chunkZ) {
        return chunkX + "." + chunkZ;
    }


}
