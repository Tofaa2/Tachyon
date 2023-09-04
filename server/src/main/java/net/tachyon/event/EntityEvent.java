package net.tachyon.event;

import net.tachyon.entity.TachyonEntity;
import org.jetbrains.annotations.NotNull;

public class EntityEvent extends Event {

    protected final TachyonEntity entity;

    public EntityEvent(@NotNull TachyonEntity entity) {
        this.entity = entity;
    }

    /**
     * Gets the entity of this event.
     *
     * @return the entity
     */
    @NotNull
    public TachyonEntity getEntity() {
        return entity;
    }
}
