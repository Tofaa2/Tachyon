package net.tachyon.utils.entity;

import net.tachyon.Tachyon;
import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.entity.Entity;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;
import net.tachyon.world.chunk.TachyonChunk;
import net.tachyon.block.Block;
import net.tachyon.utils.ChunkUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class EntityUtils {

    private EntityUtils() {

    }

    public static void forEachRange(@NotNull World instance, @NotNull Position position,
                                    int viewDistance,
                                    @NotNull Consumer<Entity> consumer) {
        final long[] chunksInRange = ChunkUtils.getChunksInRange(position, viewDistance);

        for (long chunkIndex : chunksInRange) {
            final int chunkX = ChunkUtils.getChunkCoordX(chunkIndex);
            final int chunkZ = ChunkUtils.getChunkCoordZ(chunkIndex);
            final TachyonChunk chunk = (TachyonChunk) instance.getChunk(chunkX, chunkZ);
            if (chunk == null)
                continue;
            instance.getChunkEntities(chunk).forEach(consumer);
        }
    }

    public static boolean areVisible(@NotNull Entity ent1, @NotNull Entity ent2) {
        if (ent1.getWorld() == null || ent2.getWorld() == null)
            return false;
        if (!ent1.getWorld().equals(ent2.getWorld()))
            return false;

        final Chunk chunk = ent1.getWorld().getChunkAt(ent1.getPosition());

        final long[] visibleChunksEntity = ChunkUtils.getChunksInRange(ent2.getPosition(), Tachyon.getServer().getEntityViewDistance());
        for (long visibleChunk : visibleChunksEntity) {
            final int chunkX = ChunkUtils.getChunkCoordX(visibleChunk);
            final int chunkZ = ChunkUtils.getChunkCoordZ(visibleChunk);
            if (chunk.getChunkX() == chunkX && chunk.getChunkZ() == chunkZ)
                return true;
        }

        return false;
    }

    public static boolean isOnGround(@NotNull Entity entity) {
        final World instance = entity.getWorld();
        if (instance == null)
            return false;

        final Position entityPosition = entity.getPosition();

        // TODO: check entire bounding box
        final Point blockPosition = entityPosition.toVector().subtract(0, 1, 0);
        try {
            final short blockStateId = instance.getBlockStateId(blockPosition);
            final Block block = Block.fromStateId(blockStateId);
            return block.isSolid();
        } catch (NullPointerException e) {
            // Probably an entity at the border of an unloaded chunk
            return false;
        }
    }

}
