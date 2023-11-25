package net.tachyon.event;

import net.tachyon.event.types.Event;
import net.tachyon.world.Instance;
import org.jetbrains.annotations.NotNull;

public class InstanceEvent extends Event {

    protected final Instance instance;

    public InstanceEvent(@NotNull Instance instance) {
        this.instance = instance;
    }

    /**
     * Gets the instance.
     *
     * @return instance
     */
    @NotNull
    public Instance getInstance() {
        return instance;
    }
}