package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.potion.Potion;
import org.jetbrains.annotations.NotNull;

public record EntityEffectPacket(int entityId, Potion potion) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte((byte) potion.getEffect().getId());
        writer.writeByte(potion.getAmplifier());
        writer.writeVarInt(potion.getDuration());
        // TODO(koesie10): Not be hacky
        writer.writeBoolean((potion.getFlags() & 0x02) != 0);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_EFFECT;
    }
}
