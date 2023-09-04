package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class UpdateHealthPacket implements ServerPacket {

    public float health;
    public int food;
    public float foodSaturation;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeFloat(health);
        writer.writeVarInt(food);
        writer.writeFloat(foodSaturation);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.UPDATE_HEALTH;
    }
}
