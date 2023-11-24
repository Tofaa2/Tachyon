package net.tachyon.entity.ai.target;

import net.tachyon.entity.Entity;
import net.tachyon.entity.EntityCreature;
import net.tachyon.entity.LivingEntity;
import net.tachyon.entity.ai.TargetSelector;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Target the closest targetable entity (based on the class array)
 */
public class ClosestEntityTarget extends TargetSelector {

    private final float range;
    private final Class<? extends LivingEntity>[] entitiesTarget;

    public ClosestEntityTarget(@NotNull EntityCreature entityCreature, float range,
                               @NotNull Class<? extends LivingEntity>... entitiesTarget) {
        super(entityCreature);
        this.range = range;
        this.entitiesTarget = entitiesTarget;
    }

    @Override
    public Entity findTarget() {
        final World instance = getEntityCreature().getWorld();
        final Chunk currentChunk = instance.getChunkAt(entityCreature.getPosition());
        if (currentChunk == null) {
            return null;
        }

        final List<Chunk> chunks = getNeighbours(instance, currentChunk.getChunkX(), currentChunk.getChunkZ());

        Entity entity = null;
        double distance = Double.MAX_VALUE;

        for (Chunk chunk : chunks) {
            final Set<Entity> entities = instance.getChunkEntities(chunk);

            for (Entity ent : entities) {

                // Only target living entities
                if (!(ent instanceof LivingEntity)) {
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
                final Class<? extends Entity> clazz = ent.getClass();
                boolean correct = false;
                for (Class<? extends LivingEntity> targetClass : entitiesTarget) {
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

    private List<Chunk> getNeighbours(World instance, int chunkX, int chunkZ) {
        List<Chunk> chunks = new ArrayList<>();
        // Constants used to loop through the neighbors
        final int[] posX = {1, 0, -1};
        final int[] posZ = {1, 0, -1};

        for (int x : posX) {
            for (int z : posZ) {

                final int targetX = chunkX + x;
                final int targetZ = chunkZ + z;
                final Chunk chunk = instance.getChunk(targetX, targetZ);
                if (ChunkUtils.isLoaded(chunk)) {
                    // TachyonChunk is loaded, add it
                    chunks.add(chunk);
                }

            }
        }
        return chunks;
    }

}
