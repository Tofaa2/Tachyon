package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerAbilitiesPacket extends ClientPlayPacket {

    public byte flags;
    public float flyingSpeed;
    public float walkingSpeed;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.flags = reader.readByte();
        this.flyingSpeed = reader.readFloat();
        this.walkingSpeed = reader.readFloat();
    }
}
