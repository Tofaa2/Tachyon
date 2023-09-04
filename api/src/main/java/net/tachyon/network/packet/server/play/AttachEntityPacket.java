package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record AttachEntityPacket(int attachedEntityId, int vehicleEntityId, boolean leash) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(attachedEntityId);
        writer.writeInt(vehicleEntityId);
        writer.writeBoolean(leash);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ATTACH_ENTITY;
    }
}
