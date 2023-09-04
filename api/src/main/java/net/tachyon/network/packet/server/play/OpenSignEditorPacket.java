package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class OpenSignEditorPacket implements ServerPacket {

    // WARNING: There must be a sign in this location (you can send a BlockChangePacket beforehand)
    public Point signPosition;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writePoint(signPosition);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.OPEN_SIGN_EDITOR;
    }
}
