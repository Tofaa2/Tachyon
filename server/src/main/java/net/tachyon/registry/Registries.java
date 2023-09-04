package net.tachyon.registry;

import java.util.HashMap;
import net.tachyon.entity.EntityType;
import net.tachyon.fluids.Fluid;
import net.tachyon.block.Block;
import net.tachyon.item.Enchantment;
import net.tachyon.item.Material;
import net.tachyon.particle.Particle;
import net.tachyon.potion.PotionEffect;
import net.tachyon.potion.PotionType;
import net.tachyon.sound.SoundEvent;
import net.tachyon.stat.StatisticType;
import net.tachyon.namespace.NamespaceID;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Registries {
    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, Block> blocks = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, Material> materials = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, Enchantment> enchantments = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, EntityType> entityTypes = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, Particle> particles = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, PotionType> potionTypes = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, PotionEffect> potionEffects = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, SoundEvent> sounds = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, StatisticType> statisticTypes = new HashMap<>();

    /**
     * Should only be used for internal code, please use the get* methods.
     */
    @ApiStatus.Internal
    public static final HashMap<NamespaceID, Fluid> fluids = new HashMap<>();

    /**
     * Returns the corresponding Block matching the given id. Returns 'AIR' if none match.
     */
    @NotNull
    public static Block getBlock(String id) {
        return getBlock(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding Block matching the given id. Returns 'AIR' if none match.
     */
    @NotNull
    public static Block getBlock(NamespaceID id) {
        return blocks.getOrDefault(id, Block.AIR);
    }

    /**
     * Returns the corresponding Material matching the given id. Returns 'AIR' if none match.
     */
    @NotNull
    public static Material getMaterial(String id) {
        return getMaterial(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding Material matching the given id. Returns 'AIR' if none match.
     */
    @NotNull
    public static Material getMaterial(NamespaceID id) {
        return materials.getOrDefault(id, Material.AIR);
    }

    /**
     * Returns the corresponding Enchantment matching the given id. Returns null if none match.
     */
    @Nullable
    public static Enchantment getEnchantment(String id) {
        return getEnchantment(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding Enchantment matching the given id. Returns null if none match.
     */
    @Nullable
    public static Enchantment getEnchantment(NamespaceID id) {
        return enchantments.get(id);
    }

    /**
     * Returns the corresponding EntityType matching the given id. Returns null if none match.
     */
    @Nullable
    public static EntityType getEntityType(String id) {
        return getEntityType(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding EntityType matching the given id. Returns null if none match.
     */
    @Nullable
    public static EntityType getEntityType(NamespaceID id) {
        return entityTypes.get(id);
    }

    /**
     * Returns the corresponding Particle matching the given id. Returns null if none match.
     */
    @Nullable
    public static Particle getParticle(String id) {
        return getParticle(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding Particle matching the given id. Returns null if none match.
     */
    @Nullable
    public static Particle getParticle(NamespaceID id) {
        return particles.get(id);
    }

    /**
     * Returns the corresponding PotionType matching the given id. Returns null if none match.
     */
    @Nullable
    public static PotionType getPotionType(String id) {
        return getPotionType(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding PotionType matching the given id. Returns null if none match.
     */
    @Nullable
    public static PotionType getPotionType(NamespaceID id) {
        return potionTypes.get(id);
    }

    /**
     * Returns the corresponding PotionEffect matching the given id. Returns null if none match.
     */
    @Nullable
    public static PotionEffect getPotionEffect(String id) {
        return getPotionEffect(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding PotionEffect matching the given id. Returns null if none match.
     */
    @Nullable
    public static PotionEffect getPotionEffect(NamespaceID id) {
        return potionEffects.get(id);
    }

    /**
     * Returns the corresponding SoundEvent matching the given id. Returns null if none match.
     */
    @Nullable
    public static SoundEvent getSound(String id) {
        return getSound(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding SoundEvent matching the given id. Returns null if none match.
     */
    @Nullable
    public static SoundEvent getSound(NamespaceID id) {
        return sounds.get(id);
    }

    /**
     * Returns the corresponding StatisticType matching the given id. Returns null if none match.
     */
    @Nullable
    public static StatisticType getStatisticType(String id) {
        return getStatisticType(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding StatisticType matching the given id. Returns null if none match.
     */
    @Nullable
    public static StatisticType getStatisticType(NamespaceID id) {
        return statisticTypes.get(id);
    }

    /**
     * Returns the corresponding Fluid matching the given id. Returns 'EMPTY' if none match.
     */
    @NotNull
    public static Fluid getFluid(String id) {
        return getFluid(NamespaceID.from(id));
    }

    /**
     * Returns the corresponding Fluid matching the given id. Returns 'EMPTY' if none match.
     */
    @NotNull
    public static Fluid getFluid(NamespaceID id) {
        return fluids.getOrDefault(id, Fluid.EMPTY);
    }
}
