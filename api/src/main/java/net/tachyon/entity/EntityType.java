package net.tachyon.entity;

import java.util.function.BiFunction;

import net.tachyon.Tachyon;
import net.tachyon.entity.metadata.*;
import net.tachyon.entity.metadata.animal.*;
import net.tachyon.entity.metadata.animal.tameable.OcelotMeta;
import net.tachyon.entity.metadata.animal.tameable.WolfMeta;
import net.tachyon.entity.metadata.item.*;
import net.tachyon.entity.metadata.monster.*;
import net.tachyon.entity.metadata.other.*;
import org.jetbrains.annotations.NotNull;


public enum EntityType {
    FISHING_FLOAT("minecraft:FishingFloat", 0, (byte) 90, 0.25, 0.25, meta(FishingHookMeta.class), EntitySpawnType.OBJECT),

    ITEM("minecraft:Item", 1, (byte) 2, 0.25, 0.25, meta(ItemEntityMeta.class), EntitySpawnType.OBJECT),

    THROWN_EGG("minecraft:ThrownEgg", 7, (byte) 62, 0.25, 0.25, meta(ThrownEggMeta.class), EntitySpawnType.OBJECT),

    LEASH_KNOT("minecraft:LeashKnot", 8, (byte) 77, 0.5, 0.5, meta(LeashKnotMeta.class), EntitySpawnType.OBJECT),

    ARROW("minecraft:Arrow", 10, (byte) 60, 0.5, 0.5, meta(ArrowMeta.class), EntitySpawnType.OBJECT),

    SNOWBALL("minecraft:Snowball", 11, (byte) 61, 0.25, 0.25, meta(SnowballMeta.class), EntitySpawnType.OBJECT),

    FIREBALL("minecraft:Fireball", 12, (byte) 63, 0.0, 0.0, meta(FireballMeta.class), EntitySpawnType.OBJECT),

    SMALL_FIREBALL("minecraft:SmallFireball", 13, (byte) 64, 0.3125, 0.3125, meta(SmallFireballMeta.class), EntitySpawnType.OBJECT),

    THROWN_ENDERPEARL("minecraft:ThrownEnderpearl", 14, (byte) 65, 0.25, 0.25, meta(ThrownEnderPealMeta.class), EntitySpawnType.OBJECT),

    EYE_OF_ENDER_SIGNAL("minecraft:EyeOfEnderSignal", 15, (byte) 72, 0.25, 0.25, meta(EyeOfEnderMeta.class), EntitySpawnType.OBJECT),

    THROWN_POTION("minecraft:ThrownPotion", 16, (byte) 73, 0.25, 0.25, meta(ThrownPotionMeta.class), EntitySpawnType.OBJECT),

    THROWN_EXP_BOTTLE("minecraft:ThrownExpBottle", 17, (byte) 75, 0.25, 0.25, meta(ThrownExperienceBottleMeta.class), EntitySpawnType.OBJECT),

    ITEM_FRAME("minecraft:ItemFrame", 18, (byte) 71, 0.5, 0.5, meta(ItemFrameMeta.class), EntitySpawnType.OBJECT),

    WITHER_SKULL("minecraft:WitherSkull", 19, (byte) 66, 0.3125, 0.3125, meta(WitherSkullMeta.class), EntitySpawnType.OBJECT),

    PRIMED_TNT("minecraft:PrimedTnt", 20, (byte) 50, 0.98, 0.98, meta(PrimedTntMeta.class), EntitySpawnType.OBJECT),

    FALLING_SAND("minecraft:FallingSand", 21, (byte) 70, 0.0, 0.0, meta(FallingBlockMeta.class), EntitySpawnType.OBJECT),

    FIREWORKS_ROCKET_ENTITY("minecraft:FireworksRocketEntity", 22, (byte) 76, 0.25, 0.25, meta(FireworkRocketMeta.class), EntitySpawnType.OBJECT),

    ARMOR_STAND("minecraft:ArmorStand", 30, (byte) 78, 0.5, 1.975, meta(ArmorStandMeta.class), EntitySpawnType.OBJECT),

    BOAT("minecraft:Boat", 41, (byte) 1, 1.5, 0.6, meta(BoatMeta.class), EntitySpawnType.OBJECT),

    MINECART_RIDEABLE("minecraft:MinecartRideable", 42, (byte) 10, 0.98, 0.7, meta(MinecartMeta.class), EntitySpawnType.OBJECT),

    CREEPER("minecraft:Creeper", 50, (byte) 50, 0.0, 0.0, meta(CreeperMeta.class), EntitySpawnType.MOB),

    SKELETON("minecraft:Skeleton", 51, (byte) 51, 0.0, 0.0, meta(SkeletonMeta.class), EntitySpawnType.MOB),

    SPIDER("minecraft:Spider", 52, (byte) 52, 1.4, 0.9, meta(SpiderMeta.class), EntitySpawnType.MOB),

    GIANT("minecraft:Giant", 53, (byte) 53, 0.0, 0.0, meta(GiantMeta.class), EntitySpawnType.MOB),

    ZOMBIE("minecraft:Zombie", 54, (byte) 54, 0.6, 1.95, meta(ZombieMeta.class), EntitySpawnType.MOB),

    SLIME("minecraft:Slime", 55, (byte) 55, 0.0, 0.0, meta(SlimeMeta.class), EntitySpawnType.MOB),

    GHAST("minecraft:Ghast", 56, (byte) 56, 4.0, 4.0, meta(GhastMeta.class), EntitySpawnType.MOB),

    PIG_ZOMBIE("minecraft:PigZombie", 57, (byte) 57, 0.6, 1.95, meta(ZombiePigmanMeta.class), EntitySpawnType.MOB),

    ENDERMAN("minecraft:Enderman", 58, (byte) 58, 0.6, 2.9, meta(EndermanMeta.class), EntitySpawnType.MOB),

    CAVE_SPIDER("minecraft:CaveSpider", 59, (byte) 59, 0.7, 0.5, meta(CaveSpiderMeta.class), EntitySpawnType.MOB),

    SILVERFISH("minecraft:Silverfish", 60, (byte) 60, 0.4, 0.3, meta(SilverfishMeta.class), EntitySpawnType.MOB),

    BLAZE("minecraft:Blaze", 61, (byte) 61, 0.0, 0.0, meta(BlazeMeta.class), EntitySpawnType.MOB),

    LAVA_SLIME("minecraft:LavaSlime", 62, (byte) 62, 0.0, 0.0, meta(MagmaCubeMeta.class), EntitySpawnType.MOB),

    ENDER_DRAGON("minecraft:EnderDragon", 63, (byte) 63, 16.0, 8.0, meta(EnderDragonMeta.class), EntitySpawnType.MOB),

    WITHER_BOSS("minecraft:WitherBoss", 64, (byte) 64, 0.9, 3.5, meta(WitherMeta.class), EntitySpawnType.MOB),

    BAT("minecraft:Bat", 65, (byte) 65, 0.5, 0.9, meta(BatMeta.class), EntitySpawnType.MOB),

    WITCH("minecraft:Witch", 66, (byte) 66, 0.6, 1.95, meta(WitchMeta.class), EntitySpawnType.MOB),

    ENDERMITE("minecraft:Endermite", 67, (byte) 67, 0.4, 0.3, meta(EndermiteMeta.class), EntitySpawnType.MOB),

    GUARDIAN("minecraft:Guardian", 68, (byte) 68, 0.85, 0.85, meta(GuardianMeta.class), EntitySpawnType.MOB),

    PIG("minecraft:Pig", 90, (byte) 90, 0.9, 0.9, meta(PigMeta.class), EntitySpawnType.MOB),

    SHEEP("minecraft:Sheep", 91, (byte) 91, 0.9, 1.3, meta(SheepMeta.class), EntitySpawnType.MOB),

    COW("minecraft:Cow", 92, (byte) 92, 0.9, 1.3, meta(CowMeta.class), EntitySpawnType.MOB),

    CHICKEN("minecraft:Chicken", 93, (byte) 93, 0.4, 0.7, meta(ChickenMeta.class), EntitySpawnType.MOB),

    SQUID("minecraft:Squid", 94, (byte) 94, 0.95, 0.95, meta(SquidMeta.class), EntitySpawnType.MOB),

    WOLF("minecraft:Wolf", 95, (byte) 95, 0.6, 0.8, meta(WolfMeta.class), EntitySpawnType.MOB),

    MUSHROOM_COW("minecraft:MushroomCow", 96, (byte) 96, 0.9, 1.3, meta(MooshroomMeta.class), EntitySpawnType.MOB),

    SNOW_MAN("minecraft:SnowMan", 97, (byte) 97, 0.7, 1.9, meta(SnowGolemMeta.class), EntitySpawnType.MOB),

    OZELOT("minecraft:Ozelot", 98, (byte) 98, 0.6, 0.7, meta(OcelotMeta.class), EntitySpawnType.MOB),

    VILLAGER_GOLEM("minecraft:VillagerGolem", 99, (byte) 99, 1.4, 2.9, meta(IronGolemMeta.class), EntitySpawnType.MOB),

    ENTITY_HORSE("minecraft:EntityHorse", 100, (byte) 100, 1.4, 1.6, meta(HorseMeta.class), EntitySpawnType.MOB),

    RABBIT("minecraft:Rabbit", 101, (byte) 101, 0.6, 0.7, meta(RabbitMeta.class), EntitySpawnType.MOB),

    VILLAGER("minecraft:Villager", 120, (byte) 120, 0.0, 0.0, meta(VillagerMeta.class), EntitySpawnType.MOB),

    ENDER_CRYSTAL("minecraft:EnderCrystal", 200, (byte) 51, 0.0, 0.0, meta(EndCrystalMeta.class), EntitySpawnType.OBJECT),

    PLAYER("minecraft:Player", 1000, (byte) 0, 0.6, 1.8, meta(PlayerMeta.class), EntitySpawnType.PLAYER),

    PAINTING("minecraft:Painting", 1001, (byte) 0, 0.5, 0.5, meta(PaintingMeta.class), EntitySpawnType.PAINTING),

    EXPERIENCE_ORB("minecraft:ExperienceOrb", 1002, (byte) 0, 0.5, 0.5, meta(ExperienceOrbMeta.class), EntitySpawnType.EXPERIENCE_ORB);

    private static final EntityType[] VALUES = values();
    private static <T extends EntityMeta> BiFunction<Entity, Metadata, EntityMeta> meta(Class<T> clazz) {
        return Tachyon.getServer().createMeta(clazz);
    }

    private String namespaceID;

    private int id;

    private byte protocolId;

    private double width;

    private double height;

    @NotNull
    private final BiFunction<Entity, Metadata, EntityMeta> metaConstructor;

    @NotNull
    private final EntitySpawnType spawnType;

    EntityType(String namespaceID, int id, byte protocolId, double width, double height,
            @NotNull BiFunction<Entity, Metadata, EntityMeta> metaConstructor,
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

    @NotNull
    public BiFunction<Entity, Metadata, EntityMeta> getMetaConstructor() {
        return this.metaConstructor;
    }

    @NotNull
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
