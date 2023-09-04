package net.tachyon.network.packet.server.login;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LoginSuccessPacket implements ServerPacket {

    public UUID uuid;
    public String username;

    public LoginSuccessPacket(@NotNull UUID uuid, @NotNull String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(uuid.toString());
        writer.writeSizedString(username);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_SUCCESS;
    }
}
