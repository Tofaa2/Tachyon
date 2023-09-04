package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientSteerVehiclePacket extends ClientPlayPacket {

    public float sideways;
    public float forward;
    public byte flags;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.sideways = reader.readFloat();
        this.forward = reader.readFloat();
        this.flags = reader.readByte();
    }
}
