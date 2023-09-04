package net.tachyon.instance;

import net.tachyon.coordinate.Point;
import net.tachyon.data.Data;
import net.tachyon.entity.Player;
import net.tachyon.storage.StorageLocation;
import net.tachyon.world.chunk.ChunkCallback;
import net.tachyon.world.SharedWorld;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

/**
 * The {@link SharedInstance} is an instance that shares the same chunks as its linked {@link InstanceContainer},
 * entities are separated.
 */
public class SharedInstance extends Instance implements SharedWorld {

    private final InstanceContainer instanceContainer;

    public SharedInstance(@NotNull UUID uniqueId, @NotNull InstanceContainer instanceContainer) {
        super(uniqueId, instanceContainer.getDimensionType(), instanceContainer.getLevelType());
        this.instanceContainer = instanceContainer;
    }

    @Override
    public void refreshBlockStateId(@NotNull Point blockPosition, short blockStateId) {
        this.instanceContainer.refreshBlockStateId(blockPosition, blockStateId);
    }

    @Override
    public boolean breakBlock(@NotNull Player player, @NotNull Point blockPosition) {
        return instanceContainer.breakBlock(player, blockPosition);
    }

    @Override
    public void loadChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        this.instanceContainer.loadChunk(chunkX, chunkZ, callback);
    }

    @Override
    public void loadOptionalChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        this.instanceContainer.loadOptionalChunk(chunkX, chunkZ, callback);
    }

    @Override
    public void unloadChunk(@NotNull TachyonChunk chunk) {
        instanceContainer.unloadChunk(chunk);
    }

    @Override
    public TachyonChunk getChunk(int chunkX, int chunkZ) {
        return instanceContainer.getChunk(chunkX, chunkZ);
    }

    @Override
    public void saveChunkToStorage(@NotNull TachyonChunk chunk, @Nullable Runnable callback) {
        this.instanceContainer.saveChunkToStorage(chunk, callback);
    }

    @Override
    public void saveChunksToStorage(@Nullable Runnable callback) {
        instanceContainer.saveChunksToStorage(callback);
    }

    @Override
    public void setChunkGenerator(ChunkGenerator chunkGenerator) {
        this.instanceContainer.setChunkGenerator(chunkGenerator);
    }

    @Override
    public ChunkGenerator getChunkGenerator() {
        return instanceContainer.getChunkGenerator();
    }

    @NotNull
    @Override
    public Collection<Chunk> getChunks() {
        return instanceContainer.getChunks();
    }

    @Override
    public StorageLocation getStorageLocation() {
        return instanceContainer.getStorageLocation();
    }

    @Override
    public void setStorageLocation(StorageLocation storageLocation) {
        this.instanceContainer.setStorageLocation(storageLocation);
    }

    @Override
    public void retrieveChunk(int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        this.instanceContainer.retrieveChunk(chunkX, chunkZ, callback);
    }

    @Override
    protected void createChunk(int chunkX, int chunkZ, ChunkCallback callback) {
        this.instanceContainer.createChunk(chunkX, chunkZ, callback);
    }

    @Override
    public void enableAutoChunkLoad(boolean enable) {
        instanceContainer.enableAutoChunkLoad(enable);
    }

    @Override
    public boolean hasEnabledAutoChunkLoad() {
        return instanceContainer.hasEnabledAutoChunkLoad();
    }

    @Override
    public boolean isInVoid(@NotNull Point position) {
        return instanceContainer.isInVoid(position);
    }

    @Override
    public void setBlockStateId(int x, int y, int z, short blockStateId, Data data) {
        this.instanceContainer.setBlockStateId(x, y, z, blockStateId, data);
    }

    @Override
    public void setCustomBlock(int x, int y, int z, short customBlockId, Data data) {
        this.instanceContainer.setCustomBlock(x, y, z, customBlockId, data);
    }

    @Override
    public void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, Data data) {
        this.instanceContainer.setSeparateBlocks(x, y, z, blockStateId, customBlockId, data);
    }

    @Override
    public void scheduleUpdate(@NotNull Duration duration, @NotNull Point position) {
        this.instanceContainer.scheduleUpdate(duration, position);
    }

    /**
     * Gets the {@link InstanceContainer} from where this instance takes its chunks from.
     *
     * @return the associated {@link InstanceContainer}
     */
    @NotNull
    public InstanceContainer getInstanceContainer() {
        return instanceContainer;
    }

    @Override
    public @NotNull World getSharedInstance() {
        return instanceContainer;
    }
}
