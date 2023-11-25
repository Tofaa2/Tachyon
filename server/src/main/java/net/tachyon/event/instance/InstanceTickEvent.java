package net.tachyon.event.instance;

import net.tachyon.event.InstanceEvent;
import net.tachyon.world.Instance;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an instance processes a tick.
 */
public class InstanceTickEvent extends InstanceEvent {

    private final int duration;

    public InstanceTickEvent(@NotNull Instance instance, long time, long lastTickAge) {
        super(instance);
        this.duration = (int) (time - lastTickAge);
    }

    /**
     * Gets the duration of the tick in ms.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }
}