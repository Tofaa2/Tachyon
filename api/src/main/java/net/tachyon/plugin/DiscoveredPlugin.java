package net.tachyon.plugin;

import com.google.gson.JsonObject;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public final class DiscoveredPlugin {

    /** Static logger for this class. */
    public static final Logger LOGGER = LoggerFactory.getLogger(DiscoveredPlugin.class);

    /** The regex that this name must pass. If it doesn't, it will not be accepted. */
    public static final String NAME_REGEX = "[A-Za-z][_A-Za-z0-9]+";

    /** Name of the DiscoveredPlugin. Unique for all plugins. */
    private String name;

    /** Main class of this DiscoveredPlugin, must extend {@link  Plugin}. */
    private String entrypoint;

    /** Version of this plugin, highly reccomended to set it. */
    private String version;

    /** People who have made this plugin. */
    private String[] authors;

    /** List of plugin names that this depends on. */
    private String[] dependencies;

    /**
     * Extra meta on the object.
     * Do NOT use as configuration:
     *
     * Meta is meant to handle properties that will
     * be accessed by other plugins, not accessed by itself
     */
    private JsonObject meta;

    /** All files of this plugin */
    transient List<URL> files = new LinkedList<>();

    transient LoadStatus loadStatus = LoadStatus.LOAD_SUCCESS;

    /** The original jar this is from. */
    transient private File originalJar;

    transient private Path dataDirectory;

    /** The class loader that powers it. */
    transient private PluginClassLoader classLoader;

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getEntrypoint() {
        return entrypoint;
    }

    @NotNull
    public String getVersion() {
        return version;
    }

    @NotNull
    public String[] getAuthors() {
        return authors;
    }

    @NotNull
    public String[] getDependencies() {
        return dependencies;
    }

    public void setOriginalJar(@Nullable File file) {
        originalJar = file;
    }

    @Nullable
    public File getOriginalJar() {
        return originalJar;
    }

    public @NotNull Path getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(@NotNull Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    void createClassLoader() {
        Check.stateCondition(classLoader != null, "Plugin classloader has already been created");
        final URL[] urls = this.files.toArray(new URL[0]);
        classLoader = new PluginClassLoader(this.getName(), urls, this);
    }

    @NotNull
    public PluginClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Ensures that all properties of this plugin are properly set if they aren't
     *
     * @param plugin The plugin to verify
     */
    public static void verifyIntegrity(@NotNull DiscoveredPlugin plugin) {
        if (plugin.name == null) {
            StringBuilder fileList = new StringBuilder();
            for (URL f : plugin.files) {
                fileList.append(f.toExternalForm()).append(", ");
            }
            LOGGER.error("Plugin with no name. (at {}})", fileList);
            LOGGER.error("Plugin at ({}) will not be loaded.", fileList);
            plugin.loadStatus = LoadStatus.INVALID_NAME;

            // To ensure @NotNull: name = INVALID_NAME
            plugin.name = plugin.loadStatus.name();
            return;
        }

        if (!plugin.name.matches(NAME_REGEX)) {
            LOGGER.error("Plugin '{}' specified an invalid name.", plugin.name);
            LOGGER.error("Plugin '{}' will not be loaded.", plugin.name);
            plugin.loadStatus = LoadStatus.INVALID_NAME;

            // To ensure @NotNull: name = INVALID_NAME
            plugin.name = plugin.loadStatus.name();
            return;
        }

        if (plugin.entrypoint == null) {
            LOGGER.error("Plugin '{}' did not specify an entry point (via 'entrypoint').", plugin.name);
            LOGGER.error("Plugin '{}' will not be loaded.", plugin.name);
            plugin.loadStatus = LoadStatus.NO_ENTRYPOINT;

            // To ensure @NotNull: entrypoint = NO_ENTRYPOINT
            plugin.entrypoint = plugin.loadStatus.name();
            return;
        }

        // Handle defaults
        // If we reach this code, then the plugin will most likely be loaded:
        if (plugin.version == null) {
            LOGGER.warn("Plugin '{}' did not specify a version.", plugin.name);
            LOGGER.warn("Plugin '{}' will continue to load but should specify a plugin version.", plugin.name);
            plugin.version = "Unspecified";
        }

        if (plugin.authors == null) {
            plugin.authors = new String[0];
        }

        // No dependencies were specified
        if (plugin.dependencies == null) {
            plugin.dependencies = new String[0];
        }

        // No meta was provided
        if (plugin.meta == null) {
            plugin.meta = new JsonObject();
        }

    }

    @NotNull
    public JsonObject getMeta() {
        return meta;
    }

    /**
     * The status this plugin has, all are breakpoints.
     *
     * LOAD_SUCCESS is the only valid one.
     */
    enum LoadStatus {
        LOAD_SUCCESS("Actually, it did not fail. This message should not have been printed."),
        MISSING_DEPENDENCIES("Missing dependencies, check your logs."),
        INVALID_NAME("Invalid name."),
        NO_ENTRYPOINT("No entrypoint specified."),
        FAILED_TO_SETUP_CLASSLOADER("Plugin classloader could not be setup."),
        LOAD_FAILED("Load failed. See logs for more information."),
        ;

        private final String message;

        LoadStatus(@NotNull String message) {
            this.message = message;
        }

        @NotNull
        public String getMessage() {
            return message;
        }
    }

}
