package net.tachyon.network.packet.client;

import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.network.ConnectionManager;
import org.jetbrains.annotations.NotNull;

public interface ClientPreplayPacket extends ClientPacket {

    ConnectionManager CONNECTION_MANAGER = (ConnectionManager) Tachyon.getServer().getConnectionManager();

    /**
     * Called when the packet is received.
     *
     * @param connection the connection who sent the packet
     */
    void process(@NotNull PlayerConnection connection);
}
