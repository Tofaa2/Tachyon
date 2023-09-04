package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class WindowConfirmationPacket implements ServerPacket {

    public byte windowId;
    public short actionNumber;
    public boolean accepted;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte(windowId);
        writer.writeShort(actionNumber);
        writer.writeBoolean(accepted);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.WINDOW_CONFIRMATION;
    }
}
