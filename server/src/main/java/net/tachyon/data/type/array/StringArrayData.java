package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class StringArrayData extends DataType<String[]> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull String[] value) {
        writer.writeStringArray(value);
    }

    @NotNull
    @Override
    public String[] decode(@NotNull BinaryReader reader) {
        return reader.readSizedStringArray(Integer.MAX_VALUE);
    }
}
