package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientChatMessagePacket extends ClientPlayPacket {

    public String message;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.message = reader.readSizedString(256);
    }
}
