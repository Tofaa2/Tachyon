package net.tachyon.network.packet.server.login;

import net.kyori.adventure.text.Component;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.chat.Adventure;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class LoginDisconnectPacket implements ServerPacket {

    private final String kickMessage; // JSON text

    public LoginDisconnectPacket(@NotNull String kickMessage) {
        this.kickMessage = kickMessage;
    }

    public LoginDisconnectPacket(@NotNull Component jsonKickMessage) {
        this(Adventure.COMPONENT_SERIALIZER.serialize(jsonKickMessage));
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(kickMessage);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_DISCONNECT;
    }

}
