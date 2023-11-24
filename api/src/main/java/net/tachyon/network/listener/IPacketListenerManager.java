package net.tachyon.network.listener;


import net.tachyon.entity.Player;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.network.packet.server.ServerPacket;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface IPacketListenerManager {

    <T extends ClientPlayPacket> void processClientPacket(@NotNull T packet, @NotNull Player player);

    boolean processServerPacket(@NotNull ServerPacket packet, @NotNull Collection<Player> players);

}
