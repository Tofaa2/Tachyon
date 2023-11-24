package net.tachyon.network.packet.client.status;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.network.packet.client.ClientPreplayPacket;
import org.jetbrains.annotations.NotNull;

public class LegacyServerListPingPacket implements ClientPreplayPacket {

    private byte payload;

    @Override
    public void process(@NotNull PlayerConnection connection) {

    }

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.payload = reader.readByte();
    }

    public byte getPayload() {
        return payload;
    }
}
