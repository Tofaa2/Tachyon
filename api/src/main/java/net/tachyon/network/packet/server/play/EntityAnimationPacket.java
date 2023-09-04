package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record EntityAnimationPacket(int entityId, Animation animation) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte((byte) animation.ordinal());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_ANIMATION;
    }

    public enum Animation {
        SWING_ARM,
        TAKE_DAMAGE,
        LEAVE_BED,
        EAT_FOOD,
        CRITICAL_EFFECT,
        MAGICAL_CRITICAL_EFFECT
    }
}
