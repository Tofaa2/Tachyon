package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class DoubleData extends DataType<Double> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Double value) {
        writer.writeDouble(value);
    }

    @NotNull
    @Override
    public Double decode(@NotNull BinaryReader reader) {
        return reader.readDouble();
    }
}
