package net.tachyon.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.packet.client.play.ClientKeepAlivePacket;

public class KeepAliveListener {

    public static void listener(ClientKeepAlivePacket packet, TachyonPlayer player) {
        final long packetId = packet.id;
        final long playerId = player.getLastKeepAlive();
        final boolean equals = packetId == playerId;
        if (!equals) {
            player.kick(Component.text("Bad Keep Alive packet", NamedTextColor.RED));
            return;
        }

        player.refreshAnswerKeepAlive(true);

        // Update latency
        final int latency = (int) (System.currentTimeMillis() - packet.id);
        player.refreshLatency(latency);
    }
}
