package net.tachyon.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class TitlePacket implements ServerPacket {

    public Action action;

    public Component titleText; // Only text

    public Component subtitleText; // Only text

    public int fadeIn;
    public int stay;
    public int fadeOut;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(action.ordinal());

        switch (action) {
            case SET_TITLE -> writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(titleText));
            case SET_SUBTITLE -> writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(subtitleText));
            case SET_TIMES_AND_DISPLAY -> {
                writer.writeInt(fadeIn);
                writer.writeInt(stay);
                writer.writeInt(fadeOut);
            }
            case HIDE, RESET -> {
            }
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.TITLE;
    }

    public enum Action {
        SET_TITLE,
        SET_SUBTITLE,
        SET_TIMES_AND_DISPLAY,
        HIDE,
        RESET
    }

}
