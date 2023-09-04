package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EffectPacket(int effectId, Point position, int data, boolean disableRelativeVolume) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(effectId);
        writer.writePoint(position);
        writer.writeInt(data);
        writer.writeBoolean(disableRelativeVolume);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.EFFECT;
    }
}
