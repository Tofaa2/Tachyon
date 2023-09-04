package net.tachyon.instance;

import net.tachyon.instance.batch.ChunkBatch;

public interface ChunkPopulator {

    void populateChunk(ChunkBatch batch, TachyonChunk chunk);

}
