## Creating a plugin

Make a project and depend on the API. For now the API is not on any maven repository, so you will have to clone the API and install it to your local maven repository.


```groovy
dependencies {
    compileOnly 'net.tachyon.server:api:(current version)'
    
    // Only use this if you are using the second method of creating your plugin meta (see below)
    annotationProcessor 'net.tachyon.server:api:(current version)'
}
```



Create a class that extends `Plugin`, it will be used as your entry point. There are handles for onLoad, onEnable and onDisable.

```java
public class ExamplePlugin extends Plugin {
    @Override
    public void onLoad() {
        // Called when the plugin is loaded
    }

    @Override
    public void onEnable() {
        // Called when the plugin is enabled
    }

    @Override
    public void onDisable() {
        // Called when the plugin is disabled
    }
}
```

There are two ways of creating your plugin meta. The plugin meta is used to define and identify your plugin, so it can be loaded!

### Method 1
Create a `plugin.json` file in the resources of your project.

```json
{
  "name": "Example Plugin",
  "version": "1.0.0",
  "main": "net.tachyon.exampleplugin.ExamplePlugin",
  "description": "An example plugin",
  "authors": [
    "Your name"
  ]
}
```

### Method 2
In the main class of your plugin, add a `PluginData` annotation and define your plugin meta there.
For this method to work, you must add the `annotationProcessor` dependency to your project.
This will generate the `plugin.json` file for you on compile time.

```java
@PluginData(
        name = "ExamplePlugin",
        version = "1.0.0",
        description = "An example plugin",
        authors = {"Tachyon"}
)
public class ExamplePlugin extends Plugin {
    @Override
    public void onLoad() {
        // Called when the plugin is loaded
    }

    @Override
    public void onEnable() {
        // Called when the plugin is enabled
    }

    @Override
    public void onDisable() {
        // Called when the plugin is disabled
    }
}
```

A way of adding dynamic dependencies is still being worked on.