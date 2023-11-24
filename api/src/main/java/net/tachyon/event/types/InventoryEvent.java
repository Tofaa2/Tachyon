package net.tachyon.event.types;

import net.tachyon.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class InventoryEvent extends Event {

    protected Inventory inventory;

    public InventoryEvent(@Nullable Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the inventory.
     *
     * @return the inventory, null if this is a player's inventory
     */
    @Nullable
    public Inventory getInventory() {
        return inventory;
    }
}