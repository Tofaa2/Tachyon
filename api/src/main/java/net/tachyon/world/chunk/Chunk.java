package net.tachyon.world.chunk;

import net.tachyon.Viewable;
import net.tachyon.block.CustomBlock;
import net.tachyon.data.Data;
import net.tachyon.data.DataContainer;
import net.tachyon.entity.Player;
import net.tachyon.entity.pathfinding.PFColumnarSpace;
import net.tachyon.world.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Chunk extends Viewable, DataContainer {

    int CHUNK_SIZE_X = 16;
    int CHUNK_SIZE_Y = 256;
    int CHUNK_SIZE_Z = 16;
    int CHUNK_SECTION_SIZE = 16;
    int CHUNK_SECTION_COUNT = CHUNK_SIZE_Y / CHUNK_SECTION_SIZE;
    int BIOME_COUNT = 1024; // 4x4x4 blocks group

    @NotNull UUID getUuid();

    void sendChunk(@NotNull Player player);

    void sendChunk();

    short getBlockStateId(int x, int y, int z);

    short getCustomBlockId(int x, int y, int z);

    void setColumnarSpace(PFColumnarSpace columnarSpace);

    PFColumnarSpace getColumnarSpace();

    void refreshBlockValue(int x, int y, int z, short blockStateId, short customBlockId);

    void refreshBlockStateId(int x, int y, int z, short blockStateId);


    @Nullable Data getBlockData(int index);

    void setBlockData(int x, int y, int z, @Nullable Data data);

    long getLastChangeTime();

    byte[] getSerializedData();

    void reset();

    Biome[] getBiomes();

    boolean hasSky();

    int getChunkX();

    int getChunkZ();

    @Nullable CustomBlock getCustomBlock(int x, int y, int z);

    boolean isReadOnly();

    void setReadOnly(boolean readOnly);

    boolean isLoaded();

    @Nullable Data getData();

    void sendChunkUpdate(@NotNull Player player);

    void sendChunkUpdate();

    void sendChunkSectionUpdate(int section, @NotNull Player player);

    void unload();

    int getBlockIndex(int x, int y, int z);

    @ApiStatus.Internal
    void UNSAFE_setBlock(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data, boolean updatable);

}
