package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class ShortArrayData extends DataType<short[]> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull short[] value) {
        writer.writeVarInt(value.length);
        for (short val : value) {
            writer.writeShort(val);
        }
    }

    @NotNull
    @Override
    public short[] decode(@NotNull BinaryReader reader) {
        short[] array = new short[reader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = reader.readShort();
        }
        return array;
    }
}
