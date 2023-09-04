package net.tachyon.listener;

import net.tachyon.entity.GameMode;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.client.play.ClientCreativeInventoryActionPacket;
import net.tachyon.utils.inventory.PlayerInventoryUtils;

public class CreativeInventoryActionListener {

    public static void listener(ClientCreativeInventoryActionPacket packet, TachyonPlayer player) {
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        short slot = packet.slot;
        final ItemStack item = packet.item;

        if (slot != -1) {
            // Set item
            slot = (short) PlayerInventoryUtils.convertPlayerInventorySlot(slot, PlayerInventoryUtils.OFFSET);
            PlayerInventory inventory = player.getInventory();
            inventory.setItemStack(slot, item);
        } else {
            // Drop item
            player.dropItem(item);
        }

    }

}
