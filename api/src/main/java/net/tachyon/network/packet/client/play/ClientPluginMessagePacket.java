package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientPluginMessagePacket extends ClientPlayPacket {

    private String channel;
    private byte[] data;

    public ClientPluginMessagePacket(String channel, byte[] data) {
        this.channel = channel;
        this.data = data;
    }

    public ClientPluginMessagePacket() {
        this(null, null);
    }

    @NotNull
    public String getChannel() {
        return channel;
    }

    @NotNull
    public byte[] getData() {
        return data;
    }

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.channel = reader.readSizedString(256);
        this.data = reader.getRemainingBytes();
    }
}
