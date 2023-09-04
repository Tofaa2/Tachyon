package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerResourcePackStatusEvent;
import net.tachyon.network.packet.client.play.ClientResourcePackStatusPacket;
import net.tachyon.resourcepack.ResourcePackStatus;

public class ResourcePackListener {

    public static void listener(ClientResourcePackStatusPacket packet, TachyonPlayer player) {
        final ResourcePackStatus result = packet.result;
        PlayerResourcePackStatusEvent resourcePackStatusEvent = new PlayerResourcePackStatusEvent(player, result);
        player.callEvent(PlayerResourcePackStatusEvent.class, resourcePackStatusEvent);
    }
}
