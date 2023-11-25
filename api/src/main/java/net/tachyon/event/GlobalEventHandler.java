package net.tachyon.event;

import net.tachyon.event.types.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Object containing all the global event listeners.
 */
public final class GlobalEventHandler implements EventHandler {

    private static GlobalEventHandler instance;


    // Events
    private final Map<Class<? extends Event>, Collection<EventCallback>> eventCallbacks = new ConcurrentHashMap<>();

    public GlobalEventHandler() {
        if (instance != null) {
            throw new IllegalStateException("GlobalEventHandler already initialized");
        }
        instance = this;
    }

    public <T extends Event> void  registerListener(EventListener<T> listener) {
        addEventCallback(listener.getEventClass(), EventListener.createEventCallback(listener));
    }

    @NotNull
    @Override
    public Map<Class<? extends Event>, Collection<EventCallback>> getEventCallbacksMap() {
        return eventCallbacks;
    }

}
