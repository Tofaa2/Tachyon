package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackArrayData extends DataType<ItemStack[]> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull ItemStack[] value) {
        writer.writeVarInt(value.length);
        for (ItemStack itemStack : value) {
            writer.writeItemStack(itemStack);
        }
    }

    @NotNull
    @Override
    public ItemStack[] decode(@NotNull BinaryReader reader) {
        ItemStack[] items = new ItemStack[reader.readVarInt()];
        for (int i = 0; i < items.length; i++) {
            items[i] = reader.readSlot();
        }
        return items;
    }
}
