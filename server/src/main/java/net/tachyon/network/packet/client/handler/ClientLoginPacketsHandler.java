package net.tachyon.network.packet.client.handler;

import net.tachyon.network.packet.client.login.EncryptionResponsePacket;
import net.tachyon.network.packet.client.login.LoginStartPacket;

public class ClientLoginPacketsHandler extends ClientPacketsHandler {

    public ClientLoginPacketsHandler() {
        register(0, LoginStartPacket::new);
        register(1, EncryptionResponsePacket::new);
    }

}
