package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EntityHeadLookPacket(int entityId, float yaw) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte((byte) (this.yaw * 256 / 360));
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_HEAD_LOOK;
    }
}
