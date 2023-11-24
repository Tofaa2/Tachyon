package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientUpdateSignPacket extends ClientPlayPacket {

    public Point blockPosition;
    public String line1;
    public String line2;
    public String line3;
    public String line4;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.blockPosition = reader.readPoint();
        this.line1 = reader.readSizedString(384);
        this.line2 = reader.readSizedString(384);
        this.line3 = reader.readSizedString(384);
        this.line4 = reader.readSizedString(384);

    }
}
