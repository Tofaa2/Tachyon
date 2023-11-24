package net.tachyon.event.modern;

public interface CancellableEvent extends Event {

    /**
     * Gets the cancelled state of the event.
     * @return the cancelled state
     */
    boolean isCancelled();

    /**
     * Sets the cancelled state of the event. If an event is cancelled, actions it handles will not be performed.
     * @param cancelled the cancelled state
     */
    void setCancelled(boolean cancelled);

}
