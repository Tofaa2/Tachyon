package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerPositionAndLookPacket extends ClientPlayPacket {

    public double x, y, z;
    public float yaw, pitch;
    public boolean onGround;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.x = reader.readDouble();
        this.y = reader.readDouble();
        this.z = reader.readDouble();

        this.yaw = reader.readFloat();
        this.pitch = reader.readFloat();

        this.onGround = reader.readBoolean();
    }
}
