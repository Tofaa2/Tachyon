package net.tachyon.listener.manager;

import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.packet.server.ServerPacket;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Interface used to add a listener for outgoing packets with {@link ConnectionManager#onPacketSend(ServerPacketConsumer)}.
 */
@FunctionalInterface
public interface ServerPacketConsumer {

    /**
     * Called when a packet is sent to a client.
     *
     * @param players          the players who will receive the packet
     * @param packetController the packet controller, can be used for cancelling
     * @param packet           the packet to send
     */
    void accept(@NotNull Collection<Player> players, @NotNull PacketController packetController, @NotNull ServerPacket packet);

}
