package net.tachyon.data;

import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object which can be encoded and decoded back.
 * <p>
 * Used by {@link DataManager} for {@link SerializableDataImpl}
 * and by the storage API in {@link StorageLocation}.
 *
 * @param <T> the type of the object
 */
public abstract class DataType<T> {

    /**
     * Encodes the data type.
     * <p>
     * Be sure that the encoded value can be decoded back using {@link #decode(BinaryReader)}.
     *
     * @param writer the data writer
     * @param value  the value to encode
     */
    public abstract void encode(@NotNull BinaryWriter writer, @NotNull T value);

    /**
     * Decodes the data type.
     *
     * @param reader the data reader
     * @return the decoded value
     */
    @NotNull
    public abstract T decode(@NotNull BinaryReader reader);

}