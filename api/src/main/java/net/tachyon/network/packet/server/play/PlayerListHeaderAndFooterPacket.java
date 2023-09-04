package net.tachyon.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record PlayerListHeaderAndFooterPacket(Component header, Component footer) implements ServerPacket {

    private static final String EMPTY_COMPONENT = "{\"translate\":\"\"}";

    @Override
    public void write(@NotNull BinaryWriter writer) {
        if (header == null) {
            writer.writeSizedString(EMPTY_COMPONENT);
        } else {
            writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(header));
        }

        if (footer == null) {
            writer.writeSizedString(EMPTY_COMPONENT);
        } else {
            writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(footer));
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLAYER_LIST_HEADER_AND_FOOTER;
    }
}
