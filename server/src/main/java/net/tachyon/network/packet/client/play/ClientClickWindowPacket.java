package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientClickWindowPacket extends ClientPlayPacket {

    public byte windowId;
    public short slot;
    public byte button;
    public short actionNumber;
    public int mode;
    public ItemStack item;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.windowId = reader.readByte();
        this.slot = reader.readShort();
        this.button = reader.readByte();
        this.actionNumber = reader.readShort();
        this.mode = reader.readVarInt();
        this.item = reader.readSlot();
    }
}
