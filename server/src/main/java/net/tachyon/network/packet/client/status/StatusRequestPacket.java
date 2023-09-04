package net.tachyon.network.packet.client.status;

import net.tachyon.MinecraftServer;
import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.server.handshake.ResponsePacket;
import net.tachyon.network.packet.client.ClientPreplayPacket;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.ping.ResponseData;
import net.tachyon.ping.ResponseDataConsumer;
import org.jetbrains.annotations.NotNull;

public class StatusRequestPacket implements ClientPreplayPacket {

    @Override
    public void process(@NotNull PlayerConnection connection) {
        ResponseDataConsumer consumer = MinecraftServer.getResponseDataConsumer();
        ResponseData responseData = new ResponseData();

        // Fill default params
        responseData.setName(MinecraftServer.VERSION_NAME);
        responseData.setProtocol(MinecraftServer.PROTOCOL_VERSION);
        responseData.setMaxPlayer(0);
        responseData.setOnline(0);
        responseData.setDescription("A Tachyon Server");
        responseData.setFavicon("");

        if (consumer != null)
            consumer.accept(connection, responseData);
        connection.sendPacket(new ResponsePacket(responseData.build().toString()));
    }

    @Override
    public void read(@NotNull BinaryReader reader) {
        // Empty
    }
}
