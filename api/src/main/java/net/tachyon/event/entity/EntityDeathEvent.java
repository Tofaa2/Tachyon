package net.tachyon.event.entity;

import net.tachyon.entity.Entity;
import net.tachyon.event.types.EntityEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDeathEvent extends EntityEvent {

    // TODO cause

    public EntityDeathEvent(@NotNull Entity entity) {
        super(entity);
    }
}
