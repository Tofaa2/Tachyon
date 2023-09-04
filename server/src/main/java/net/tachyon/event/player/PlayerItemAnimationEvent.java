package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Used when a {@link Player} finish the animation of an item.
 *
 * @see ItemAnimationType
 */
public class PlayerItemAnimationEvent extends PlayerEvent implements CancellableEvent {

    private final ItemAnimationType armAnimationType;

    private boolean cancelled;

    public PlayerItemAnimationEvent(@NotNull TachyonPlayer player, @NotNull ItemAnimationType armAnimationType) {
        super(player);
        this.armAnimationType = armAnimationType;
    }

    /**
     * Gets the animation.
     *
     * @return the animation
     */
    @NotNull
    public ItemAnimationType getArmAnimationType() {
        return armAnimationType;
    }

    public enum ItemAnimationType {
        BOW,
        EAT
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
