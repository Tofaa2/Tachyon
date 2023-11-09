package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class IntegerArrayData extends DataType<int[]> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull int[] value) {
        writer.writeVarInt(value.length);
        for (int val : value) {
            writer.writeInt(val);
        }
    }

    @NotNull
    @Override
    public int[] decode(@NotNull BinaryReader reader) {
        int[] array = new int[reader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = reader.readInteger();
        }
        return array;
    }
}
