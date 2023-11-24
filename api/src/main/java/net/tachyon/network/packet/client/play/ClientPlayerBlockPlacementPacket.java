package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.coordinate.BlockFace;
import net.tachyon.coordinate.Point;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientPlayerBlockPlacementPacket extends ClientPlayPacket {

    public Point blockPosition;
    public BlockFace blockFace;
    public ItemStack item;
    public byte cursorPositionX, cursorPositionY, cursorPositionZ;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.blockPosition = reader.readPoint();

        byte blockFaceValue = reader.readByte();
        if (blockFaceValue == -1) {
            this.blockFace = null;
        } else {
            this.blockFace = BlockFace.values()[blockFaceValue];
        }

        this.item = reader.readSlot();
        this.cursorPositionX = reader.readByte();
        this.cursorPositionY = reader.readByte();
        this.cursorPositionZ = reader.readByte();
    }

}
