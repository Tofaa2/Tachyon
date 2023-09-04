package net.tachyon.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataManager {

    <T> void registerType(@NotNull Class<T> clazz, @NotNull DataType<T> type);

    @Nullable <T> DataType<T> getDataType(@NotNull Class<T> clazz);

}
