package net.tachyon.event;

import net.tachyon.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityEvent extends Event {

    protected final Entity entity;

    public EntityEvent(@NotNull Entity entity) {
        this.entity = entity;
    }

    /**
     * Gets the entity of this event.
     *
     * @return the entity
     */
    @NotNull
    public Entity getEntity() {
        return entity;
    }
}
