package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the player swings his hand.
 */
public class PlayerHandAnimationEvent extends PlayerEvent implements CancellableEvent {

    private boolean cancelled;

    public PlayerHandAnimationEvent(@NotNull Player player) {
        super(player);
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
