package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class ShortData extends DataType<Short> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Short value) {
        writer.writeShort(value);
    }

    @NotNull
    @Override
    public Short decode(@NotNull BinaryReader reader) {
        return reader.readShort();
    }
}
