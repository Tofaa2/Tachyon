package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the player swings his hand.
 */
public class PlayerHandAnimationEvent extends PlayerEvent implements CancellableEvent {

    private boolean cancelled;

    public PlayerHandAnimationEvent(@NotNull TachyonPlayer player) {
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
