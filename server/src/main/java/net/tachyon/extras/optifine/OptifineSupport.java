package net.tachyon.extras.optifine;

import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.namespace.NamespaceID;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.biome.Biome;
import net.tachyon.biome.TachyonBiomeManager;
import net.tachyon.world.biome.BiomeManager;

/**
 * Hacky class for Optifine because of an issue making the client crash if biomes 'swamp' and 'swamp_hills'
 * are not registered.
 * <p>
 * Can be removed anytime, hope that it will be fixed.
 */
public final class OptifineSupport {

    private static volatile boolean enabled;

    /**
     * Enables optifine support by registering the required biomes.
     *
     * @throws IllegalStateException if optifine support is already enabled
     */
    public static void enable() {
        Check.stateCondition(enabled, "Optifine support is already enabled!");
        OptifineSupport.enabled = true;

        BiomeManager biomeManager = Tachyon.getServer().getBiomeManager();
        biomeManager.addBiome(Biome.builder().name(NamespaceID.from("minecraft:swamp")).build());
        biomeManager.addBiome(Biome.builder().name(NamespaceID.from("minecraft:swamp_hills")).build());
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
