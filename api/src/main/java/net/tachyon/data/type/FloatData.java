package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class FloatData extends DataType<Float> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Float value) {
        writer.writeFloat(value);
    }

    @NotNull
    @Override
    public Float decode(@NotNull BinaryReader reader) {
        return reader.readFloat();
    }
}
