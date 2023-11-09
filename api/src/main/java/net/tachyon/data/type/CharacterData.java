package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class CharacterData extends DataType<Character> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Character value) {
        writer.writeChar(value);
    }

    @NotNull
    @Override
    public Character decode(@NotNull BinaryReader reader) {
        return reader.readChar();
    }
}
