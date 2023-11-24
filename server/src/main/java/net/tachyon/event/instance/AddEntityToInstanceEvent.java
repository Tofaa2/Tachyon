package net.tachyon.event.instance;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.event.InstanceEvent;
import net.tachyon.instance.Instance;
import org.jetbrains.annotations.NotNull;

/**
 * Called by an Instance when an entity is added to it.
 * Can be used attach data.
 */
public class AddEntityToInstanceEvent extends InstanceEvent implements CancellableEvent {

    private final TachyonEntity entity;

    private boolean cancelled;

    public AddEntityToInstanceEvent(@NotNull Instance instance, @NotNull TachyonEntity entity) {
        super(instance);
        this.entity = entity;
    }

    /**
     * TachyonEntity being added.
     *
     * @return the entity being added
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
