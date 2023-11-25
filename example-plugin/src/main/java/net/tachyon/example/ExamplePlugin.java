package net.tachyon.example;

import net.tachyon.plugin.Plugin;
import net.tachyon.plugin.PluginData;

@PluginData(
        name = "ExamplePlugin",
        version = "1.0.0",
        description = "An example plugin",
        authors = {"Tachyon"}
)
public class ExamplePlugin extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("ExamplePlugin enabled!");
    }
}
