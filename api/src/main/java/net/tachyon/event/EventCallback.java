package net.tachyon.event;

import net.tachyon.event.types.Event;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface EventCallback<E extends Event> {

    void run(@NotNull E event);

}
