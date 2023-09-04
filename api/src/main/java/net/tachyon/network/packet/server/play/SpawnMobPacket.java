package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.entity.Metadata;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class SpawnMobPacket implements ServerPacket {

    public int entityId;
    public byte entityType;
    public Position position;
    public float headPitch;
    public short velocityX, velocityY, velocityZ;
    public Collection<Metadata.Entry<?>> metadataEntries;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(entityType);

        writer.writeInt((int) (position.getX() * 32.0));
        writer.writeInt((int) (position.getY() * 32.0));
        writer.writeInt((int) (position.getZ() * 32.0));

        writer.writeByte((byte) (position.getYaw() * 256 / 360));
        writer.writeByte((byte) (position.getPitch() * 256 / 360));
        writer.writeByte((byte) (headPitch * 256 / 360));

        writer.writeShort(velocityX);
        writer.writeShort(velocityY);
        writer.writeShort(velocityZ);

        // Write all the fields
        for (Metadata.Entry<?> entry : metadataEntries) {
            entry.write(writer);
        }

        writer.writeByte((byte) 0x7F); // End
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_MOB;
    }
}
