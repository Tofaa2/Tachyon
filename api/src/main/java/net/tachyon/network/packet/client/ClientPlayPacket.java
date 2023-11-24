package net.tachyon.network.packet.client;

import net.tachyon.Tachyon;
import net.tachyon.entity.Player;
import net.tachyon.network.listener.IPacketListenerManager;
import org.jetbrains.annotations.NotNull;

public abstract class ClientPlayPacket implements ClientPacket {

    private static final IPacketListenerManager PACKET_LISTENER_MANAGER = Tachyon.getServer().getPacketListenerManager();

    /**
     * Processes the packet for {@code player}.
     * <p>
     * Called during the player tick and forwarded to the {@link IPacketListenerManager}.
     *
     * @param player the player who sent the packet
     */
    public void process(@NotNull Player player) {
        Tachyon.getServer().getPacketListenerManager().processClientPacket(this, player);
    }

}
