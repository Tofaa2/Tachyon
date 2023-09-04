package net.tachyon.network.packet.client.handler;

import net.tachyon.network.packet.client.status.PingPacket;
import net.tachyon.network.packet.client.status.StatusRequestPacket;

public class ClientStatusPacketsHandler extends ClientPacketsHandler {

    public ClientStatusPacketsHandler() {
        register(0x00, StatusRequestPacket::new);
        register(0x01, PingPacket::new);
    }

}
