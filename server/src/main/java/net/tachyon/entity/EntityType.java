package net.tachyon.entity;

import java.util.function.BiFunction;

import net.tachyon.entity.metadata.TachyonBatMeta;
import net.tachyon.entity.metadata.TachyonGhastMeta;
import net.tachyon.entity.metadata.other.TachyonWitherSkullMeta;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.TachyonPlayerMeta;
import net.tachyon.entity.metadata.animal.ChickenMeta;
import net.tachyon.entity.metadata.animal.CowMeta;
import net.tachyon.entity.metadata.animal.TachyonHorseMeta;
import net.tachyon.entity.metadata.animal.MooshroomMeta;
import net.tachyon.entity.metadata.animal.TachyonPigMeta;
import net.tachyon.entity.metadata.animal.TachyonRabbitMeta;
import net.tachyon.entity.metadata.animal.TachyonSheepMeta;
import net.tachyon.entity.metadata.animal.tameable.TachyonOcelotMeta;
import net.tachyon.entity.metadata.animal.tameable.TachyonWolfMeta;
import net.tachyon.entity.metadata.TachyonArrowMeta;
import net.tachyon.entity.metadata.golem.TachyonIronGolemMeta;
import net.tachyon.entity.metadata.golem.SnowGolemMeta;
import net.tachyon.entity.metadata.item.EyeOfEnderMeta;
import net.tachyon.entity.metadata.item.FireballMeta;
import net.tachyon.entity.metadata.item.ItemEntityMeta;
import net.tachyon.entity.metadata.item.SmallFireballMeta;
import net.tachyon.entity.metadata.item.SnowballMeta;
import net.tachyon.entity.metadata.item.ThrownEggMeta;
import net.tachyon.entity.metadata.item.ThrownEnderPearlMeta;
import net.tachyon.entity.metadata.item.ThrownExperienceBottleMeta;
import net.tachyon.entity.metadata.item.ThrownPotionMeta;
import net.tachyon.entity.metadata.minecart.TachyonMinecartMeta;
import net.tachyon.entity.metadata.monster.TachyonBlazeMeta;
import net.tachyon.entity.metadata.monster.CaveSpiderMeta;
import net.tachyon.entity.metadata.monster.TachyonCreeperMeta;
import net.tachyon.entity.metadata.monster.TachyonEndermanMeta;
import net.tachyon.entity.metadata.monster.EndermiteMeta;
import net.tachyon.entity.metadata.monster.GiantMeta;
import net.tachyon.entity.metadata.monster.TachyonGuardianMeta;
import net.tachyon.entity.metadata.monster.SilverfishMeta;
import net.tachyon.entity.metadata.monster.TachyonSpiderMeta;
import net.tachyon.entity.metadata.monster.TachyonWitchMeta;
import net.tachyon.entity.metadata.monster.TachyonWitherMeta;
import net.tachyon.entity.metadata.monster.skeleton.SkeletonMeta;
import net.tachyon.entity.metadata.monster.zombie.ZombieMeta;
import net.tachyon.entity.metadata.monster.zombie.ZombiePigmanMeta;
import net.tachyon.entity.metadata.other.TachyonArmorStandMeta;
import net.tachyon.entity.metadata.other.TachyonBoatMeta;
import net.tachyon.entity.metadata.other.TachyonEndCrystalMeta;
import net.tachyon.entity.metadata.other.EnderDragonMeta;
import net.tachyon.entity.metadata.other.TachyonExperienceOrbMeta;
import net.tachyon.entity.metadata.other.TachyonFallingBlockMeta;
import net.tachyon.entity.metadata.other.TachyonFireworkRocketMeta;
import net.tachyon.entity.metadata.other.TachyonFishingHookMeta;
import net.tachyon.entity.metadata.other.TachyonItemFrameMeta;
import net.tachyon.entity.metadata.other.LeashKnotMeta;
import net.tachyon.entity.metadata.other.TachyonMagmaCubeMeta;
import net.tachyon.entity.metadata.other.TachyonPaintingMeta;
import net.tachyon.entity.metadata.other.PrimedTntMeta;
import net.tachyon.entity.metadata.other.TachyonSlimeMeta;
import net.tachyon.entity.metadata.villager.TachyonVillagerMeta;
import net.tachyon.entity.metadata.TachyonSquidMeta;
import org.jetbrains.annotations.NotNull;


public enum EntityType {
    FISHING_FLOAT("minecraft:FishingFloat", 0, (byte) 90, 0.25, 0.25, TachyonFishingHookMeta::new, EntitySpawnType.OBJECT),

    ITEM("minecraft:Item", 1, (byte) 2, 0.25, 0.25, ItemEntityMeta::new, EntitySpawnType.OBJECT),

    THROWN_EGG("minecraft:ThrownEgg", 7, (byte) 62, 0.25, 0.25, ThrownEggMeta::new, EntitySpawnType.OBJECT),

    LEASH_KNOT("minecraft:LeashKnot", 8, (byte) 77, 0.5, 0.5, LeashKnotMeta::new, EntitySpawnType.OBJECT),

    ARROW("minecraft:Arrow", 10, (byte) 60, 0.5, 0.5, TachyonArrowMeta::new, EntitySpawnType.OBJECT),

    SNOWBALL("minecraft:Snowball", 11, (byte) 61, 0.25, 0.25, SnowballMeta::new, EntitySpawnType.OBJECT),

    FIREBALL("minecraft:Fireball", 12, (byte) 63, 0.0, 0.0, FireballMeta::new, EntitySpawnType.OBJECT),

    SMALL_FIREBALL("minecraft:SmallFireball", 13, (byte) 64, 0.3125, 0.3125, SmallFireballMeta::new, EntitySpawnType.OBJECT),

    THROWN_ENDERPEARL("minecraft:ThrownEnderpearl", 14, (byte) 65, 0.25, 0.25, ThrownEnderPearlMeta::new, EntitySpawnType.OBJECT),

    EYE_OF_ENDER_SIGNAL("minecraft:EyeOfEnderSignal", 15, (byte) 72, 0.25, 0.25, EyeOfEnderMeta::new, EntitySpawnType.OBJECT),

    THROWN_POTION("minecraft:ThrownPotion", 16, (byte) 73, 0.25, 0.25, ThrownPotionMeta::new, EntitySpawnType.OBJECT),

    THROWN_EXP_BOTTLE("minecraft:ThrownExpBottle", 17, (byte) 75, 0.25, 0.25, ThrownExperienceBottleMeta::new, EntitySpawnType.OBJECT),

    ITEM_FRAME("minecraft:ItemFrame", 18, (byte) 71, 0.5, 0.5, TachyonItemFrameMeta::new, EntitySpawnType.OBJECT),

    WITHER_SKULL("minecraft:WitherSkull", 19, (byte) 66, 0.3125, 0.3125, TachyonWitherSkullMeta::new, EntitySpawnType.OBJECT),

    PRIMED_TNT("minecraft:PrimedTnt", 20, (byte) 50, 0.98, 0.98, PrimedTntMeta::new, EntitySpawnType.OBJECT),

    FALLING_SAND("minecraft:FallingSand", 21, (byte) 70, 0.0, 0.0, TachyonFallingBlockMeta::new, EntitySpawnType.OBJECT),

    FIREWORKS_ROCKET_ENTITY("minecraft:FireworksRocketEntity", 22, (byte) 76, 0.25, 0.25, TachyonFireworkRocketMeta::new, EntitySpawnType.OBJECT),

    ARMOR_STAND("minecraft:ArmorStand", 30, (byte) 78, 0.5, 1.975, TachyonArmorStandMeta::new, EntitySpawnType.OBJECT),

    BOAT("minecraft:Boat", 41, (byte) 1, 1.5, 0.6, TachyonBoatMeta::new, EntitySpawnType.OBJECT),

    MINECART_RIDEABLE("minecraft:MinecartRideable", 42, (byte) 10, 0.98, 0.7, TachyonMinecartMeta::new, EntitySpawnType.OBJECT),

    CREEPER("minecraft:Creeper", 50, (byte) 50, 0.0, 0.0, TachyonCreeperMeta::new, EntitySpawnType.MOB),

    SKELETON("minecraft:Skeleton", 51, (byte) 51, 0.0, 0.0, SkeletonMeta::new, EntitySpawnType.MOB),

    SPIDER("minecraft:Spider", 52, (byte) 52, 1.4, 0.9, TachyonSpiderMeta::new, EntitySpawnType.MOB),

    GIANT("minecraft:Giant", 53, (byte) 53, 0.0, 0.0, GiantMeta::new, EntitySpawnType.MOB),

    ZOMBIE("minecraft:Zombie", 54, (byte) 54, 0.6, 1.95, ZombieMeta::new, EntitySpawnType.MOB),

    SLIME("minecraft:Slime", 55, (byte) 55, 0.0, 0.0, TachyonSlimeMeta::new, EntitySpawnType.MOB),

    GHAST("minecraft:Ghast", 56, (byte) 56, 4.0, 4.0, TachyonGhastMeta::new, EntitySpawnType.MOB),

    PIG_ZOMBIE("minecraft:PigZombie", 57, (byte) 57, 0.6, 1.95, ZombiePigmanMeta::new, EntitySpawnType.MOB),

    ENDERMAN("minecraft:Enderman", 58, (byte) 58, 0.6, 2.9, TachyonEndermanMeta::new, EntitySpawnType.MOB),

    CAVE_SPIDER("minecraft:CaveSpider", 59, (byte) 59, 0.7, 0.5, CaveSpiderMeta::new, EntitySpawnType.MOB),

    SILVERFISH("minecraft:Silverfish", 60, (byte) 60, 0.4, 0.3, SilverfishMeta::new, EntitySpawnType.MOB),

    BLAZE("minecraft:Blaze", 61, (byte) 61, 0.0, 0.0, TachyonBlazeMeta::new, EntitySpawnType.MOB),

    LAVA_SLIME("minecraft:LavaSlime", 62, (byte) 62, 0.0, 0.0, TachyonMagmaCubeMeta::new, EntitySpawnType.MOB),

    ENDER_DRAGON("minecraft:EnderDragon", 63, (byte) 63, 16.0, 8.0, EnderDragonMeta::new, EntitySpawnType.MOB),

    WITHER_BOSS("minecraft:WitherBoss", 64, (byte) 64, 0.9, 3.5, TachyonWitherMeta::new, EntitySpawnType.MOB),

    BAT("minecraft:Bat", 65, (byte) 65, 0.5, 0.9, TachyonBatMeta::new, EntitySpawnType.MOB),

    WITCH("minecraft:Witch", 66, (byte) 66, 0.6, 1.95, TachyonWitchMeta::new, EntitySpawnType.MOB),

    ENDERMITE("minecraft:Endermite", 67, (byte) 67, 0.4, 0.3, EndermiteMeta::new, EntitySpawnType.MOB),

    GUARDIAN("minecraft:Guardian", 68, (byte) 68, 0.85, 0.85, TachyonGuardianMeta::new, EntitySpawnType.MOB),

    PIG("minecraft:Pig", 90, (byte) 90, 0.9, 0.9, TachyonPigMeta::new, EntitySpawnType.MOB),

    SHEEP("minecraft:Sheep", 91, (byte) 91, 0.9, 1.3, TachyonSheepMeta::new, EntitySpawnType.MOB),

    COW("minecraft:Cow", 92, (byte) 92, 0.9, 1.3, CowMeta::new, EntitySpawnType.MOB),

    CHICKEN("minecraft:Chicken", 93, (byte) 93, 0.4, 0.7, ChickenMeta::new, EntitySpawnType.MOB),

    SQUID("minecraft:Squid", 94, (byte) 94, 0.95, 0.95, TachyonSquidMeta::new, EntitySpawnType.MOB),

    WOLF("minecraft:Wolf", 95, (byte) 95, 0.6, 0.8, TachyonWolfMeta::new, EntitySpawnType.MOB),

    MUSHROOM_COW("minecraft:MushroomCow", 96, (byte) 96, 0.9, 1.3, MooshroomMeta::new, EntitySpawnType.MOB),

    SNOW_MAN("minecraft:SnowMan", 97, (byte) 97, 0.7, 1.9, SnowGolemMeta::new, EntitySpawnType.MOB),

    OZELOT("minecraft:Ozelot", 98, (byte) 98, 0.6, 0.7, TachyonOcelotMeta::new, EntitySpawnType.MOB),

    VILLAGER_GOLEM("minecraft:VillagerGolem", 99, (byte) 99, 1.4, 2.9, TachyonIronGolemMeta::new, EntitySpawnType.MOB),

    ENTITY_HORSE("minecraft:EntityHorse", 100, (byte) 100, 1.4, 1.6, TachyonHorseMeta::new, EntitySpawnType.MOB),

    RABBIT("minecraft:Rabbit", 101, (byte) 101, 0.6, 0.7, TachyonRabbitMeta::new, EntitySpawnType.MOB),

    VILLAGER("minecraft:Villager", 120, (byte) 120, 0.0, 0.0, TachyonVillagerMeta::new, EntitySpawnType.MOB),

    ENDER_CRYSTAL("minecraft:EnderCrystal", 200, (byte) 51, 0.0, 0.0, TachyonEndCrystalMeta::new, EntitySpawnType.OBJECT),

    PLAYER("minecraft:Player", 1000, (byte) 0, 0.6, 1.8, TachyonPlayerMeta::new, EntitySpawnType.PLAYER),

    PAINTING("minecraft:Painting", 1001, (byte) 0, 0.5, 0.5, TachyonPaintingMeta::new, EntitySpawnType.PAINTING),

    EXPERIENCE_ORB("minecraft:ExperienceOrb", 1002, (byte) 0, 0.5, 0.5, TachyonExperienceOrbMeta::new, EntitySpawnType.EXPERIENCE_ORB);

    private static final EntityType[] VALUES = values();

    private String namespaceID;

    private int id;

    private byte protocolId;

    private double width;

    private double height;

    @NotNull
    private final BiFunction<TachyonEntity, Metadata, TachyonEntityMeta> metaConstructor;

    @NotNull
    private final EntitySpawnType spawnType;

    EntityType(String namespaceID, int id, byte protocolId, double width, double height,
            @NotNull BiFunction<TachyonEntity, Metadata, TachyonEntityMeta> metaConstructor,
            @NotNull EntitySpawnType spawnType) {
        this.namespaceID = namespaceID;
        this.id = id;
        this.protocolId = protocolId;
        this.width = width;
        this.height = height;
        this.metaConstructor = metaConstructor;
        this.spawnType = spawnType;
    }

    public int getId() {
        return id;
    }

    public byte getProtocolId() {
        return protocolId;
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public BiFunction<TachyonEntity, Metadata, TachyonEntityMeta> getMetaConstructor() {
        return this.metaConstructor;
    }

    public EntitySpawnType getSpawnType() {
        return this.spawnType;
    }

    public static EntityType fromId(short id) {
        for (EntityType o : values()) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }
}
