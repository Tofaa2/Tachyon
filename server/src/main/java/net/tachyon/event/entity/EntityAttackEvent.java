package net.tachyon.event.entity;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.EntityCreature;
import net.tachyon.event.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player does a left click on an entity or with
 * {@link EntityCreature#attack(TachyonEntity)}.
 */
public class EntityAttackEvent extends EntityEvent {

    private final TachyonEntity target;

    public EntityAttackEvent(@NotNull TachyonEntity source, @NotNull TachyonEntity target) {
        super(source);
        this.target = target;
    }

    /**
     * @return the target of the attack
     */
    @NotNull
    public TachyonEntity getTarget() {
        return target;
    }
}
