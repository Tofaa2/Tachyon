package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class DoubleArrayData extends DataType<double[]> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull double[] value) {
        writer.writeVarInt(value.length);
        for (double val : value) {
            writer.writeDouble(val);
        }
    }

    @NotNull
    @Override
    public double[] decode(@NotNull BinaryReader reader) {
        double[] array = new double[reader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = reader.readDouble();
        }
        return array;
    }
}
