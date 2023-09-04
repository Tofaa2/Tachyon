package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.world.Difficulty;
import org.jetbrains.annotations.NotNull;

public class ServerDifficultyPacket implements ServerPacket {

    public Difficulty difficulty;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte(difficulty.getId());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SERVER_DIFFICULTY;
    }
}
