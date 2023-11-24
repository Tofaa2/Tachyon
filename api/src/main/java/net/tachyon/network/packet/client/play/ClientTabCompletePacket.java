package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientTabCompletePacket extends ClientPlayPacket {

    public String text;
    public boolean hasPosition;
    public Point lookedAtBlock;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.text = reader.readSizedString(Short.MAX_VALUE);
        this.hasPosition = reader.readBoolean();

        if (this.hasPosition) {
            this.lookedAtBlock = reader.readPoint();
        }
    }
}
