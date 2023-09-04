package net.tachyon.item;


import org.jetbrains.annotations.Nullable;

public enum Enchantment {
    PROTECTION("minecraft:protection", 0),

    FIRE_PROTECTION("minecraft:fire_protection", 1),

    FEATHER_FALLING("minecraft:feather_falling", 2),

    BLAST_PROTECTION("minecraft:blast_protection", 3),

    PROJECTILE_PROTECTION("minecraft:projectile_protection", 4),

    RESPIRATION("minecraft:respiration", 5),

    AQUA_AFFINITY("minecraft:aqua_affinity", 6),

    THORNS("minecraft:thorns", 7),

    DEPTH_STRIDER("minecraft:depth_strider", 8),

    SHARPNESS("minecraft:sharpness", 16),

    SMITE("minecraft:smite", 17),

    BANE_OF_ARTHROPODS("minecraft:bane_of_arthropods", 18),

    KNOCKBACK("minecraft:knockback", 19),

    FIRE_ASPECT("minecraft:fire_aspect", 20),

    LOOTING("minecraft:looting", 21),

    EFFICIENCY("minecraft:efficiency", 32),

    SILK_TOUCH("minecraft:silk_touch", 33),

    UNBREAKING("minecraft:unbreaking", 34),

    FORTUNE("minecraft:fortune", 35),

    POWER("minecraft:power", 48),

    PUNCH("minecraft:punch", 49),

    FLAME("minecraft:flame", 50),

    INFINITY("minecraft:infinity", 51),

    LUCK_OF_THE_SEA("minecraft:luck_of_the_sea", 61),

    LURE("minecraft:lure", 62);

    private final String namespaceID;

    private final int id;

    Enchantment(String namespaceID, int id) {
        this.namespaceID = namespaceID;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static @Nullable Enchantment fromId(int id) {
        for (Enchantment o : values()) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }
}
