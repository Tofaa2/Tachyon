package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class ByteData extends DataType<Byte> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Byte value) {
        writer.writeByte(value);
    }

    @NotNull
    @Override
    public Byte decode(@NotNull BinaryReader reader) {
        return reader.readByte();
    }
}
