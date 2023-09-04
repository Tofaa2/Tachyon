package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.item.ItemStack;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public class WindowItemsPacket implements ServerPacket {

    public byte windowId;
    public ItemStack[] items;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte(windowId);

        if (items == null) {
            writer.writeShort((short) 0);
            return;
        }

        writer.writeShort((short) items.length);
        for (ItemStack item : items) {
            writer.writeItemStack(item);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.WINDOW_ITEMS;
    }
}
