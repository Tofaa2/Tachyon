package net.tachyon;

import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;

public final class UnsafeServer implements Unsafe {
    @Override
    public void changeDidCloseInventory(Player player, boolean didCloseInventory) {
        ((TachyonPlayer) player).UNSAFE_changeDidCloseInventory(didCloseInventory);
    }

    @Override
    public void syncEquipment(Player player, EntityEquipmentPacket.Slot slot) {
        ((TachyonPlayer) player).syncEquipment(slot);
    }
}
