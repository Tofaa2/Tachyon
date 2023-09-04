package net.tachyon.network.packet.server.login;

import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;

public interface EncryptionRequestPacket extends ServerPacket {

    @Override
    default int getId() {
        return ServerPacketIdentifier.LOGIN_ENCRYPTION_REQUEST;
    }
}
