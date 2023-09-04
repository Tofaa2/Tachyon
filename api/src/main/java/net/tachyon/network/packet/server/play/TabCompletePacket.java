package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class TabCompletePacket implements ServerPacket {

    public String[] matches;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(matches.length);
        for (String match : matches) {
            writer.writeSizedString(match);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.TAB_COMPLETE;
    }
}
