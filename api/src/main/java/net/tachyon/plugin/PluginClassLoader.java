package net.tachyon.plugin;

import net.tachyon.Tachyon;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public final class PluginClassLoader extends URLClassLoader {
    private final List<PluginClassLoader> children = new ArrayList<>();
    private final DiscoveredPlugin discoveredPlugin;
    private Logger logger;

    public PluginClassLoader(String name, URL[] urls, DiscoveredPlugin discoveredPlugin) {
        super("Plugin_" + name, urls, Tachyon.class.getClassLoader());
        this.discoveredPlugin = discoveredPlugin;
    }

    public PluginClassLoader(String name, URL[] urls, ClassLoader parent, DiscoveredPlugin discoveredPlugin) {
        super("Plugin_" + name, urls, parent);
        this.discoveredPlugin = discoveredPlugin;
    }

    @Override
    public void addURL(@NotNull URL url) {
        super.addURL(url);
    }

    public void addChild(@NotNull PluginClassLoader loader) {
        children.add(loader);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException e) {
            for (PluginClassLoader child : children) {
                try {
                    return child.loadClass(name, resolve);
                } catch (ClassNotFoundException ignored) {}
            }
            throw e;
        }
    }

    public InputStream getResourceAsStreamWithChildren(@NotNull String name) {
        InputStream in = getResourceAsStream(name);
        if (in != null) return in;

        for (PluginClassLoader child : children) {
            InputStream childInput = child.getResourceAsStreamWithChildren(name);
            if (childInput != null)
                return childInput;
        }

        return null;
    }

    public DiscoveredPlugin getDiscoveredPlugin() {
        return discoveredPlugin;
    }

    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(discoveredPlugin.getName());
        }
        return logger;
    }

}
