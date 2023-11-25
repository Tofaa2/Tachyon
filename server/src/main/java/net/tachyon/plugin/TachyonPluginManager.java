package net.tachyon.plugin;

import com.google.gson.Gson;
import net.tachyon.Tachyon;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class TachyonPluginManager implements PluginManager {


    public final static Logger LOGGER = LoggerFactory.getLogger(PluginManager.class);

    public final static String INDEV_CLASSES_FOLDER = "tachyon.plugin.indevfolder.classes";
    public final static String INDEV_RESOURCES_FOLDER = "tachyon.plugin.indevfolder.resources";
    private final static Gson GSON = new Gson();
    // LinkedHashMaps are HashMaps that preserve order
    private final Map<String, Plugin> plugins = new LinkedHashMap<>();
    private final Map<String, Plugin> immutablePlugins = Collections.unmodifiableMap(plugins);

    private final File pluginFolder;
    private final File dependenciesFolder;
    private Path pluginsDataRoot;


    public TachyonPluginManager() {
        this.pluginFolder = new File(Tachyon.getServer().getDataDir(), "plugins");
        this.dependenciesFolder = new File(pluginFolder, ".libs");
        this.pluginsDataRoot = pluginFolder.toPath();
    }

    public void start() {
        loadPlugins();
        plugins.values().forEach(p -> {
            p.onLoad();
            p.getLogger().info("Loaded plugin {}", p.getOrigin().getName());
        });
        plugins.values().forEach(p -> {
            p.onEnable();
            p.getLogger().info("Enabled plugin {}", p.getOrigin().getName());
        });
    }

    @Override
    public boolean loadDynamicPlugin(@NotNull File jarFile) throws FileNotFoundException {
        if (!jarFile.exists()) {
            throw new FileNotFoundException("File '" + jarFile.getAbsolutePath() + "' does not exists. Cannot load plugin.");
        }
        LOGGER.info("Discover dynamic plugin from jar {}", jarFile.getAbsolutePath());
        DiscoveredPlugin discoveredPlugin = discoverFromJar(jarFile);
        List<DiscoveredPlugin> pluginsToLoad = Collections.singletonList(discoveredPlugin);
        return loadPluginList(pluginsToLoad);
    }

    @Override
    public <T extends Plugin> @Nullable T getPlugin(@NotNull Class<T> clazz) {
        Check.notNull(clazz, "Class cannot be null");
        return this.plugins.values().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }

    @Override
    public <T extends Plugin> @NotNull T getPluginOrThrow(@NotNull Class<T> clazz) throws IllegalArgumentException {
        T plugin = getPlugin(clazz);
        if (plugin == null) {
            throw new IllegalArgumentException("No plugin of type " + clazz.getName() + " is loaded.");
        }
        return plugin;
    }

    @NotNull
    public File getPluginFolder() {
        return pluginFolder;
    }

    public @NotNull Path getPluginDataRoot() {
        return pluginsDataRoot;
    }

    public void setPluginDataRoot(@NotNull Path dataRoot) {
        this.pluginsDataRoot = dataRoot;
    }

    @NotNull
    public Collection<Plugin> getPlugins() {
        return immutablePlugins.values();
    }

    @Nullable
    public Plugin getPlugin(@NotNull String name) {
        return plugins.get(name.toLowerCase());
    }

    public boolean hasPlugin(@NotNull String name) {
        return plugins.containsKey(name);
    }

    private void loadPlugins() {
        {
            if (!pluginFolder.exists()) {
                if (!pluginFolder.mkdirs()) {
                    LOGGER.error("Could not find or create the plugin folder, plugins will not be loaded!");
                    return;
                }
            }

            if (!dependenciesFolder.exists()) {
                if (!dependenciesFolder.mkdirs()) {
                    LOGGER.error("Could not find nor create the plugin dependencies folder, plugins will not be loaded!");
                    return;
                }
            }
        }
        {
            List<DiscoveredPlugin> discoveredPlugins = discoverPlugins();

            if (discoveredPlugins.isEmpty()) return;

            Iterator<DiscoveredPlugin> pluginIterator = discoveredPlugins.iterator();
            while (pluginIterator.hasNext()) {
                DiscoveredPlugin discoveredPlugin = pluginIterator.next();
                try {
                    discoveredPlugin.createClassLoader();
                } catch (Exception e) {
                    discoveredPlugin.loadStatus = DiscoveredPlugin.LoadStatus.FAILED_TO_SETUP_CLASSLOADER;
                    Tachyon.getServer().getExceptionManager().handleException(e);
                    LOGGER.error("Failed to load plugin {}", discoveredPlugin.getName());
                    LOGGER.error("Failed to load plugin", e);
                    pluginIterator.remove();
                }
            }

            discoveredPlugins = generateLoadOrder(discoveredPlugins);
            loadDependencies(discoveredPlugins);

            discoveredPlugins.removeIf(ext -> ext.loadStatus != DiscoveredPlugin.LoadStatus.LOAD_SUCCESS);

            for (DiscoveredPlugin discoveredPlugin : discoveredPlugins) {
                try {
                    loadPLugin(discoveredPlugin);
                } catch (Exception e) {
                    discoveredPlugin.loadStatus = DiscoveredPlugin.LoadStatus.LOAD_FAILED;
                    LOGGER.error("Failed to load plugin {}", discoveredPlugin.getName());
                    Tachyon.getServer().getExceptionManager().handleException(e);
                }
            }
        }
    }

    @Nullable
    private Plugin loadPLugin(@NotNull DiscoveredPlugin discoveredPlugin) {
        String pluginName = discoveredPlugin.getName();
        String mainClass = discoveredPlugin.getEntrypoint();

        PluginClassLoader loader = discoveredPlugin.getClassLoader();

        if (plugins.containsKey(pluginName.toLowerCase())) {
            LOGGER.error("A plugin called '{}' has already been registered.", pluginName);
            return null;
        }

        Class<?> jarClass;
        try {
            jarClass = Class.forName(mainClass, true, loader);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Could not find main class '{}' in plugin '{}'.",
                    mainClass, pluginName, e);
            return null;
        }

        Class<? extends Plugin> pluginClass;
        try {
            pluginClass = jarClass.asSubclass(Plugin.class);
        } catch (ClassCastException e) {
            LOGGER.error("Main class '{}' in '{}' does not extend the 'Plugin' superclass.", mainClass, pluginName, e);
            return null;
        }

        Constructor<? extends Plugin> constructor;
        try {
            constructor = pluginClass.getDeclaredConstructor();
            // Let's just make it accessible, plugin creators don't have to make this public.
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            LOGGER.error("Main class '{}' in '{}' does not define a no-args constructor.", mainClass, pluginName, e);
            return null;
        }
        Plugin plugin = null;
        try {
            plugin = constructor.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("Main class '{}' in '{}' cannot be an abstract class.", mainClass, pluginName, e);
            return null;
        } catch (IllegalAccessException ignored) {
            // We made it accessible, should not occur
        } catch (InvocationTargetException e) {
            LOGGER.error(
                    "While instantiating the main class '{}' in '{}' an exception was thrown.",
                    mainClass,
                    pluginName,
                    e.getTargetException()
            );
            return null;
        }

        for (String dependencyName : discoveredPlugin.getDependencies()) {
            Plugin dependency = plugins.get(dependencyName.toLowerCase());
            if (dependency == null) {
                LOGGER.warn("Dependency {} of {} is null? This means the plugin has been loaded without its dependency, which could cause issues later.", dependencyName, discoveredPlugin.getName());
            } else {
                dependency.getDepends().add(discoveredPlugin.getName());
            }
        }

        // add to a linked hash map, as they preserve order
        plugins.put(pluginName.toLowerCase(), plugin);

        return plugin;
    }

    private @NotNull List<DiscoveredPlugin> discoverPlugins() {
        List<DiscoveredPlugin> plugins = new LinkedList<>();

        File[] fileList = pluginFolder.listFiles();

        if (fileList != null) {
            for (File file : fileList) {

                if (file.isDirectory()) {
                    continue;
                }

                if (!file.getName().endsWith(".jar")) {
                    continue;
                }

                DiscoveredPlugin plugin = discoverFromJar(file);
                if (plugin != null && plugin.loadStatus == DiscoveredPlugin.LoadStatus.LOAD_SUCCESS) {
                    plugins.add(plugin);
                }
            }
        }

        if (System.getProperty(INDEV_CLASSES_FOLDER) != null && System.getProperty(INDEV_RESOURCES_FOLDER) != null) {
            LOGGER.info("Found indev folders for plugin. Adding to list of discovered plugin.");
            final String pluginClasses = System.getProperty(INDEV_CLASSES_FOLDER);
            final String pluginResources = System.getProperty(INDEV_RESOURCES_FOLDER);
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(pluginResources, "plugin.json")))) {
                DiscoveredPlugin plugin = GSON.fromJson(reader, DiscoveredPlugin.class);
                plugin.files.add(new File(pluginClasses).toURI().toURL());
                plugin.files.add(new File(pluginResources).toURI().toURL());
                plugin.setDataDirectory(getPluginDataRoot().resolve(plugin.getName()));
                DiscoveredPlugin.verifyIntegrity(plugin);

                if (plugin.loadStatus == DiscoveredPlugin.LoadStatus.LOAD_SUCCESS) {
                    plugins.add(plugin);
                }
            } catch (IOException e) {
                Tachyon.getServer().getExceptionManager().handleException(e);
            }
        }
        return plugins;
    }

    private @Nullable DiscoveredPlugin discoverFromJar(@NotNull File file) {
        try (ZipFile f = new ZipFile(file)) {

            ZipEntry entry = f.getEntry("plugin.json");

            if (entry == null)
                throw new IllegalStateException("Missing plugin.json in plugin " + file.getName() + ".");

            InputStreamReader reader = new InputStreamReader(f.getInputStream(entry));

            DiscoveredPlugin plugin = GSON.fromJson(reader, DiscoveredPlugin.class);
            plugin.setOriginalJar(file);
            plugin.files.add(file.toURI().toURL());
            plugin.setDataDirectory(getPluginDataRoot().resolve(plugin.getName()));

            DiscoveredPlugin.verifyIntegrity(plugin);

            return plugin;
        } catch (IOException e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
            return null;
        }
    }

    @NotNull
    private List<DiscoveredPlugin> generateLoadOrder(@NotNull List<DiscoveredPlugin> discoveredPlugins) {
        // Plugin --> Plugins it depends on.
        Map<DiscoveredPlugin, List<DiscoveredPlugin>> dependencyMap = new HashMap<>();

        {
            Map<String, DiscoveredPlugin> pluginsMap = new HashMap<>();

            for (DiscoveredPlugin discoveredPlugin : discoveredPlugins) {
                pluginsMap.put(discoveredPlugin.getName().toLowerCase(), discoveredPlugin);
            }

            allPlugins:
            for (DiscoveredPlugin discoveredPlugin : discoveredPlugins) {

                List<DiscoveredPlugin> dependencies = new ArrayList<>(discoveredPlugin.getDependencies().length);

                for (String dependencyName : discoveredPlugin.getDependencies()) {

                    DiscoveredPlugin dependencyPlugin = pluginsMap.get(dependencyName.toLowerCase());
                    if (dependencyPlugin == null) {

                        if (plugins.containsKey(dependencyName.toLowerCase())) {

                            dependencies.add(plugins.get(dependencyName.toLowerCase()).getOrigin());
                            continue;

                        } else {
                            LOGGER.error("Plugin {} requires an plugin called {}.", discoveredPlugin.getName(), dependencyName);
                            LOGGER.error("However the plugin {} could not be found.", dependencyName);
                            LOGGER.error("Therefore {} will not be loaded.", discoveredPlugin.getName());
                            discoveredPlugin.loadStatus = DiscoveredPlugin.LoadStatus.MISSING_DEPENDENCIES;
                            continue allPlugins;

                        }
                    }
                    // This will add null for an unknown-plugin
                    dependencies.add(dependencyPlugin);

                }

                dependencyMap.put(
                        discoveredPlugin,
                        dependencies
                );

            }
        }

        // List containing the load order.
        LinkedList<DiscoveredPlugin> sortedList = new LinkedList<>();

        // TODO actually have to read this
        {
            List<Map.Entry<DiscoveredPlugin, List<DiscoveredPlugin>>> loadablePlugins;

            while (!(
                    loadablePlugins = dependencyMap.entrySet().stream().filter(entry -> isLoaded(entry.getValue())).toList()
            ).isEmpty()
            ) {
                for (var entry : loadablePlugins) {
                    sortedList.add(entry.getKey());
                    dependencyMap.remove(entry.getKey());
                    for (var dependencies : dependencyMap.values()) {
                        dependencies.remove(entry.getKey());
                    }
                }
            }
        }

        if (!dependencyMap.isEmpty()) {
            LOGGER.error("Tachyon found {} cyclic plugins.", dependencyMap.size());
            LOGGER.error("Cyclic plugins depend on each other and can therefore not be loaded.");
            for (var entry : dependencyMap.entrySet()) {
                DiscoveredPlugin key = entry.getKey();
                LOGGER.error("{} could not be loaded, as it depends on: {}.",
                        key.getName(),
                        entry.getValue().stream().map(DiscoveredPlugin::getName).collect(Collectors.joining(", ")));
            }

        }

        return sortedList;
    }

    private boolean isLoaded(@NotNull List<DiscoveredPlugin> plugins) {
        return
                plugins.isEmpty() // Don't waste CPU on checking an empty array
                        // Make sure the internal plugins list contains all of these.
                        || plugins.stream().allMatch(ext -> this.plugins.containsKey(ext.getName().toLowerCase()));
    }

    private void loadDependencies(@NotNull List<DiscoveredPlugin> plugins) {
        List<DiscoveredPlugin> allLoadedPlugins = new LinkedList<>(plugins);

        for (Plugin plugin : immutablePlugins.values())
            allLoadedPlugins.add(plugin.getOrigin());

        for (DiscoveredPlugin discoveredPlugin : plugins) {
            try {
                PluginClassLoader classLoader = discoveredPlugin.getClassLoader();
                for (String dependencyName : discoveredPlugin.getDependencies()) {
                    var resolved = plugins.stream()
                            .filter(ext -> ext.getName().equalsIgnoreCase(dependencyName))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Unknown dependency '" + dependencyName + "' of '" + discoveredPlugin.getName() + "'"));

                    PluginClassLoader dependencyClassLoader = resolved.getClassLoader();

                    classLoader.addChild(dependencyClassLoader);
                    LOGGER.trace("Dependency of Plugin {}: {}", discoveredPlugin.getName(), resolved);
                }
            } catch (Exception e) {
                discoveredPlugin.loadStatus = DiscoveredPlugin.LoadStatus.MISSING_DEPENDENCIES;
                LOGGER.error("Failed to load dependencies for Plugin {}", discoveredPlugin.getName());
                LOGGER.error("Plugin '{}' will not be loaded", discoveredPlugin.getName());
                LOGGER.error("This is the exception", e);
            }
        }
    }

    private boolean loadPluginList(@NotNull List<DiscoveredPlugin> pluginsToLoad) {
        LOGGER.debug("Reorder plugins to ensure proper load order");
        pluginsToLoad = generateLoadOrder(pluginsToLoad);
        loadDependencies(pluginsToLoad);

        for (DiscoveredPlugin toReload : pluginsToLoad) {
            LOGGER.debug("Setting up classloader for plugins {}", toReload.getName());
        }

        List<Plugin> newPlugins = new LinkedList<>();
        for (DiscoveredPlugin toReload : pluginsToLoad) {
            LOGGER.info("Actually load plugin {}", toReload.getName());
            Plugin loadedPlugin = loadPLugin(toReload);
            if (loadedPlugin != null) {
                newPlugins.add(loadedPlugin);
            }
        }

        if (newPlugins.isEmpty()) {
            LOGGER.error("No plugins to load, skipping callbacks");
            return false;
        }

        LOGGER.info("Load complete, firing preinit, init and then postinit callbacks");
        newPlugins.forEach(Plugin::onLoad);
        newPlugins.forEach(Plugin::onEnable);
        return true;
    }

    public void shutdown() {
        Set<String> pluginNames = new HashSet<>(plugins.keySet());
        for (String ext : pluginNames) {
            if (plugins.containsKey(ext)) {
                unloadPlugin(ext);
            }
        }
    }

    private void unloadPlugin(@NotNull String pluginName) {
        Plugin ext = plugins.get(pluginName.toLowerCase());

        if (ext == null) {
            throw new IllegalArgumentException("Plugin " + pluginName + " is not currently loaded.");
        }

        List<String> dependents = new LinkedList<>(ext.getDepends()); // copy dependents list

        for (String dependentID : dependents) {
            Plugin dependentExt = plugins.get(dependentID.toLowerCase());
            if (dependentExt != null) {
                LOGGER.info("Unloading dependent plugin {} (because it depends on {})", dependentID, pluginName);
                unload(dependentExt);
            }
        }

        LOGGER.info("Unloading plugin {}", pluginName);
        unload(ext);
    }

    private void unload(@NotNull Plugin ext) {
        ext.onDisable();

        String id = ext.getOrigin().getName().toLowerCase();
        plugins.remove(id);

        // TODO: Is it necessary to remove the CLs since this is only called on shutdown?
    }

}
