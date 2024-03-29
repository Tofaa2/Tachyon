package net.tachyon.inventory.types;

import net.kyori.adventure.text.Component;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.InventoryProperty;
import net.tachyon.inventory.InventoryType;

public class AnvilInventory extends Inventory {

    private short repairCost;

    public AnvilInventory(Component title) {
        super(InventoryType.ANVIL, title);
    }

    /**
     * Gets the anvil repair cost.
     *
     * @return the repair cost
     */
    public short getRepairCost() {
        return repairCost;
    }

    /**
     * Sets the anvil repair cost.
     *
     * @param cost the new anvil repair cost
     */
    public void setRepairCost(short cost) {
        this.repairCost = cost;
        sendProperty(InventoryProperty.ANVIL_REPAIR_COST, cost);
    }
}
