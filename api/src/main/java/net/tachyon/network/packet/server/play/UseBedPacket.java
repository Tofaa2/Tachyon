package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record UseBedPacket(int entityId, Point location) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writePoint(location);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.USE_BED;
    }
}
