package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerPluginMessageEvent;
import net.tachyon.network.packet.client.play.ClientPluginMessagePacket;

public class PluginMessageListener {

    public static void listener(ClientPluginMessagePacket packet, TachyonPlayer player) {
        PlayerPluginMessageEvent pluginMessageEvent = new PlayerPluginMessageEvent(player, packet.channel, packet.data);
        player.callEvent(PlayerPluginMessageEvent.class, pluginMessageEvent);
    }

}
