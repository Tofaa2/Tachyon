package net.tachyon.inventory;

import net.tachyon.entity.Player;
import net.tachyon.event.inventory.InventoryClickEvent;
import net.tachyon.inventory.click.ClickType;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an inventory which can receive click input.
 * All methods returning boolean returns true if the action is successful, false otherwise.
 * <p>
 * See <a href="https://wiki.vg/Protocol#Click_Window">...</a> for more information.
 */
public interface InventoryClickHandler {

    /**
     * Called when a {@link Player} left click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean leftClick(@NotNull Player player, int slot);

    /**
     * Called when a {@link Player} right click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean rightClick(@NotNull Player player, int slot);

    /**
     * Called when a {@link Player} shift click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean shiftClick(@NotNull Player player, int slot); // shift + left/right click have the same behavior

    /**
     * Called when a {@link Player} held click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @param key    the held slot (0-8) pressed
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean changeHeld(@NotNull Player player, int slot, int key);

    boolean middleClick(@NotNull Player player, int slot);

    /**
     * Called when a {@link Player} press the drop button
     *
     * @param player the player who clicked
     * @param mode Left click or drop
     * @param slot   the slot number
     * @param button -999 if clicking outside, normal if he is not
     * @return true if the drop hasn't been cancelled, false otherwise
     */
    boolean drop(@NotNull Player player, int mode, int slot, int button);

    boolean dragging(@NotNull Player player, int slot, int button);

    /**
     * Called when a {@link Player} double click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean doubleClick(@NotNull Player player, int slot);

    default void callClickEvent(@NotNull Player player, Inventory inventory, int slot,
                                @NotNull ClickType clickType, @NotNull ItemStack clicked, @NotNull ItemStack cursor) {
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(inventory, player, slot, clickType, clicked, cursor);
        player.callEvent(InventoryClickEvent.class, inventoryClickEvent);
    }

}
