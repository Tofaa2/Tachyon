package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.particle.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public record ParticlePacket(int particleId, boolean longDistance, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, @Nullable Consumer<BinaryWriter> dataConsumer) implements ServerPacket {


    public ParticlePacket(@NotNull Particle particle, boolean longDistance, Point position, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount, @Nullable Consumer<BinaryWriter> dataConsumer) {
        this(particle.getId(), longDistance, position.x(), position.y(), position.z(), offsetX, offsetY, offsetZ, particleData, particleCount, dataConsumer);
    }

    public ParticlePacket(@NotNull Particle particle, boolean longDistance, Point position, float offsetX, float offsetY, float offsetZ, float particleData, int particleCount) {
        this(particle, longDistance, position, offsetX, offsetY, offsetZ, particleData, particleCount, null);
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(particleId);
        writer.writeBoolean(longDistance);
        writer.writeDouble(x);
        writer.writeDouble(y);
        writer.writeDouble(z);
        writer.writeFloat(offsetX);
        writer.writeFloat(offsetY);
        writer.writeFloat(offsetZ);
        writer.writeFloat(particleData);
        writer.writeInt(particleCount);

        if (dataConsumer != null) {
            dataConsumer.accept(writer);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PARTICLE;
    }
}
