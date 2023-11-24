package net.tachyon.event.entity;

import net.tachyon.entity.Entity;
import net.tachyon.event.types.EntityEvent;
import net.tachyon.instance.Instance;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a new instance is set for an entity.
 */
public class EntitySpawnEvent extends EntityEvent {

    private final World spawnInstance;

    public EntitySpawnEvent(@NotNull Entity entity, @NotNull World spawnInstance) {
        super(entity);
        this.spawnInstance = spawnInstance;
    }

    /**
     * Gets the entity who spawned in the instance.
     *
     * @return the entity
     */
    @NotNull
    @Override
    public Entity getEntity() {
        return entity;
    }

    /**
     * Gets the entity new instance.
     *
     * @return the instance
     */
    @NotNull
    public World getSpawnInstance() {
        return spawnInstance;
    }

}
