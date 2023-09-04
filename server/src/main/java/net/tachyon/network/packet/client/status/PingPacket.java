package net.tachyon.network.packet.client.status;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.server.status.PongPacket;
import net.tachyon.network.packet.client.ClientPreplayPacket;
import net.tachyon.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

public class PingPacket implements ClientPreplayPacket {

    private long number;

    @Override
    public void process(@NotNull PlayerConnection connection) {
        PongPacket pongPacket = new PongPacket(number);
        connection.sendPacket(pongPacket);
        connection.disconnect();
    }

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.number = reader.readLong();
    }
}
