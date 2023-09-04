package net.tachyon.network.packet.server.handshake;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record ResponsePacket(@NotNull String jsonResponse) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(jsonResponse);
    }

    @Override
    public int getId() {
        return 0x00;
    }

}
