package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientWindowConfirmationPacket extends ClientPlayPacket {

    public byte windowId;
    public short actionNumber;
    public boolean accepted;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.windowId = reader.readByte();
        this.actionNumber = reader.readShort();
        this.accepted = reader.readBoolean();
    }
}
