package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientPluginMessagePacket extends ClientPlayPacket {

    public String channel;
    public byte[] data;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.channel = reader.readSizedString(256);
        this.data = reader.getRemainingBytes();
    }
}
