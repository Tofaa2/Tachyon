package net.tachyon.world.chunk;

import net.tachyon.world.World;
import net.tachyon.world.batch.ChunkBatch;
import net.tachyon.block.Block;
import net.tachyon.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Responsible for the {@link TachyonChunk} generation, can be set using {@link World#setChunkGenerator(ChunkGenerator)}.
 * <p>
 * Called if the instance {@link IChunkLoader} hasn't been able to load the chunk.
 */
public interface ChunkGenerator {

    /**
     * Called when the blocks in the {@link TachyonChunk} should be set using {@link ChunkBatch#setBlock(int, int, int, Block)}
     * or similar.
     * <p>
     * WARNING: all positions are chunk-based (0-15).
     *
     * @param batch  the {@link ChunkBatch} which will be flush after the generation
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ);

    /**
     * Defines all the {@link Biome} in the {@link TachyonChunk}.
     *
     * @param biomes the array of {@link Biome}
     * @param chunkX the chunk X
     * @param chunkZ the chunk Z
     */
    void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ);

    /**
     * Gets all the {@link ChunkPopulator} of this generator.
     *
     * @return a {@link List} of {@link ChunkPopulator}, can be null or empty
     */
    @Nullable
    List<ChunkPopulator> getPopulators();

}
