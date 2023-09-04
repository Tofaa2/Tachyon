package net.tachyon.event.inventory;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.InventoryEvent;
import net.tachyon.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when an {@link Inventory} is closed by a player.
 */
public class InventoryCloseEvent extends InventoryEvent {

    private final TachyonPlayer player;
    private Inventory newInventory;

    public InventoryCloseEvent(@Nullable Inventory inventory, @NotNull TachyonPlayer player) {
        super(inventory);
        this.player = player;
    }

    /**
     * Gets the player who closed the inventory.
     *
     * @return the player who closed the inventory
     */
    @NotNull
    public TachyonPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the new inventory to open.
     *
     * @return the new inventory to open, null if there isn't any
     */
    @Nullable
    public Inventory getNewInventory() {
        return newInventory;
    }

    /**
     * Can be used to open a new inventory after closing the previous one.
     *
     * @param newInventory the inventory to open, null to do not open any
     */
    public void setNewInventory(@Nullable Inventory newInventory) {
        this.newInventory = newInventory;
    }
}
