package net.tachyon.event.inventory;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.InventoryEvent;
import net.tachyon.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player open an {@link Inventory}.
 * <p>
 * Executed by {@link TachyonPlayer#openInventory(Inventory)}.
 */
public class InventoryOpenEvent extends InventoryEvent implements CancellableEvent {

    private final TachyonPlayer player;

    private boolean cancelled;

    public InventoryOpenEvent(@Nullable Inventory inventory, @NotNull TachyonPlayer player) {
        super(inventory);
        this.player = player;
    }

    /**
     * Gets the player who opens the inventory.
     *
     * @return the player who opens the inventory
     */
    @NotNull
    public TachyonPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the inventory to open, this could have been change by the {@link #setInventory(Inventory)}.
     *
     * @return the inventory to open, null to just close the current inventory if any
     */
    @Nullable
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Changes the inventory to open.
     * <p>
     * To do not open any inventory see {@link #setCancelled(boolean)}.
     *
     * @param inventory the inventory to open
     */
    public void setInventory(@Nullable Inventory inventory) {
        this.inventory = inventory;
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
