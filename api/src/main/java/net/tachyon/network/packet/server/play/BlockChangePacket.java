package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record BlockChangePacket(@NotNull Point blockPosition, short blockStateId) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writePoint(blockPosition);
        writer.writeVarInt(blockStateId);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.BLOCK_CHANGE;
    }
}
