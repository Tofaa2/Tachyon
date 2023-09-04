package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record CameraPacket(int cameraId) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(cameraId);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CAMERA;
    }
}
