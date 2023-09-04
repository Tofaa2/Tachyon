package net.tachyon.event.entity;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.EntityEvent;
import net.tachyon.utils.time.TimeUnit;

public class EntityFireEvent extends EntityEvent implements CancellableEvent {

    private int duration;
    private TimeUnit timeUnit;

    private boolean cancelled;

    public EntityFireEvent(TachyonEntity entity, int duration, TimeUnit timeUnit) {
        super(entity);
        setFireTime(duration, timeUnit);
    }

    public long getFireTime(TimeUnit timeUnit) {
        switch (timeUnit) {
            case TICK:
                return duration;
            case MILLISECOND:
                return timeUnit.toMilliseconds(duration);
            default:
                // Unexpected
                return -1;
        }
    }

    public void setFireTime(int duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
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
