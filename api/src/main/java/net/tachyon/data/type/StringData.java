package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class StringData extends DataType<String> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull String value) {
        writer.writeSizedString(value);
    }

    @NotNull
    @Override
    public String decode(@NotNull BinaryReader reader) {
        return reader.readSizedString(Integer.MAX_VALUE);
    }
}
