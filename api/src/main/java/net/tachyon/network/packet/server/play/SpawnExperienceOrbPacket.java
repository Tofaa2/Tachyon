package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class SpawnExperienceOrbPacket implements ServerPacket {

    public int entityId;
    public Position position;
    public short expCount;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeInt((int) (position.getX() * 32.0));
        writer.writeInt((int) (position.getY() * 32.0));
        writer.writeInt((int) (position.getZ() * 32.0));
        writer.writeShort(expCount);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_EXPERIENCE_ORB;
    }
}
