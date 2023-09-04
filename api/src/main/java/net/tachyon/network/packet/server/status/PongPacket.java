package net.tachyon.network.packet.server.status;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PongPacket(long number) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeLong(number);
    }

    @Override
    public int getId() {
        return 0x01;
    }

}
