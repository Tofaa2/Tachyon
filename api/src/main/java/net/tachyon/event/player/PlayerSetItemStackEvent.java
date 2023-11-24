package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called as a result of {@link PlayerInventory#setItemStack(int, ItemStack)}
 * and player click in his inventory.
 */
public class PlayerSetItemStackEvent extends PlayerEvent implements CancellableEvent {

    private int slot;
    private ItemStack itemStack;

    private boolean cancelled;

    public PlayerSetItemStackEvent(@NotNull Player player, int slot, @NotNull ItemStack itemStack) {
        super(player);
        this.slot = slot;
        this.itemStack = itemStack;
    }

    /**
     * Gets the slot where the item will be set.
     *
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Changes the slot where the item will be set.
     *
     * @param slot the new slot
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Gets the item stack which will be set.
     *
     * @return the item stack
     */
    @NotNull
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Changes the item stack which will be set.
     *
     * @param itemStack the new item stack
     */
    public void setItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
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
