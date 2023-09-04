package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class TimeUpdatePacket implements ServerPacket {

    public long worldAge;
    public long timeOfDay;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeLong(worldAge);
        writer.writeLong(timeOfDay);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.TIME_UPDATE;
    }
}
