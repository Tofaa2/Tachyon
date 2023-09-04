package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.entity.Metadata;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public class SpawnPlayerPacket implements ServerPacket {

    public int entityId;
    public UUID playerUuid;
    public Position position;
    public short heldItem;
    public Collection<Metadata.Entry<?>> metadataEntries;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeUuid(playerUuid);
        // Fixed point numbers
        writer.writeInt((int)(position.getX() * 32));
        writer.writeInt((int)(position.getY() * 32));
        writer.writeInt((int)(position.getZ() * 32));
        writer.writeByte((byte) (position.getYaw() * 256f / 360f));
        writer.writeByte((byte) (position.getPitch() * 256f / 360f));
        writer.writeShort(heldItem);

        // Write all the fields
        for (Metadata.Entry<?> entry : metadataEntries) {
            entry.write(writer);
        }

        writer.writeByte((byte) 0x7F); // End
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_PLAYER;
    }
}
