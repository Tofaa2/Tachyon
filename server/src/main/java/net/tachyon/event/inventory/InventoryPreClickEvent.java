package net.tachyon.event.inventory;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.InventoryEvent;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.click.ClickType;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called before {@link InventoryClickEvent}, used to potentially cancel the click.
 */
public class InventoryPreClickEvent extends InventoryEvent implements CancellableEvent {

    private final TachyonPlayer player;
    private final int slot;
    private final ClickType clickType;
    private ItemStack clickedItem;
    private ItemStack cursorItem;

    private boolean cancelled;

    public InventoryPreClickEvent(@Nullable Inventory inventory,
                                  @NotNull TachyonPlayer player,
                                  int slot, @NotNull ClickType clickType,
                                  @NotNull ItemStack clicked, @NotNull ItemStack cursor) {
        super(inventory);
        this.player = player;
        this.slot = slot;
        this.clickType = clickType;
        this.clickedItem = clicked;
        this.cursorItem = cursor;
    }

    /**
     * Gets the player who is trying to click on the inventory.
     *
     * @return the player who clicked
     */
    @NotNull
    public TachyonPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the clicked slot number.
     *
     * @return the clicked slot number
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Gets the click type.
     *
     * @return the click type
     */
    @NotNull
    public ClickType getClickType() {
        return clickType;
    }

    /**
     * Gets the item who have been clicked.
     *
     * @return the clicked item
     */
    @NotNull
    public ItemStack getClickedItem() {
        return clickedItem;
    }

    /**
     * Changes the clicked item.
     *
     * @param clickedItem the clicked item
     */
    public void setClickedItem(@NotNull ItemStack clickedItem) {
        this.clickedItem = clickedItem;
    }

    /**
     * Gets the item who was in the player cursor.
     *
     * @return the cursor item
     */
    @NotNull
    public ItemStack getCursorItem() {
        return cursorItem;
    }

    /**
     * Changes the cursor item.
     *
     * @param cursorItem the cursor item
     */
    public void setCursorItem(@NotNull ItemStack cursorItem) {
        this.cursorItem = cursorItem;
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
