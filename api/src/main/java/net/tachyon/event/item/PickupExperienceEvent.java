package net.tachyon.event.item;

import net.tachyon.entity.ExperienceOrb;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.Event;
import org.jetbrains.annotations.NotNull;

public class PickupExperienceEvent extends Event implements CancellableEvent {

    private final ExperienceOrb experienceOrb;
    private short experienceCount;

    private boolean cancelled;

    public PickupExperienceEvent(@NotNull ExperienceOrb experienceOrb) {
        this.experienceOrb = experienceOrb;
        this.experienceCount = experienceOrb.getExperienceCount();
    }

    @NotNull
    public ExperienceOrb getExperienceOrb() {
        return experienceOrb;
    }

    public short getExperienceCount() {
        return experienceCount;
    }

    public void setExperienceCount(short experienceCount) {
        this.experienceCount = experienceCount;
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
