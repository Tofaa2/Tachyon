package net.tachyon.data.type.array;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.DataType;
import org.jetbrains.annotations.NotNull;

public class ByteArrayData extends DataType<byte[]> {
    @Override
    public void encode(@NotNull BinaryWriter writer, @NotNull byte[] value) {
        encodeByteArray(writer, value);
    }

    @NotNull
    @Override
    public byte[] decode(@NotNull BinaryReader reader) {
        return decodeByteArray(reader);
    }

    public static void encodeByteArray(@NotNull BinaryWriter binaryWriter, @NotNull byte[] value) {
        binaryWriter.writeVarInt(value.length);
        for (byte val : value) {
            binaryWriter.writeByte(val);
        }
    }

    public static byte[] decodeByteArray(@NotNull BinaryReader binaryReader) {
        byte[] array = new byte[binaryReader.readVarInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = binaryReader.readByte();
        }
        return array;
    }
}
