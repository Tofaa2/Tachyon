package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EntityRelativeMovePacket(int entityId, byte deltaX, byte deltaY, byte deltaZ, boolean onGround) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(deltaX);
        writer.writeByte(deltaY);
        writer.writeByte(deltaZ);
        writer.writeBoolean(onGround);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_RELATIVE_MOVE;
    }

    @NotNull
    public static EntityRelativeMovePacket getPacket(int entityId,
                                                     @NotNull Position newPosition, @NotNull Position oldPosition,
                                                     boolean onGround) {
        return new EntityRelativeMovePacket(
                entityId,
                getRelativeMove(newPosition.getX(), oldPosition.getX()),
                getRelativeMove(newPosition.getY(), oldPosition.getY()),
                getRelativeMove(newPosition.getZ(), oldPosition.getZ()),
                onGround
        );
    }

    public static byte getRelativeMove(double newCoord, double oldCoord) {
        int newFixedPoint = (int) (newCoord * 32.0);
        int oldFixedPoint = (int) (oldCoord * 32.0);

        return (byte) (newFixedPoint - oldFixedPoint);
    }
}
