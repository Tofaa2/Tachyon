package net.tachyon.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * Represents a configuration file wrapper that can be loaded and saved. Configuration files are stored in JSON format.
 * @param <T> The type of the configuration file. This is usually a POJO.
 */
public interface Config<T> {

    /**
     * Creates a new configuration file wrapper.
     * @param type The type of the configuration file. This is usually a POJO.
     * @param defaultPath The path to the default configuration file.
     * @return The new configuration file wrapper.
     * @param <B> The type of the configuration file. This is usually a POJO.
     */
    static <B> @NotNull Config<B> create(@NotNull Class<B> type, @NotNull Path defaultPath) {
        return new ConfigImpl<>(defaultPath, type);
    }

    /**
     * Creates and loads a new configuration file wrapper.
     * @param type The type of the configuration file. This is usually a POJO.
     * @param defaultPath The path to the default configuration file.
     * @param defaultValue The default value of the configuration file or null if none.
     * @return The new configuration file wrapper.
     * @param <B> The type of the configuration file. This is usually a POJO.
     */
    static <B> @NotNull Config<B> createAndLoad(@NotNull Class<B> type, @NotNull Path defaultPath, @Nullable B defaultValue) {
        Config<B> config = create(type, defaultPath);
        config.load(defaultValue);
        return config;
    }

    /**
     * Creates and loads a new configuration file wrapper.
     * @param type The type of the configuration file. This is usually a POJO.
     * @param defaultPath The path to the default configuration file.
     * @return The new configuration file wrapper.
     * @param <B> The type of the configuration file. This is usually a POJO.
     */
    static <B> @NotNull Config<B> createAndLoad(@NotNull Class<B> type, @NotNull Path defaultPath) {
        return createAndLoad(type, defaultPath, null);
    }


    /**
     * @return The configuration object.
     */
    @NotNull T get();

    /**
     * @return The default path to the configuration file in the file system.
     */
    @NotNull Path getDefaultPath();

    /**
     * @return The type of the configuration file. This is usually a POJO class
     */
    @NotNull Class<? extends T> getType();

    /**
     * Loads the configuration file from the given path, if the configuration is already loaded, will throw an exception.
     * @param path The path to the configuration file.
     * @param defaultValue The default value of the configuration file or null if none.
     */
    void load(@NotNull Path path, @Nullable T defaultValue);

    /**
     * Loads the configuration file from the given path, if the configuration is already loaded, will throw an exception.
     * @param defaultValue The default value of the configuration file or null if none.
     */
    default void load(@Nullable T defaultValue) {
        load(getDefaultPath(), defaultValue);
    }

    /**
     * Saves this configuration object to a given file.
     * @param path The path to the configuration file.
     */
    void save(@NotNull Path path);

    /**
     * Saves this configuration object to the default file.
     */
    default void save() {
        save(getDefaultPath());
    }

    /**
     * Attempts to reload the config object from a given file.
     * @param path The path to the configuration file.
     */
    void reload(@NotNull Path path);

    /**
     * Attempts to reload the config object from the default file.
     */
    default void reload() {
        reload(getDefaultPath());
    }

    /**
     * @return true if the config has already been loaded, false otherwise.
     */
    boolean isLoaded();


}
