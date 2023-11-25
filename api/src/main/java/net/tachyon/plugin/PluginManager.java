package net.tachyon.plugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

public interface PluginManager {

    boolean loadDynamicPlugin(@NotNull File file) throws FileNotFoundException;

    @Nullable Plugin getPlugin(@NotNull String name);

    boolean hasPlugin(@NotNull String name);

    @NotNull Collection<Plugin> getPlugins();

    <T extends Plugin> @Nullable T getPlugin(@NotNull Class<T> clazz);

    <T extends Plugin> @NotNull T getPluginOrThrow(@NotNull Class<T> clazz) throws IllegalArgumentException;

}
