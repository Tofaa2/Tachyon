package net.tachyon.network.packet.client.play;

import net.tachyon.binary.BinaryReader;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.client.ClientPlayPacket;
import org.jetbrains.annotations.NotNull;

public class ClientCreativeInventoryActionPacket extends ClientPlayPacket {

    public short slot;
    public ItemStack item;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.slot = reader.readShort();
        this.item = reader.readSlot();
    }
}
