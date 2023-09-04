package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

public class IntegerData extends DataType<Integer> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull Integer value) {
        writer.writeVarInt(value);
    }

    @NotNull
    @Override
    public Integer decode(@NotNull BinaryReader reader) {
        return reader.readVarInt();
    }
}