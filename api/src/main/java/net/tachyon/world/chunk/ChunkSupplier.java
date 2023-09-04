package net.tachyon.world.chunk;

import net.tachyon.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used to customize which type of {@link Chunk} an implementation should use.
 */
@FunctionalInterface
public interface ChunkSupplier {

    /**
     * Creates a {@link Chunk} object.
     *
     * @param biomes the biomes of the chunk, can be null
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     * @param hasSky whether the instance this chunk is a part of has a sky
     * @return a newly {@link Chunk} object, cannot be null
     */
    @NotNull
    Chunk createChunk(@Nullable Biome[] biomes, int chunkX, int chunkZ, boolean hasSky);
}
