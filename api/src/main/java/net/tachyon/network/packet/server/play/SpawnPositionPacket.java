package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class SpawnPositionPacket implements ServerPacket {

    public int x, y, z;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writePoint(x, y, z);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_POSITION;
    }
}
