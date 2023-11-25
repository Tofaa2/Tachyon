package net.tachyon.plugin;

import org.jetbrains.annotations.NotNull;

public @interface PluginData {

    @NotNull String name() default "";

    @NotNull String version() default "1.0.0";

    @NotNull String description() default "No description provided.";

    @NotNull String[] authors() default {};

    @NotNull String[] dependencies() default {};

}
