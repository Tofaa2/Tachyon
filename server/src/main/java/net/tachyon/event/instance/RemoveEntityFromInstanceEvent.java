package net.tachyon.event.instance;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.InstanceEvent;
import net.tachyon.instance.Instance;
import org.jetbrains.annotations.NotNull;

/**
 * Called by an Instance when an entity is removed from it.
 */
public class RemoveEntityFromInstanceEvent extends InstanceEvent implements CancellableEvent {

    private final TachyonEntity entity;

    private boolean cancelled;

    public RemoveEntityFromInstanceEvent(@NotNull Instance instance, @NotNull TachyonEntity entity) {
        super(instance);
        this.entity = entity;
    }

    /**
     * Gets the entity being removed.
     *
     * @return entity being removed
     */
    @NotNull
    public TachyonEntity getEntity() {
        return entity;
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
