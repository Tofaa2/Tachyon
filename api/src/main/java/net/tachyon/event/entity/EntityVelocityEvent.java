package net.tachyon.event.entity;

import net.tachyon.coordinate.Vec;
import net.tachyon.entity.Entity;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.event.types.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a velocity is applied to an entity using {@link net.tachyon.entity.Entity#setVelocity(Vec)}.
 */
public class EntityVelocityEvent extends EntityEvent implements CancellableEvent {

    private Vec velocity;

    private boolean cancelled;

    public EntityVelocityEvent(@NotNull Entity entity, @NotNull Vec velocity) {
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
    public Entity getEntity() {
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
