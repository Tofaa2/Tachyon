package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.resourcepack.ResourcePackStatus;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientResourcePackStatusPacket extends ClientPlayPacket {

    public String hash;
    public ResourcePackStatus result;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.hash = reader.readSizedString(40);
        this.result = ResourcePackStatus.values()[reader.readVarInt()];
    }

}
