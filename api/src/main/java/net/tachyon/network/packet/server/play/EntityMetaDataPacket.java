package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.entity.Metadata;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class EntityMetaDataPacket implements ServerPacket {

    public int entityId;
    public Collection<Metadata.Entry<?>> entries;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);

        // Write all the fields
        for (Metadata.Entry<?> entry : entries) {
            entry.write(writer);
        }

        writer.writeByte((byte) 0x7F); // End
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_METADATA;
    }
}
