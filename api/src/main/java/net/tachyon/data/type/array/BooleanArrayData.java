package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class BooleanArrayData extends DataType<boolean[]> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull boolean[] value) {
        writer.writeVarInt(value.length);
        for (boolean val : value) {
            writer.writeBoolean(val);
        }
    }

    @NotNull
    @Override
    public boolean[] decode(@NotNull BinaryReader reader) {
        boolean[] array = new boolean[reader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = reader.readBoolean();
        }
        return array;
    }
}
