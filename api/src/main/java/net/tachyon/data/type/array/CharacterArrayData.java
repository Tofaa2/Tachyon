package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class CharacterArrayData extends DataType<char[]> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull char[] value) {
        writer.writeVarInt(value.length);
        for (char val : value) {
            writer.writeChar(val);
        }
    }

    @NotNull
    @Override
    public char[] decode(@NotNull BinaryReader reader) {
        char[] array = new char[reader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = reader.readChar();
        }
        return array;
    }
}
