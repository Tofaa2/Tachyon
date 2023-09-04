package net.tachyon.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record DisconnectPacket(@NotNull String message) implements ServerPacket {

    public DisconnectPacket(@NotNull Component message){
        this(Adventure.COMPONENT_SERIALIZER.serialize(message));
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(message);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.DISCONNECT;
    }
}
