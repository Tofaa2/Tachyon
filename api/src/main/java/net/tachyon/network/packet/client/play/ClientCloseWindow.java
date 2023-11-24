package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientCloseWindow extends ClientPlayPacket {

    public int windowId;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.windowId = reader.readVarInt();
    }
}
