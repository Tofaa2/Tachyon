package net.tachyon.network.packet.server.login;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class SetCompressionPacket implements ServerPacket {

    public int threshold;

    public SetCompressionPacket(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(threshold);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_SET_COMPRESSION;
    }
}
