package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.coordinate.BlockFace;
import net.tachyon.coordinate.Point;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerDiggingPacket extends ClientPlayPacket {

    public Status status;
    public Point blockPosition;
    public BlockFace blockFace;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.status = Status.values()[reader.readVarInt()];
        this.blockPosition = reader.readPoint();
        this.blockFace = BlockFace.values()[reader.readByte()];
    }

    public enum Status {
        STARTED_DIGGING,
        CANCELLED_DIGGING,
        FINISHED_DIGGING,
        DROP_ITEM_STACK,
        DROP_ITEM,
        UPDATE_ITEM_STATE
    }

}
