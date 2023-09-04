package net.tachyon.particle;

public enum Particle {
    EXPLODE("minecraft:explode", 0),

    LARGEEXPLODE("minecraft:largeexplode", 1),

    HUGEEXPLOSION("minecraft:hugeexplosion", 2),

    FIREWORKSSPARK("minecraft:fireworksSpark", 3),

    BUBBLE("minecraft:bubble", 4),

    SPLASH("minecraft:splash", 5),

    WAKE("minecraft:wake", 6),

    SUSPENDED("minecraft:suspended", 7),

    DEPTHSUSPEND("minecraft:depthsuspend", 8),

    CRIT("minecraft:crit", 9),

    MAGICCRIT("minecraft:magicCrit", 10),

    SMOKE("minecraft:smoke", 11),

    LARGESMOKE("minecraft:largesmoke", 12),

    SPELL("minecraft:spell", 13),

    INSTANTSPELL("minecraft:instantSpell", 14),

    MOBSPELL("minecraft:mobSpell", 15),

    MOBSPELLAMBIENT("minecraft:mobSpellAmbient", 16),

    WITCHMAGIC("minecraft:witchMagic", 17),

    DRIPWATER("minecraft:dripWater", 18),

    DRIPLAVA("minecraft:dripLava", 19),

    ANGRYVILLAGER("minecraft:angryVillager", 20),

    HAPPYVILLAGER("minecraft:happyVillager", 21),

    TOWNAURA("minecraft:townaura", 22),

    NOTE("minecraft:note", 23),

    PORTAL("minecraft:portal", 24),

    ENCHANTMENTTABLE("minecraft:enchantmenttable", 25),

    FLAME("minecraft:flame", 26),

    LAVA("minecraft:lava", 27),

    FOOTSTEP("minecraft:footstep", 28),

    CLOUD("minecraft:cloud", 29),

    REDDUST("minecraft:reddust", 30),

    SNOWBALLPOOF("minecraft:snowballpoof", 31),

    SNOWSHOVEL("minecraft:snowshovel", 32),

    SLIME("minecraft:slime", 33),

    HEART("minecraft:heart", 34),

    BARRIER("minecraft:barrier", 35),

    ICONCRACK_("minecraft:iconcrack_", 36),

    BLOCKCRACK_("minecraft:blockcrack_", 37),

    BLOCKDUST_("minecraft:blockdust_", 38),

    DROPLET("minecraft:droplet", 39),

    TAKE("minecraft:take", 40),

    MOBAPPEARANCE("minecraft:mobappearance", 41);

    private final String namespaceID;

    private final int id;

    Particle(String namespaceID, int id) {
        this.namespaceID = namespaceID;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static Particle fromId(int id) {
        for (Particle o : values()) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }
}
