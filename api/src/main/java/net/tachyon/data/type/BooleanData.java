package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;

import org.jetbrains.annotations.NotNull;

public class BooleanData extends DataType<Boolean> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Boolean value) {
        writer.writeBoolean(value);
    }

    @NotNull
    @Override
    public Boolean decode(@NotNull BinaryReader reader) {
        return reader.readBoolean();
    }
}
