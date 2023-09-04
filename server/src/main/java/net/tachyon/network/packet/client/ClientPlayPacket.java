package net.tachyon.network.packet.client;

import net.tachyon.MinecraftServer;
import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.listener.manager.PacketListenerManager;
import org.jetbrains.annotations.NotNull;

public abstract class ClientPlayPacket implements ClientPacket {

    private static final PacketListenerManager PACKET_LISTENER_MANAGER = MinecraftServer.getPacketListenerManager();

    /**
     * Processes the packet for {@code player}.
     * <p>
     * Called during the player tick and forwarded to the {@link PacketListenerManager}.
     *
     * @param player the player who sent the packet
     */
    public void process(@NotNull TachyonPlayer player) {
        PACKET_LISTENER_MANAGER.processClientPacket(this, player);
    }

}
