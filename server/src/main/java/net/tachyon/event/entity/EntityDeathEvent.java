package net.tachyon.event.entity;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.EntityEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDeathEvent extends EntityEvent {

    // TODO cause

    public EntityDeathEvent(@NotNull TachyonEntity entity) {
        super(entity);
    }
}
