package net.tachyon.data.type;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import net.tachyon.data.SerializableData;
import net.tachyon.data.SerializableDataImpl;
import net.tachyon.utils.binary.TachyonBinaryReader;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

// Pretty weird name huh?
public class SerializableDataData extends DataType<SerializableData> {

    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull SerializableData value) {
        writer.writeBytes(value.getIndexedSerializedData());
    }

    @NotNull
    @Override
    public SerializableData decode(@NotNull BinaryReader reader) {
        SerializableData serializableData = new SerializableDataImpl();
        serializableData.readIndexedSerializedData((TachyonBinaryReader)reader);
        return serializableData;
    }
}
