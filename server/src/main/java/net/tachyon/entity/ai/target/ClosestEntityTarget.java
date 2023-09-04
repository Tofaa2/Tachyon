package net.tachyon.entity.ai.target;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.EntityCreature;
import net.tachyon.entity.TachyonLivingEntity;
import net.tachyon.entity.ai.TargetSelector;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.instance.Instance;
import net.tachyon.utils.chunk.ChunkUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Target the closest targetable entity (based on the class array)
 */
public class ClosestEntityTarget extends TargetSelector {

    private final float range;
    private final Class<? extends TachyonLivingEntity>[] entitiesTarget;

    public ClosestEntityTarget(@NotNull EntityCreature entityCreature, float range,
                               @NotNull Class<? extends TachyonLivingEntity>... entitiesTarget) {
        super(entityCreature);
        this.range = range;
        this.entitiesTarget = entitiesTarget;
    }

    @Override
    public TachyonEntity findTarget() {
        final Instance instance = getEntityCreature().getInstance();
        final TachyonChunk currentChunk = instance.getChunkAt(entityCreature.getPosition());
        if (currentChunk == null) {
            return null;
        }

        final List<TachyonChunk> chunks = getNeighbours(instance, currentChunk.getChunkX(), currentChunk.getChunkZ());

        TachyonEntity entity = null;
        double distance = Double.MAX_VALUE;

        for (TachyonChunk chunk : chunks) {
            final Set<TachyonEntity> entities = instance.getChunkEntities(chunk);

            for (TachyonEntity ent : entities) {

                // Only target living entities
                if (!(ent instanceof TachyonLivingEntity)) {
                    continue;
                }

                // Don't target itself
                if (ent.equals(entityCreature)) {
                    continue;
                }

                if (ent.isRemoved()) {
                    // TachyonEntity not valid
                    return null;
                }

                // Check if the entity type can be targeted
                final Class<? extends TachyonEntity> clazz = ent.getClass();
                boolean correct = false;
                for (Class<? extends TachyonLivingEntity> targetClass : entitiesTarget) {
                    if (targetClass.isAssignableFrom(clazz)) {
                        correct = true;
                        break;
                    }
                }

                if (!correct) {
                    continue;
                }

                // Check distance
                final double d = entityCreature.getDistance(ent);
                if ((entity == null || d < distance) && d < range) {
                    entity = ent;
                    distance = d;
                    continue;
                }
            }
        }

        return entity;
    }

    private List<TachyonChunk> getNeighbours(Instance instance, int chunkX, int chunkZ) {
        List<TachyonChunk> chunks = new ArrayList<>();
        // Constants used to loop through the neighbors
        final int[] posX = {1, 0, -1};
        final int[] posZ = {1, 0, -1};

        for (int x : posX) {
            for (int z : posZ) {

                final int targetX = chunkX + x;
                final int targetZ = chunkZ + z;
                final TachyonChunk chunk = instance.getChunk(targetX, targetZ);
                if (ChunkUtils.isLoaded(chunk)) {
                    // TachyonChunk is loaded, add it
                    chunks.add(chunk);
                }

            }
        }
        return chunks;
    }

}
