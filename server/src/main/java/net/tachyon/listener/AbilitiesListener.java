package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerStartFlyingEvent;
import net.tachyon.event.player.PlayerStopFlyingEvent;
import net.tachyon.network.packet.client.play.ClientPlayerAbilitiesPacket;

public class AbilitiesListener {

    public static void listener(ClientPlayerAbilitiesPacket packet, TachyonPlayer player) {
        final boolean canFly = player.isAllowFlying() || player.isCreative();

        if (canFly) {
            final boolean isFlying = (packet.flags & 0x2) > 0;

            player.refreshFlying(isFlying);

            if (isFlying) {
                PlayerStartFlyingEvent startFlyingEvent = new PlayerStartFlyingEvent(player);
                player.callEvent(PlayerStartFlyingEvent.class, startFlyingEvent);
            } else {
                PlayerStopFlyingEvent stopFlyingEvent = new PlayerStopFlyingEvent(player);
                player.callEvent(PlayerStopFlyingEvent.class, stopFlyingEvent);
            }
        }
    }
}
