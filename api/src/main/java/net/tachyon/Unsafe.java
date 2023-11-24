package net.tachyon;

import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;

public interface Unsafe {


    void changeDidCloseInventory(Player player, boolean didCloseInventory);

    void syncEquipment(Player player, EntityEquipmentPacket.Slot slot);

}
