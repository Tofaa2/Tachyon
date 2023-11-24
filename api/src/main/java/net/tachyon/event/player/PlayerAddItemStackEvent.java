package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called as a result of {@link PlayerInventory#addItemStack(ItemStack)}.
 */
public class PlayerAddItemStackEvent extends PlayerEvent implements CancellableEvent {

    private ItemStack itemStack;

    private boolean cancelled;

    public PlayerAddItemStackEvent(@NotNull Player player, @NotNull ItemStack itemStack) {
        super(player);
        this.itemStack = itemStack;
    }

    /**
     * Gets the item stack which will be added.
     *
     * @return the item stack
     */
    @NotNull
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Changes the item stack which will be added.
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
