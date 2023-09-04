package net.tachyon.event.entity;

import net.tachyon.coordinate.Vec;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a velocity is applied to an entity using {@link TachyonEntity#setVelocity(Vec)}.
 */
public class EntityVelocityEvent extends EntityEvent implements CancellableEvent {

    private Vec velocity;

    private boolean cancelled;

    public EntityVelocityEvent(@NotNull TachyonEntity entity, @NotNull Vec velocity) {
        super(entity);
        this.velocity = velocity;
    }

    /**
     * Gets the enity who will be affected by {@link #getVelocity()}.
     *
     * @return the entity
     */
    @NotNull
    @Override
    public TachyonEntity getEntity() {
        return entity;
    }

    /**
     * Gets the velocity which will be applied.
     *
     * @return the velocity
     */
    @NotNull
    public Vec getVelocity() {
        return velocity;
    }

    /**
     * Changes the applied velocity.
     *
     * @param velocity the new velocity
     */
    public void setVelocity(@NotNull Vec velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
