package net.tachyon.event;

import net.tachyon.event.types.Event;
import org.jetbrains.annotations.NotNull;

public interface EventListener<T extends Event> {

    @NotNull Class<T> getEventClass();

    void handle(@NotNull T event);


    static <T extends Event> EventCallback<T> createEventCallback(@NotNull EventListener<T> listener) {
        return listener::handle;
    }
}
