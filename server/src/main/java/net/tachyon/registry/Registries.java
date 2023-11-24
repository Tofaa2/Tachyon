package net.tachyon.registry;

import java.util.HashMap;
import java.util.function.BiFunction;

import net.tachyon.entity.Entity;
import net.tachyon.entity.EntityType;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.metadata.*;
import net.tachyon.entity.metadata.animal.*;
import net.tachyon.entity.metadata.animal.tameable.OcelotMeta;
import net.tachyon.entity.metadata.animal.tameable.TachyonOcelotMeta;
import net.tachyon.entity.metadata.animal.tameable.TachyonWolfMeta;
import net.tachyon.entity.metadata.animal.tameable.WolfMeta;
import net.tachyon.entity.metadata.golem.TachyonIronGolemMeta;
import net.tachyon.entity.metadata.golem.TachyonSnowGolemMeta;
import net.tachyon.entity.metadata.item.*;
import net.tachyon.entity.metadata.minecart.TachyonMinecartMeta;
import net.tachyon.entity.metadata.monster.*;
import net.tachyon.entity.metadata.monster.skeleton.TachyonSkeletonMeta;
import net.tachyon.entity.metadata.monster.GiantMeta;
import net.tachyon.entity.metadata.monster.zombie.TachyonZombiePigmanMeta;
import net.tachyon.entity.metadata.other.*;
import net.tachyon.entity.metadata.villager.TachyonVillagerMeta;
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


    private static final HashMap<Class<?>, BiFunction<Entity, Metadata, EntityMeta>> entityMetaSuppliers = new HashMap<>() {{
        put(ItemEntityMeta.class, TachyonItemEntityMeta::new);
        put(FishingHookMeta.class, TachyonFishingHookMeta::new);
        put(ThrownEggMeta.class, TachyonThrownEggMeta::new);
        put(LeashKnotMeta.class, TachyonLeashKnotMeta::new);
        put(ArrowMeta.class, TachyonArrowMeta::new);
        put(SnowballMeta.class, TachyonSnowballMeta::new);
        put(FireballMeta.class, TachyonFireballMeta::new);
        put(SmallFireballMeta.class, TachyonSmallFireballMeta::new);
        put(ThrownEnderPealMeta.class, TachyonThrownEnderPearlMeta::new);
        put(EyeOfEnderMeta.class, TachyonEyeOfEnderMeta::new);
        put(ThrownPotionMeta.class, TachyonThrownPotionMeta::new);
        put(ThrownExperienceBottleMeta.class, TachyonThrownExperienceBottleMeta::new);
        put(ItemFrameMeta.class, TachyonItemFrameMeta::new);
        put(WitherSkullMeta.class, TachyonWitherSkullMeta::new);
        put(PrimedTntMeta.class, TachyonPrimedTntMeta::new);
        put(FallingBlockMeta.class, TachyonFallingBlockMeta::new);
        put(FireworkRocketMeta.class, TachyonFireworkRocketMeta::new);
        put(ArmorStandMeta.class, TachyonArmorStandMeta::new);
        put(BoatMeta.class, TachyonBoatMeta::new);
        put(MinecartMeta.class, TachyonMinecartMeta::new);
        put(CreeperMeta.class, TachyonCreeperMeta::new);
        put(SkeletonMeta.class, TachyonSkeletonMeta::new);
        put(SpiderMeta.class, TachyonSpiderMeta::new);
        put(GiantMeta.class, TachyonGiantMeta::new);
        put(SlimeMeta.class, TachyonSlimeMeta::new);
        put(GhastMeta.class, TachyonGhastMeta::new);
        put(ZombiePigmanMeta.class, TachyonZombiePigmanMeta::new);
        put(EndermanMeta.class, TachyonEndermanMeta::new);
        put(CaveSpiderMeta.class, TachyonCaveSpiderMeta::new);
        put(SilverfishMeta.class, TachyonSilverfishMeta::new);
        put(BlazeMeta.class, TachyonBlazeMeta::new);
        put(MagmaCubeMeta.class, TachyonMagmaCubeMeta::new);
        put(WitherMeta.class, TachyonWitherMeta::new);
        put(BatMeta.class, TachyonBatMeta::new);
        put(WitchMeta.class, TachyonWitchMeta::new);
        put(EndermiteMeta.class, TachyonEndermiteMeta::new);
        put(GuardianMeta.class, TachyonGuardianMeta::new);
        put(PigMeta.class, TachyonPigMeta::new);
        put(SheepMeta.class, TachyonSheepMeta::new);
        put(CowMeta.class, TachyonCowMeta::new);
        put(ChickenMeta.class, TachyonChickenMeta::new);
        put(SquidMeta.class, TachyonSquidMeta::new);
        put(WolfMeta.class, TachyonWolfMeta::new);
        put(MooshroomMeta.class, TachyonMooshroomMeta::new);
        put(SnowGolemMeta.class, TachyonSnowGolemMeta::new);
        put(OcelotMeta.class, TachyonOcelotMeta::new);
        put(IronGolemMeta.class, TachyonIronGolemMeta::new);
        put(HorseMeta.class, TachyonHorseMeta::new);
        put(RabbitMeta.class, TachyonRabbitMeta::new);
        put(VillagerMeta.class, TachyonVillagerMeta::new);
        put(EndCrystalMeta.class, TachyonEndCrystalMeta::new);
        put(PlayerMeta.class, TachyonPlayerMeta::new);
        put(PaintingMeta.class, TachyonPaintingMeta::new);
        put(ExperienceOrbMeta.class, TachyonExperienceOrbMeta::new);
    }};

    public static BiFunction<Entity, Metadata, EntityMeta> getEntityMetaSupplier(Class<?> clazz) {
        return entityMetaSuppliers.get(clazz);
    }

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
