package net.tachyon.data.type;

import net.tachyon.Tachyon;
import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.data.SerializableData;
import org.jetbrains.annotations.NotNull;

public class SerializableDataData extends DataType<SerializableData> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull SerializableData value) {
        writer.writeBytes(value.getIndexedSerializedData());
    }

    @NotNull
    @Override
    public SerializableData decode(@NotNull BinaryReader reader) {
        return Tachyon.getServer().getDataManager().createSerializableData(reader);
    }
}
