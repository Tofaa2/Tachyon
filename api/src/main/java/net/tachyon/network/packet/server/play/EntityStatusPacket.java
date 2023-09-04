package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EntityStatusPacket(int entityId, byte status) implements ServerPacket {


    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(entityId);
        writer.writeByte(status);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_STATUS;
    }
}
