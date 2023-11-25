package net.tachyon.world.chunk;

import net.tachyon.world.batch.ChunkBatch;
import net.tachyon.world.chunk.TachyonChunk;

public interface ChunkPopulator {

    void populateChunk(ChunkBatch batch, TachyonChunk chunk);

}
