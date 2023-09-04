package net.tachyon.event.entity;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.EntityEvent;
import net.tachyon.instance.Instance;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a new instance is set for an entity.
 */
public class EntitySpawnEvent extends EntityEvent {

    private final Instance spawnInstance;

    public EntitySpawnEvent(@NotNull TachyonEntity entity, @NotNull Instance spawnInstance) {
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
    public TachyonEntity getEntity() {
        return entity;
    }

    /**
     * Gets the entity new instance.
     *
     * @return the instance
     */
    @NotNull
    public Instance getSpawnInstance() {
        return spawnInstance;
    }

}
