package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class SpawnObjectPacket implements ServerPacket {

    public int entityId;
    public byte type;
    public Position position;
    public int data;
    public short velocityX, velocityY, velocityZ;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(type);

        writer.writeInt((int) (position.getX() * 32.0));
        writer.writeInt((int) (position.getY() * 32.0));
        writer.writeInt((int) (position.getZ() * 32.0));

        writer.writeByte((byte) (position.getPitch() * 256 / 360));
        writer.writeByte((byte) (position.getYaw() * 256 / 360));

        writer.writeInt(data);

        if (data > 0) {
            writer.writeShort(velocityX);
            writer.writeShort(velocityY);
            writer.writeShort(velocityZ);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_OBJECT;
    }
}
