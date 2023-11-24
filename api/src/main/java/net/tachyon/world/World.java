package net.tachyon.world;

import net.tachyon.block.Block;
import net.tachyon.block.BlockModifier;
import net.tachyon.block.CustomBlock;
import net.tachyon.chat.ForwardingPlayerAudience;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.data.Data;
import net.tachyon.entity.Entity;
import net.tachyon.entity.EntityType;
import net.tachyon.entity.Player;
import net.tachyon.event.EventHandler;
import net.tachyon.world.chunk.Chunk;
import net.tachyon.world.chunk.ChunkCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface World extends ForwardingPlayerAudience, BlockModifier, EventHandler {

    @NotNull UUID getUuid();

    @NotNull DimensionType getDimensionType();

    @NotNull LevelType getLevelType();

    @NotNull WorldBorder getWorldBorder();

    @NotNull Set<Entity> getChunkEntities(@NotNull Chunk chunk);

    void setExplosionSupplier(@Nullable ExplosionSupplier explosionSupplier);

    @Nullable ExplosionSupplier getExplosionSupplier();

    void explode(float centerX, float centerY, float centerZ, float strength, @Nullable Data additionalData);

    void explode(float centerX, float centerY, float centerZ, float strength);

    int getTimeRate();

    void setTimeRate(int timeRate);

    long getWorldAge();

    long getTime();

    void setTime(long time);

    boolean hasEnabledAutoChunkLoad();

    void enableAutoChunkLoad(boolean enable);

    boolean isRegistered();

    void loadChunk(int chunkX, int chunkZ);

    void refreshBlockId(@NotNull Point point, @NotNull Block block);

    void refreshBlockId(int x, int y, int z, @NotNull Block block);

    void refreshBlockStateId(int x, int y, int z, short blockStateId);

    @NotNull Collection<Chunk> getChunks();

    @NotNull Collection<Player> getPlayers();

    @NotNull Collection<Entity> getEntities();

    @Nullable Chunk getChunk(int chunkX, int chunkZ);

    @Nullable Chunk getChunkAt(Point blockPosition);

    @Nullable Data getBlockData(int x, int y, int z);

    @Nullable Data getBlockData(Point point);

    short getBlockStateId(int x, int y, int z);

    short getBlockStateId(Point point);

    void loadOptionalChunk(int chunkX, int chunkY, @Nullable ChunkCallback callback);

    @Nullable CustomBlock getCustomBlock(int x, int y, int z);

    /**
     * Spawns an entity to this world.
     * @param uuid the uuid of the entity to spawn
     * @param position the position to spawn the entity at
     * @param entityType the type of the entity to spawn
     * @return the entity spawned. Depending on the type, you can use its respective metadata class to modify it.
     */
    @NotNull Entity spawnEntity(@NotNull UUID uuid, @NotNull Position position, @NotNull EntityType entityType);

}
