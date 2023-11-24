package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientKeepAlivePacket extends ClientPlayPacket {

    public int id;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.id = reader.readVarInt();
    }
}
