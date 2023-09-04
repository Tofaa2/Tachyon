package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class StatisticsPacket implements ServerPacket {

    public Statistic[] statistics;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(statistics.length);
        for (Statistic statistic : statistics) {
            statistic.write(writer);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.STATISTICS;
    }

    public static class Statistic {

        public String name;
        public int value;

        private void write(BinaryWriter writer) {
            writer.writeSizedString(name);
            writer.writeVarInt(value);
        }
    }

}
