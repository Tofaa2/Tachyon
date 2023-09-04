package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class ResourcePackSendPacket implements ServerPacket {

    public String url;
    public String hash; // Size 40

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(url);
        writer.writeSizedString(hash);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.RESOURCE_PACK_SEND;
    }
}
