package net.tachyon.network.packet.client;

import net.tachyon.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

public interface ClientPreplayPacket extends ClientPacket {

    /**
     * Called when the packet is received.
     *
     * @param connection the connection who sent the packet
     */
    void process(@NotNull PlayerConnection connection);
}
