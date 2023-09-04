package net.tachyon.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record ChatMessagePacket(@NotNull Component component, @NotNull Position position) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        String jsonMessage;
        if (position == Position.GAME_INFO) {
            // The action bar doesn't support colors in components, but does require it
            jsonMessage = Adventure.COMPONENT_SERIALIZER.serialize(Component.text(LegacyComponentSerializer.legacySection().serialize(component)));
        } else {
            jsonMessage = Adventure.COMPONENT_SERIALIZER.serialize(component);
        }

        writer.writeSizedString(jsonMessage);
        writer.writeByte((byte) position.ordinal());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CHAT_MESSAGE;
    }

    public enum Position {
        CHAT,
        SYSTEM_MESSAGE,
        GAME_INFO
    }
}
