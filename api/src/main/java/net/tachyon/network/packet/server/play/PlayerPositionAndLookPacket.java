package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record PlayerPositionAndLookPacket(Position position, byte flags) implements ServerPacket {


    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeDouble(position.getX());
        writer.writeDouble(position.getY());
        writer.writeDouble(position.getZ());

        writer.writeFloat(position.getYaw());
        writer.writeFloat(position.getPitch());

        writer.writeByte(flags);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLAYER_POSITION_AND_LOOK;
    }
}