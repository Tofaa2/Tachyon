package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class FloatArrayData extends DataType<float[]> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull float[] value) {
        writer.writeVarInt(value.length);
        for (float val : value) {
            writer.writeFloat(val);
        }
    }

    @NotNull
    @Override
    public float[] decode(@NotNull BinaryReader reader) {
        float[] array = new float[reader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = reader.readFloat();
        }
        return array;
    }
}
