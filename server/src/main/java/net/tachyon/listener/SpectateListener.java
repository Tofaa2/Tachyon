package net.tachyon.listener;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.packet.client.play.ClientSpectatePacket;

import java.util.UUID;

public class SpectateListener {

    public static void listener(ClientSpectatePacket packet, TachyonPlayer player) {
        final UUID targetUuid = packet.targetUuid;
        final TachyonEntity target = TachyonEntity.getEntity(targetUuid);

        // Check if the target is valid
        if (target == null || target == player)
            return;

        // TODO check if 'target' is in a different instance
        player.spectate(target);
    }

}
