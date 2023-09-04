package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerLookPacket extends ClientPlayPacket {

    public float yaw, pitch;
    public boolean onGround;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.yaw = reader.readFloat();
        this.pitch = reader.readFloat();
        this.onGround = reader.readBoolean();
    }
}
