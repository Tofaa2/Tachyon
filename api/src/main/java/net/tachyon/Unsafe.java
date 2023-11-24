package net.tachyon;

import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;

public interface Unsafe {

    /**
     * Pretends the player did close the inventory they had open.
     * @param player
     * @param didCloseInventory
     */
    void changeDidCloseInventory(Player player, boolean didCloseInventory);

    /**
     * Syncs the player's equipment with the client.
     * @param player
     * @param slot
     */
    void syncEquipment(Player player, EntityEquipmentPacket.Slot slot);

}
