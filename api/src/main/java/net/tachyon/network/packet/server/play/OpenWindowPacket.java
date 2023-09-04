package net.tachyon.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record OpenWindowPacket(byte windowId, String windowType, Component title, byte numberOfSlots, int entityId) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte(windowId);
        writer.writeSizedString(windowType);
        writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(title));
        writer.writeByte(numberOfSlots);

        if (Objects.equals(windowType, "EntityHorse")) {
            writer.writeVarInt(entityId);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.OPEN_WINDOW;
    }
}
