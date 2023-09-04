package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerChangeHeldSlotEvent;
import net.tachyon.network.packet.client.play.ClientHeldItemChangePacket;
import net.tachyon.utils.MathUtils;

public class PlayerHeldListener {

    public static void heldListener(ClientHeldItemChangePacket packet, TachyonPlayer player) {
        if (!MathUtils.isBetween(packet.slot, 0, 8)) {
            // Incorrect packet, ignore
            return;
        }

        final byte slot = (byte) packet.slot;

        PlayerChangeHeldSlotEvent changeHeldSlotEvent = new PlayerChangeHeldSlotEvent(player, slot);
        player.callEvent(PlayerChangeHeldSlotEvent.class, changeHeldSlotEvent);

        if (!changeHeldSlotEvent.isCancelled()) {
            // Event hasn't been canceled, process it

            final byte resultSlot = changeHeldSlotEvent.getSlot();

            // If the held slot has been changed by the event, send the change to the player
            if (resultSlot != slot) {
                player.setHeldItemSlot(resultSlot);
            } else {
                // Otherwise, simply refresh the player field
                player.refreshHeldSlot(resultSlot);
            }
        } else {
            // Event has been canceled, send the last held slot to refresh the client
            player.setHeldItemSlot(player.getHeldSlot());
        }
    }

}
