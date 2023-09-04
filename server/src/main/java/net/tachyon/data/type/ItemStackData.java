package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.item.ItemStack;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class ItemStackData extends DataType<ItemStack> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull ItemStack value) {
        writer.writeItemStack(value);
    }

    @NotNull
    @Override
    public ItemStack decode(@NotNull BinaryReader reader) {
        return reader.readSlot();
    }
}
