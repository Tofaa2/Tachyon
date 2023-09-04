package net.tachyon.fluids;

import org.jetbrains.annotations.NotNull;

public enum Fluid {
    EMPTY("minecraft:empty"),

    FLOWING_WATER("minecraft:flowing_water"),

    WATER("minecraft:water"),

    FLOWING_LAVA("minecraft:flowing_lava"),

    LAVA("minecraft:lava");

    private final String namespaceID;

    Fluid(String namespaceID) {
        this.namespaceID = namespaceID;
    }

    public int getId() {
        return ordinal();
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static @NotNull Fluid fromId(int id) {
        if (id >= 0 && id < values().length) {
            return values()[id];
        }
        return EMPTY;
    }
}
