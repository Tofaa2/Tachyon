package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Position;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EntityLookAndRelativeMove(int entityId, byte deltaX, byte deltaY, byte deltaZ, float yaw, float pitch, boolean onGround) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(deltaX);
        writer.writeByte(deltaY);
        writer.writeByte(deltaZ);
        writer.writeByte((byte) (yaw * 256 / 360));
        writer.writeByte((byte) (pitch * 256 / 360));
        writer.writeBoolean(onGround);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_LOOK_AND_RELATIVE_MOVE;
    }

    public static EntityLookAndRelativeMove getPacket(int entityId,
                                                      @NotNull Position newPosition, @NotNull Position oldPosition,
                                                      boolean onGround) {
        return new EntityLookAndRelativeMove(
                entityId,
                EntityRelativeMovePacket.getRelativeMove(newPosition.getX(), oldPosition.getX()),
                EntityRelativeMovePacket.getRelativeMove(newPosition.getY(), oldPosition.getY()),
                EntityRelativeMovePacket.getRelativeMove(newPosition.getZ(), oldPosition.getZ()),
                newPosition.getYaw(),
                newPosition.getPitch(),
                onGround
                );
    }
}
