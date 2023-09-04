package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UuidType extends DataType<UUID> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull UUID value) {
        writer.writeUuid(value);
    }

    @NotNull
    @Override
    public UUID decode(@NotNull BinaryReader reader) {
        return reader.readUuid();
    }
}
