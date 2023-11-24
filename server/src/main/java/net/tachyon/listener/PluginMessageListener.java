package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerPluginMessageEvent;
import net.tachyon.network.packet.client.play.ClientPluginMessagePacket;

import java.nio.charset.StandardCharsets;

public class PluginMessageListener {

    public static void listener(ClientPluginMessagePacket packet, TachyonPlayer player) {


        byte[] data = packet.getData();
        String channel = packet.getChannel();
        if (data != null) {
            if (channel.equalsIgnoreCase("MC|Brand") || channel.equalsIgnoreCase("minecraft:brand")) {
                if (data.length <= 0 || data.length > 64) {
                    player.kick("Invalid brand message received");
                    return;
                }
                String brand = new String(data, StandardCharsets.UTF_8);
                brand = brand.replaceAll(" (Velocity)", ""); // Velocity adds its own watermark when forwarded to.
                player.setClientBrand(brand);
            }
        }

        PlayerPluginMessageEvent pluginMessageEvent = new PlayerPluginMessageEvent(player, channel, data);
        player.callEvent(PlayerPluginMessageEvent.class, pluginMessageEvent);
    }

}
