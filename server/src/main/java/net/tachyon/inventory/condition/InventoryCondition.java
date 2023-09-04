package net.tachyon.inventory.condition;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.InventoryModifier;
import net.tachyon.inventory.click.ClickType;

/**
 * Can be added to any {@link InventoryModifier}
 * using {@link Inventory#addInventoryCondition(InventoryCondition)}
 * or {@link PlayerInventory#addInventoryCondition(InventoryCondition)}
 * in order to listen to any issued clicks.
 */
@FunctionalInterface
public interface InventoryCondition {

    /**
     * Called when a {@link TachyonPlayer} clicks in the inventory where this {@link InventoryCondition} has been added to.
     *
     * @param player                   the player who clicked in the inventory
     * @param slot                     the slot clicked, can be -999 if the click is out of the inventory
     * @param clickType                the click type
     * @param inventoryConditionResult the result of this callback
     */
    void accept(TachyonPlayer player, int slot, ClickType clickType, InventoryConditionResult inventoryConditionResult);
}
