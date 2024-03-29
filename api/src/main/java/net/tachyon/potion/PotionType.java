package net.tachyon.potion;

public enum PotionType {
    EMPTY("minecraft:empty"),

    WATER("minecraft:water"),

    MUNDANE("minecraft:mundane"),

    THICK("minecraft:thick"),

    AWKWARD("minecraft:awkward"),

    NIGHT_VISION("minecraft:night_vision"),

    LONG_NIGHT_VISION("minecraft:long_night_vision"),

    INVISIBILITY("minecraft:invisibility"),

    LONG_INVISIBILITY("minecraft:long_invisibility"),

    LEAPING("minecraft:leaping"),

    LONG_LEAPING("minecraft:long_leaping"),

    STRONG_LEAPING("minecraft:strong_leaping"),

    FIRE_RESISTANCE("minecraft:fire_resistance"),

    LONG_FIRE_RESISTANCE("minecraft:long_fire_resistance"),

    SWIFTNESS("minecraft:swiftness"),

    LONG_SWIFTNESS("minecraft:long_swiftness"),

    STRONG_SWIFTNESS("minecraft:strong_swiftness"),

    SLOWNESS("minecraft:slowness"),

    LONG_SLOWNESS("minecraft:long_slowness"),

    STRONG_SLOWNESS("minecraft:strong_slowness"),

    TURTLE_MASTER("minecraft:turtle_master"),

    LONG_TURTLE_MASTER("minecraft:long_turtle_master"),

    STRONG_TURTLE_MASTER("minecraft:strong_turtle_master"),

    WATER_BREATHING("minecraft:water_breathing"),

    LONG_WATER_BREATHING("minecraft:long_water_breathing"),

    HEALING("minecraft:healing"),

    STRONG_HEALING("minecraft:strong_healing"),

    HARMING("minecraft:harming"),

    STRONG_HARMING("minecraft:strong_harming"),

    POISON("minecraft:poison"),

    LONG_POISON("minecraft:long_poison"),

    STRONG_POISON("minecraft:strong_poison"),

    REGENERATION("minecraft:regeneration"),

    LONG_REGENERATION("minecraft:long_regeneration"),

    STRONG_REGENERATION("minecraft:strong_regeneration"),

    STRENGTH("minecraft:strength"),

    LONG_STRENGTH("minecraft:long_strength"),

    STRONG_STRENGTH("minecraft:strong_strength"),

    WEAKNESS("minecraft:weakness"),

    LONG_WEAKNESS("minecraft:long_weakness"),

    LUCK("minecraft:luck"),

    SLOW_FALLING("minecraft:slow_falling"),

    LONG_SLOW_FALLING("minecraft:long_slow_falling");

    private String namespaceID;

    PotionType(String namespaceID) {
        this.namespaceID = namespaceID;
    }

    public int getId() {
        return ordinal();
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static PotionType fromId(int id) {
        if (id >= 0 && id < values().length) {
            return values()[id];
        }
        return EMPTY;
    }
}
