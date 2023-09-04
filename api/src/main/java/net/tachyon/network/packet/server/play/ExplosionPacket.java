package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record ExplosionPacket(float x, float y, float z, float radius, byte[] records, float playerMotionX, float playerMotionY, float playerMotionZ) implements ServerPacket {


    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeFloat(x);
        writer.writeFloat(y);
        writer.writeFloat(z);
        writer.writeFloat(radius);
        writer.writeInt(records.length/3); // each record is 3 bytes long
        for (byte record : records)
            writer.writeByte(record);
        writer.writeFloat(playerMotionX);
        writer.writeFloat(playerMotionY);
        writer.writeFloat(playerMotionZ);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.EXPLOSION;
    }
}
