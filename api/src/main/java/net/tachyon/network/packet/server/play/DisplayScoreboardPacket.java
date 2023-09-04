package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record DisplayScoreboardPacket(byte position, String scoreName) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte(position);
        writer.writeSizedString(scoreName);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.DISPLAY_SCOREBOARD;
    }
}
