package net.tachyon.data;

import net.tachyon.binary.BinaryReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;

public interface DataManager {

    <T> void registerType(@NotNull Class<T> clazz, @NotNull DataType<T> type);

    @Nullable <T> DataType<T> getDataType(@NotNull Class<T> clazz);


    @NotNull SerializableData createSerializableData(@NotNull BinaryReader reader);


}
