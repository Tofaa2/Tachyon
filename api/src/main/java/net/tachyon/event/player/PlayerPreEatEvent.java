package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called before the PlayerEatEvent and can be used to change the eating time
 * or to cancel its processing, cancelling the event means that the player will
 * continue the animation indefinitely.
 */
public class PlayerPreEatEvent extends PlayerEvent implements CancellableEvent {

    private final ItemStack foodItem;
    private long eatingTime;

    private boolean cancelled;

    public PlayerPreEatEvent(@NotNull Player player, @NotNull ItemStack foodItem, long eatingTime) {
        super(player);
        this.foodItem = foodItem;
        this.eatingTime = eatingTime;
    }

    /**
     * The food item which will be eaten.
     *
     * @return the food item
     */
    @NotNull
    public ItemStack getFoodItem() {
        return foodItem;
    }

    /**
     * Gets the food eating time.
     * <p>
     * This is by default {@link Player#getDefaultEatingTime()}.
     *
     * @return the eating time
     */
    public long getEatingTime() {
        return eatingTime;
    }

    /**
     * Changes the food eating time.
     *
     * @param eatingTime the new eating time
     */
    public void setEatingTime(long eatingTime) {
        this.eatingTime = eatingTime;
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
