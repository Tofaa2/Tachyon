package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.inventory.InventoryCloseEvent;
import net.tachyon.inventory.Inventory;
import net.tachyon.inventory.InventoryClickHandler;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.client.play.ClientClickWindowPacket;
import net.tachyon.network.packet.client.play.ClientCloseWindow;
import net.tachyon.network.packet.client.play.ClientWindowConfirmationPacket;
import net.tachyon.network.packet.server.play.SetSlotPacket;
import net.tachyon.network.packet.server.play.WindowConfirmationPacket;

public class WindowListener {

    public static void clickWindowListener(ClientClickWindowPacket packet, TachyonPlayer player) {
        final Inventory inventory;
        final byte windowId = packet.windowId;
        if (windowId == 0) {
            inventory = null;
        } else {
            inventory = player.getOpenInventory();
        }

        InventoryClickHandler clickHandler = inventory == null ?
                player.getInventory() : player.getOpenInventory();

        final short slot = packet.slot;
        final byte button = packet.button;
        final short actionNumber = packet.actionNumber;
        final int mode = packet.mode;

        // System.out.println("Window id: " + windowId + " | slot: " + slot + " | button: " + button + " | mode: " + mode);

        boolean successful = false;

        // prevent click in a non interactive slot (why does it exist?)
        if (slot == -1) {
            return;
        }

        switch (mode) {
            case 0 -> {
                switch (button) {
                    case 0 -> {
                        if (slot != -999) {
                            // Left click
                            successful = clickHandler.leftClick(player, slot);
                        } else {
                            // DROP
                            successful = clickHandler.drop(player, mode, slot, button);
                        }
                    }
                    case 1 -> {
                        if (slot != -999) {
                            // Right click
                            successful = clickHandler.rightClick(player, slot);
                        } else {
                            // DROP
                            successful = clickHandler.drop(player, mode, slot, button);
                        }
                    }
                }
            }
            case 1 -> successful = clickHandler.shiftClick(player, slot); // Shift + left/right have identical behavior
            case 2 -> successful = clickHandler.changeHeld(player, slot, button);
            case 3 -> {
            }
            // Middle click (only creative players in non-player inventories)
            case 4 ->
                // Dropping functions
                    successful = clickHandler.drop(player, mode, slot, button);
            case 5 ->
                // Dragging
                    successful = clickHandler.dragging(player, slot, button);
            case 6 -> successful = clickHandler.doubleClick(player, slot);
        }

        // Prevent the player from picking a ghost item in cursor
        refreshCursorItem(player, inventory);

        WindowConfirmationPacket windowConfirmationPacket = new WindowConfirmationPacket();
        windowConfirmationPacket.windowId = windowId;
        windowConfirmationPacket.actionNumber = actionNumber;
        windowConfirmationPacket.accepted = successful;

        player.getPlayerConnection().sendPacket(windowConfirmationPacket);
    }

    public static void closeWindowListener(ClientCloseWindow packet, TachyonPlayer player) {
        // if windowId == 0 then it is player's inventory, meaning that they hadn't been any open inventory packet
        InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent(player.getOpenInventory(), player);
        player.callEvent(InventoryCloseEvent.class, inventoryCloseEvent);

        player.closeInventory();

        Inventory newInventory = inventoryCloseEvent.getNewInventory();
        if (newInventory != null)
            player.openInventory(newInventory);
    }

    public static void windowConfirmationListener(ClientWindowConfirmationPacket packet, TachyonPlayer player) {
        // Empty
    }

    /**
     * @param player    the player to refresh the cursor item
     * @param inventory the player open inventory, null if not any (could be player inventory)
     */
    private static void refreshCursorItem(TachyonPlayer player, Inventory inventory) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack cursorItem;
        if (inventory != null) {
            cursorItem = inventory.getCursorItem(player);
        } else {
            cursorItem = playerInventory.getCursorItem();
        }

        final SetSlotPacket setSlotPacket = SetSlotPacket.createCursorPacket(cursorItem);

        player.getPlayerConnection().sendPacket(setSlotPacket);
    }

}
