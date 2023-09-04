package net.tachyon.potion;

public enum PotionEffect {
    SPEED("minecraft:Speed", 1),

    SLOWNESS("minecraft:Slowness", 2),

    HASTE("minecraft:Haste", 3),

    MINING_FATIGUE("minecraft:MiningFatigue", 4),

    STRENGTH("minecraft:Strength", 5),

    INSTANT_HEALTH("minecraft:InstantHealth", 6),

    INSTANT_DAMAGE("minecraft:InstantDamage", 7),

    JUMP_BOOST("minecraft:JumpBoost", 8),

    NAUSEA("minecraft:Nausea", 9),

    REGENERATION("minecraft:Regeneration", 10),

    RESISTANCE("minecraft:Resistance", 11),

    FIRE_RESISTANCE("minecraft:FireResistance", 12),

    WATER_BREATHING("minecraft:WaterBreathing", 13),

    INVISIBILITY("minecraft:Invisibility", 14),

    BLINDNESS("minecraft:Blindness", 15),

    NIGHT_VISION("minecraft:NightVision", 16),

    HUNGER("minecraft:Hunger", 17),

    WEAKNESS("minecraft:Weakness", 18),

    POISON("minecraft:Poison", 19),

    WITHER("minecraft:Wither", 20),

    HEALTH_BOOST("minecraft:HealthBoost", 21),

    ABSORPTION("minecraft:Absorption", 22),

    SATURATION("minecraft:Saturation", 23);

    private final String namespaceID;

    private final int id;

    PotionEffect(String namespaceID, int id) {
        this.namespaceID = namespaceID;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static PotionEffect fromId(int id) {
        for (PotionEffect o : values()) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }
}
