package net.tachyon.inventory;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.inventory.InventoryClickEvent;
import net.tachyon.inventory.click.ClickType;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an inventory which can receive click input.
 * All methods returning boolean returns true if the action is successful, false otherwise.
 * <p>
 * See https://wiki.vg/Protocol#Click_Window for more information.
 */
public interface InventoryClickHandler {

    /**
     * Called when a {@link TachyonPlayer} left click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean leftClick(@NotNull TachyonPlayer player, int slot);

    /**
     * Called when a {@link TachyonPlayer} right click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean rightClick(@NotNull TachyonPlayer player, int slot);

    /**
     * Called when a {@link TachyonPlayer} shift click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean shiftClick(@NotNull TachyonPlayer player, int slot); // shift + left/right click have the same behavior

    /**
     * Called when a {@link TachyonPlayer} held click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @param key    the held slot (0-8) pressed
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean changeHeld(@NotNull TachyonPlayer player, int slot, int key);

    boolean middleClick(@NotNull TachyonPlayer player, int slot);

    /**
     * Called when a {@link TachyonPlayer} press the drop button
     *
     * @param player the player who clicked
     * @param mode
     * @param slot   the slot number
     * @param button -999 if clicking outside, normal if he is not
     * @return true if the drop hasn't been cancelled, false otherwise
     */
    boolean drop(@NotNull TachyonPlayer player, int mode, int slot, int button);

    boolean dragging(@NotNull TachyonPlayer player, int slot, int button);

    /**
     * Called when a {@link TachyonPlayer} double click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    boolean doubleClick(@NotNull TachyonPlayer player, int slot);

    default void callClickEvent(@NotNull TachyonPlayer player, Inventory inventory, int slot,
                                @NotNull ClickType clickType, @NotNull ItemStack clicked, @NotNull ItemStack cursor) {
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(inventory, player, slot, clickType, clicked, cursor);
        player.callEvent(InventoryClickEvent.class, inventoryClickEvent);
    }

}
