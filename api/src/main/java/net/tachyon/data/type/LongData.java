package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class LongData extends DataType<Long> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Long value) {
        writer.writeLong(value);
    }

    @NotNull
    @Override
    public Long decode(@NotNull BinaryReader reader) {
        return reader.readLong();
    }
}
