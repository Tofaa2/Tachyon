package net.tachyon.event.entity;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an entity ticks itself.
 * Same event instance used for all tick events for the same entity.
 */
public class EntityTickEvent extends EntityEvent {

    public EntityTickEvent(@NotNull TachyonEntity entity) {
        super(entity);
    }

}
