package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerSettingsChangeEvent;
import net.tachyon.network.packet.client.play.ClientSettingsPacket;

public class SettingsListener {

    public static void listener(ClientSettingsPacket packet, TachyonPlayer player) {
        TachyonPlayer.PlayerSettings settings = player.getSettings();
        settings.refresh(packet.locale, packet.viewDistance, packet.chatMode, packet.chatColors, packet.displayedSkinParts);

        PlayerSettingsChangeEvent playerSettingsChangeEvent = new PlayerSettingsChangeEvent(player);
        player.callEvent(PlayerSettingsChangeEvent.class, playerSettingsChangeEvent);
    }

}
