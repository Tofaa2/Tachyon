package net.tachyon.item;

import net.tachyon.block.Block;
import net.tachyon.namespace.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Material {
    AIR("minecraft:air", (short) 0, 64, Block.AIR),

    STONE("minecraft:stone", (short) 1, 64, Block.STONE),

    GRASS("minecraft:grass", (short) 2, 64, Block.GRASS),

    DIRT("minecraft:dirt", (short) 3, 64, Block.DIRT),

    COBBLESTONE("minecraft:cobblestone", (short) 4, 64, Block.COBBLESTONE),

    PLANKS("minecraft:planks", (short) 5, 64, Block.PLANKS),

    SAPLING("minecraft:sapling", (short) 6, 64, Block.SAPLING),

    BEDROCK("minecraft:bedrock", (short) 7, 64, Block.BEDROCK),

    SAND("minecraft:sand", (short) 12, 64, Block.SAND),

    GRAVEL("minecraft:gravel", (short) 13, 64, Block.GRAVEL),

    GOLD_ORE("minecraft:gold_ore", (short) 14, 64, Block.GOLD_ORE),

    IRON_ORE("minecraft:iron_ore", (short) 15, 64, Block.IRON_ORE),

    COAL_ORE("minecraft:coal_ore", (short) 16, 64, Block.COAL_ORE),

    LOG("minecraft:log", (short) 17, 64, Block.LOG),

    LEAVES("minecraft:leaves", (short) 18, 64, Block.LEAVES),

    SPONGE("minecraft:sponge", (short) 19, 64, Block.SPONGE),

    GLASS("minecraft:glass", (short) 20, 64, Block.GLASS),

    LAPIS_ORE("minecraft:lapis_ore", (short) 21, 64, Block.LAPIS_ORE),

    LAPIS_BLOCK("minecraft:lapis_block", (short) 22, 64, Block.LAPIS_BLOCK),

    DISPENSER("minecraft:dispenser", (short) 23, 64, Block.DISPENSER),

    SANDSTONE("minecraft:sandstone", (short) 24, 64, Block.SANDSTONE),

    NOTEBLOCK("minecraft:noteblock", (short) 25, 64, Block.NOTEBLOCK),

    GOLDEN_RAIL("minecraft:golden_rail", (short) 27, 64, Block.GOLDEN_RAIL),

    DETECTOR_RAIL("minecraft:detector_rail", (short) 28, 64, Block.DETECTOR_RAIL),

    STICKY_PISTON("minecraft:sticky_piston", (short) 29, 64, Block.STICKY_PISTON),

    WEB("minecraft:web", (short) 30, 64, Block.WEB),

    TALLGRASS("minecraft:tallgrass", (short) 31, 64, Block.TALLGRASS),

    DEADBUSH("minecraft:deadbush", (short) 32, 64, Block.DEADBUSH),

    PISTON("minecraft:piston", (short) 33, 64, Block.PISTON),

    WOOL("minecraft:wool", (short) 35, 64, Block.WOOL),

    YELLOW_FLOWER("minecraft:yellow_flower", (short) 37, 64, Block.YELLOW_FLOWER),

    RED_FLOWER("minecraft:red_flower", (short) 38, 64, Block.RED_FLOWER),

    BROWN_MUSHROOM("minecraft:brown_mushroom", (short) 39, 64, Block.BROWN_MUSHROOM),

    RED_MUSHROOM("minecraft:red_mushroom", (short) 40, 64, Block.RED_MUSHROOM),

    GOLD_BLOCK("minecraft:gold_block", (short) 41, 64, Block.GOLD_BLOCK),

    IRON_BLOCK("minecraft:iron_block", (short) 42, 64, Block.IRON_BLOCK),

    STONE_SLAB("minecraft:stone_slab", (short) 44, 64, Block.STONE_SLAB),

    BRICK_BLOCK("minecraft:brick_block", (short) 45, 64, Block.BRICK_BLOCK),

    TNT("minecraft:tnt", (short) 46, 64, Block.TNT),

    BOOKSHELF("minecraft:bookshelf", (short) 47, 64, Block.BOOKSHELF),

    MOSSY_COBBLESTONE("minecraft:mossy_cobblestone", (short) 48, 64, Block.MOSSY_COBBLESTONE),

    OBSIDIAN("minecraft:obsidian", (short) 49, 64, Block.OBSIDIAN),

    TORCH("minecraft:torch", (short) 50, 64, Block.TORCH),

    MOB_SPAWNER("minecraft:mob_spawner", (short) 52, 64, Block.MOB_SPAWNER),

    OAK_STAIRS("minecraft:oak_stairs", (short) 53, 64, Block.OAK_STAIRS),

    CHEST("minecraft:chest", (short) 54, 64, Block.CHEST),

    DIAMOND_ORE("minecraft:diamond_ore", (short) 56, 64, Block.DIAMOND_ORE),

    DIAMOND_BLOCK("minecraft:diamond_block", (short) 57, 64, Block.DIAMOND_BLOCK),

    CRAFTING_TABLE("minecraft:crafting_table", (short) 58, 64, Block.CRAFTING_TABLE),

    FARMLAND("minecraft:farmland", (short) 60, 64, Block.FARMLAND),

    FURNACE("minecraft:furnace", (short) 61, 64, Block.FURNACE),

    LIT_FURNACE("minecraft:lit_furnace", (short) 62, 64, Block.LIT_FURNACE),

    LADDER("minecraft:ladder", (short) 65, 64, Block.LADDER),

    RAIL("minecraft:rail", (short) 66, 64, Block.RAIL),

    STONE_STAIRS("minecraft:stone_stairs", (short) 67, 64, Block.STONE_STAIRS),

    LEVER("minecraft:lever", (short) 69, 64, Block.LEVER),

    STONE_PRESSURE_PLATE("minecraft:stone_pressure_plate", (short) 70, 64, Block.STONE_PRESSURE_PLATE),

    WOODEN_PRESSURE_PLATE("minecraft:wooden_pressure_plate", (short) 72, 64, Block.WOODEN_PRESSURE_PLATE),

    REDSTONE_ORE("minecraft:redstone_ore", (short) 73, 64, Block.REDSTONE_ORE),

    REDSTONE_TORCH("minecraft:redstone_torch", (short) 76, 64, Block.REDSTONE_TORCH),

    STONE_BUTTON("minecraft:stone_button", (short) 77, 64, Block.STONE_BUTTON),

    SNOW_LAYER("minecraft:snow_layer", (short) 78, 64, Block.SNOW_LAYER),

    ICE("minecraft:ice", (short) 79, 64, Block.ICE),

    SNOW("minecraft:snow", (short) 80, 64, Block.SNOW),

    CACTUS("minecraft:cactus", (short) 81, 64, Block.CACTUS),

    CLAY("minecraft:clay", (short) 82, 64, Block.CLAY),

    JUKEBOX("minecraft:jukebox", (short) 84, 64, Block.JUKEBOX),

    FENCE("minecraft:fence", (short) 85, 64, Block.FENCE),

    PUMPKIN("minecraft:pumpkin", (short) 86, 64, Block.PUMPKIN),

    NETHERRACK("minecraft:netherrack", (short) 87, 64, Block.NETHERRACK),

    SOUL_SAND("minecraft:soul_sand", (short) 88, 64, Block.SOUL_SAND),

    GLOWSTONE("minecraft:glowstone", (short) 89, 64, Block.GLOWSTONE),

    LIT_PUMPKIN("minecraft:lit_pumpkin", (short) 91, 64, Block.LIT_PUMPKIN),

    STAINED_GLASS("minecraft:stained_glass", (short) 95, 64, Block.STAINED_GLASS),

    TRAPDOOR("minecraft:trapdoor", (short) 96, 64, Block.TRAPDOOR),

    MONSTER_EGG("minecraft:monster_egg", (short) 97, 64, Block.MONSTER_EGG),

    STONEBRICK("minecraft:stonebrick", (short) 98, 64, Block.STONEBRICK),

    BROWN_MUSHROOM_BLOCK("minecraft:brown_mushroom_block", (short) 99, 64, Block.BROWN_MUSHROOM_BLOCK),

    RED_MUSHROOM_BLOCK("minecraft:red_mushroom_block", (short) 100, 64, Block.RED_MUSHROOM_BLOCK),

    IRON_BARS("minecraft:iron_bars", (short) 101, 64, Block.IRON_BARS),

    GLASS_PANE("minecraft:glass_pane", (short) 102, 64, Block.GLASS_PANE),

    MELON_BLOCK("minecraft:melon_block", (short) 103, 64, Block.MELON_BLOCK),

    VINE("minecraft:vine", (short) 106, 64, Block.VINE),

    FENCE_GATE("minecraft:fence_gate", (short) 107, 64, Block.FENCE_GATE),

    BRICK_STAIRS("minecraft:brick_stairs", (short) 108, 64, Block.BRICK_STAIRS),

    STONE_BRICK_STAIRS("minecraft:stone_brick_stairs", (short) 109, 64, Block.STONE_BRICK_STAIRS),

    MYCELIUM("minecraft:mycelium", (short) 110, 64, Block.MYCELIUM),

    WATERLILY("minecraft:waterlily", (short) 111, 64, Block.WATERLILY),

    NETHER_BRICK("minecraft:nether_brick", (short) 112, 64, Block.NETHER_BRICK),

    NETHER_BRICK_FENCE("minecraft:nether_brick_fence", (short) 113, 64, Block.NETHER_BRICK_FENCE),

    NETHER_BRICK_STAIRS("minecraft:nether_brick_stairs", (short) 114, 64, Block.NETHER_BRICK_STAIRS),

    ENCHANTING_TABLE("minecraft:enchanting_table", (short) 116, 64, Block.ENCHANTING_TABLE),

    END_PORTAL_FRAME("minecraft:end_portal_frame", (short) 120, 64, Block.END_PORTAL_FRAME),

    END_STONE("minecraft:end_stone", (short) 121, 64, Block.END_STONE),

    DRAGON_EGG("minecraft:dragon_egg", (short) 122, 64, Block.DRAGON_EGG),

    REDSTONE_LAMP("minecraft:redstone_lamp", (short) 123, 64, Block.REDSTONE_LAMP),

    WOODEN_SLAB("minecraft:wooden_slab", (short) 126, 64, Block.WOODEN_SLAB),

    SANDSTONE_STAIRS("minecraft:sandstone_stairs", (short) 128, 64, Block.SANDSTONE_STAIRS),

    EMERALD_ORE("minecraft:emerald_ore", (short) 129, 64, Block.EMERALD_ORE),

    ENDER_CHEST("minecraft:ender_chest", (short) 130, 64, Block.ENDER_CHEST),

    TRIPWIRE_HOOK("minecraft:tripwire_hook", (short) 131, 64, Block.TRIPWIRE_HOOK),

    EMERALD_BLOCK("minecraft:emerald_block", (short) 133, 64, Block.EMERALD_BLOCK),

    SPRUCE_STAIRS("minecraft:spruce_stairs", (short) 134, 64, Block.SPRUCE_STAIRS),

    BIRCH_STAIRS("minecraft:birch_stairs", (short) 135, 64, Block.BIRCH_STAIRS),

    JUNGLE_STAIRS("minecraft:jungle_stairs", (short) 136, 64, Block.JUNGLE_STAIRS),

    COMMAND_BLOCK("minecraft:command_block", (short) 137, 64, Block.COMMAND_BLOCK),

    BEACON("minecraft:beacon", (short) 138, 64, Block.BEACON),

    COBBLESTONE_WALL("minecraft:cobblestone_wall", (short) 139, 64, Block.COBBLESTONE_WALL),

    WOODEN_BUTTON("minecraft:wooden_button", (short) 143, 64, Block.WOODEN_BUTTON),

    ANVIL("minecraft:anvil", (short) 145, 64, Block.ANVIL),

    TRAPPED_CHEST("minecraft:trapped_chest", (short) 146, 64, Block.TRAPPED_CHEST),

    LIGHT_WEIGHTED_PRESSURE_PLATE("minecraft:light_weighted_pressure_plate", (short) 147, 64, Block.LIGHT_WEIGHTED_PRESSURE_PLATE),

    HEAVY_WEIGHTED_PRESSURE_PLATE("minecraft:heavy_weighted_pressure_plate", (short) 148, 64, Block.HEAVY_WEIGHTED_PRESSURE_PLATE),

    DAYLIGHT_DETECTOR("minecraft:daylight_detector", (short) 151, 64, Block.DAYLIGHT_DETECTOR),

    REDSTONE_BLOCK("minecraft:redstone_block", (short) 152, 64, Block.REDSTONE_BLOCK),

    QUARTZ_ORE("minecraft:quartz_ore", (short) 153, 64, Block.QUARTZ_ORE),

    HOPPER("minecraft:hopper", (short) 154, 64, Block.HOPPER),

    QUARTZ_BLOCK("minecraft:quartz_block", (short) 155, 64, Block.QUARTZ_BLOCK),

    QUARTZ_STAIRS("minecraft:quartz_stairs", (short) 156, 64, Block.QUARTZ_STAIRS),

    ACTIVATOR_RAIL("minecraft:activator_rail", (short) 157, 64, Block.ACTIVATOR_RAIL),

    DROPPER("minecraft:dropper", (short) 158, 64, Block.DROPPER),

    STAINED_HARDENED_CLAY("minecraft:stained_hardened_clay", (short) 159, 64, Block.STAINED_HARDENED_CLAY),

    STAINED_GLASS_PANE("minecraft:stained_glass_pane", (short) 160, 64, Block.STAINED_GLASS_PANE),

    LEAVES2("minecraft:leaves2", (short) 161, 64, Block.LEAVES2),

    LOG2("minecraft:log2", (short) 162, 64, Block.LOG2),

    ACACIA_STAIRS("minecraft:acacia_stairs", (short) 163, 64, Block.ACACIA_STAIRS),

    DARK_OAK_STAIRS("minecraft:dark_oak_stairs", (short) 164, 64, Block.DARK_OAK_STAIRS),

    SLIME("minecraft:slime", (short) 165, 64, Block.SLIME),

    BARRIER("minecraft:barrier", (short) 166, 64, Block.BARRIER),

    IRON_TRAPDOOR("minecraft:iron_trapdoor", (short) 167, 64, Block.IRON_TRAPDOOR),

    PRISMARINE("minecraft:prismarine", (short) 168, 64, Block.PRISMARINE),

    SEA_LANTERN("minecraft:sea_lantern", (short) 169, 64, Block.SEA_LANTERN),

    HAY_BLOCK("minecraft:hay_block", (short) 170, 64, Block.HAY_BLOCK),

    CARPET("minecraft:carpet", (short) 171, 64, Block.CARPET),

    HARDENED_CLAY("minecraft:hardened_clay", (short) 172, 64, Block.HARDENED_CLAY),

    COAL_BLOCK("minecraft:coal_block", (short) 173, 64, Block.COAL_BLOCK),

    PACKED_ICE("minecraft:packed_ice", (short) 174, 64, Block.PACKED_ICE),

    DOUBLE_PLANT("minecraft:double_plant", (short) 175, 64, Block.DOUBLE_PLANT),

    RED_SANDSTONE("minecraft:red_sandstone", (short) 179, 64, Block.RED_SANDSTONE),

    RED_SANDSTONE_STAIRS("minecraft:red_sandstone_stairs", (short) 180, 64, Block.RED_SANDSTONE_STAIRS),

    STONE_SLAB2("minecraft:stone_slab2", (short) 182, 64, Block.STONE_SLAB2),

    SPRUCE_FENCE_GATE("minecraft:spruce_fence_gate", (short) 183, 64, Block.SPRUCE_FENCE_GATE),

    BIRCH_FENCE_GATE("minecraft:birch_fence_gate", (short) 184, 64, Block.BIRCH_FENCE_GATE),

    JUNGLE_FENCE_GATE("minecraft:jungle_fence_gate", (short) 185, 64, Block.JUNGLE_FENCE_GATE),

    DARK_OAK_FENCE_GATE("minecraft:dark_oak_fence_gate", (short) 186, 64, Block.DARK_OAK_FENCE_GATE),

    ACACIA_FENCE_GATE("minecraft:acacia_fence_gate", (short) 187, 64, Block.ACACIA_FENCE_GATE),

    SPRUCE_FENCE("minecraft:spruce_fence", (short) 188, 64, Block.SPRUCE_FENCE),

    BIRCH_FENCE("minecraft:birch_fence", (short) 189, 64, Block.BIRCH_FENCE),

    JUNGLE_FENCE("minecraft:jungle_fence", (short) 190, 64, Block.JUNGLE_FENCE),

    DARK_OAK_FENCE("minecraft:dark_oak_fence", (short) 191, 64, Block.DARK_OAK_FENCE),

    ACACIA_FENCE("minecraft:acacia_fence", (short) 192, 64, Block.ACACIA_FENCE),

    IRON_SHOVEL("minecraft:iron_shovel", (short) 256, 64, null),

    IRON_PICKAXE("minecraft:iron_pickaxe", (short) 257, 64, null),

    IRON_AXE("minecraft:iron_axe", (short) 258, 64, null),

    FLINT_AND_STEEL("minecraft:flint_and_steel", (short) 259, 64, null),

    APPLE("minecraft:apple", (short) 260, 64, null),

    BOW("minecraft:bow", (short) 261, 64, null),

    ARROW("minecraft:arrow", (short) 262, 64, null),

    COAL("minecraft:coal", (short) 263, 64, null),

    DIAMOND("minecraft:diamond", (short) 264, 64, null),

    IRON_INGOT("minecraft:iron_ingot", (short) 265, 64, null),

    GOLD_INGOT("minecraft:gold_ingot", (short) 266, 64, null),

    IRON_SWORD("minecraft:iron_sword", (short) 267, 64, null),

    WOODEN_SWORD("minecraft:wooden_sword", (short) 268, 64, null),

    WOODEN_SHOVEL("minecraft:wooden_shovel", (short) 269, 64, null),

    WOODEN_PICKAXE("minecraft:wooden_pickaxe", (short) 270, 64, null),

    WOODEN_AXE("minecraft:wooden_axe", (short) 271, 64, null),

    STONE_SWORD("minecraft:stone_sword", (short) 272, 64, null),

    STONE_SHOVEL("minecraft:stone_shovel", (short) 273, 64, null),

    STONE_PICKAXE("minecraft:stone_pickaxe", (short) 274, 64, null),

    STONE_AXE("minecraft:stone_axe", (short) 275, 64, null),

    DIAMOND_SWORD("minecraft:diamond_sword", (short) 276, 64, null),

    DIAMOND_SHOVEL("minecraft:diamond_shovel", (short) 277, 64, null),

    DIAMOND_PICKAXE("minecraft:diamond_pickaxe", (short) 278, 64, null),

    DIAMOND_AXE("minecraft:diamond_axe", (short) 279, 64, null),

    STICK("minecraft:stick", (short) 280, 64, null),

    BOWL("minecraft:bowl", (short) 281, 64, null),

    MUSHROOM_STEW("minecraft:mushroom_stew", (short) 282, 64, null),

    GOLDEN_SWORD("minecraft:golden_sword", (short) 283, 64, null),

    GOLDEN_SHOVEL("minecraft:golden_shovel", (short) 284, 64, null),

    GOLDEN_PICKAXE("minecraft:golden_pickaxe", (short) 285, 64, null),

    GOLDEN_AXE("minecraft:golden_axe", (short) 286, 64, null),

    STRING("minecraft:string", (short) 287, 64, null),

    FEATHER("minecraft:feather", (short) 288, 64, null),

    GUNPOWDER("minecraft:gunpowder", (short) 289, 64, null),

    WOODEN_HOE("minecraft:wooden_hoe", (short) 290, 64, null),

    STONE_HOE("minecraft:stone_hoe", (short) 291, 64, null),

    IRON_HOE("minecraft:iron_hoe", (short) 292, 64, null),

    DIAMOND_HOE("minecraft:diamond_hoe", (short) 293, 64, null),

    GOLDEN_HOE("minecraft:golden_hoe", (short) 294, 64, null),

    WHEAT_SEEDS("minecraft:wheat_seeds", (short) 295, 64, null),

    WHEAT("minecraft:wheat", (short) 296, 64, Block.WHEAT),

    BREAD("minecraft:bread", (short) 297, 64, null),

    LEATHER_HELMET("minecraft:leather_helmet", (short) 298, 64, null),

    LEATHER_CHESTPLATE("minecraft:leather_chestplate", (short) 299, 64, null),

    LEATHER_LEGGINGS("minecraft:leather_leggings", (short) 300, 64, null),

    LEATHER_BOOTS("minecraft:leather_boots", (short) 301, 64, null),

    CHAINMAIL_HELMET("minecraft:chainmail_helmet", (short) 302, 64, null),

    CHAINMAIL_CHESTPLATE("minecraft:chainmail_chestplate", (short) 303, 64, null),

    CHAINMAIL_LEGGINGS("minecraft:chainmail_leggings", (short) 304, 64, null),

    CHAINMAIL_BOOTS("minecraft:chainmail_boots", (short) 305, 64, null),

    IRON_HELMET("minecraft:iron_helmet", (short) 306, 64, null),

    IRON_CHESTPLATE("minecraft:iron_chestplate", (short) 307, 64, null),

    IRON_LEGGINGS("minecraft:iron_leggings", (short) 308, 64, null),

    IRON_BOOTS("minecraft:iron_boots", (short) 309, 64, null),

    DIAMOND_HELMET("minecraft:diamond_helmet", (short) 310, 64, null),

    DIAMOND_CHESTPLATE("minecraft:diamond_chestplate", (short) 311, 64, null),

    DIAMOND_LEGGINGS("minecraft:diamond_leggings", (short) 312, 64, null),

    DIAMOND_BOOTS("minecraft:diamond_boots", (short) 313, 64, null),

    GOLDEN_HELMET("minecraft:golden_helmet", (short) 314, 64, null),

    GOLDEN_CHESTPLATE("minecraft:golden_chestplate", (short) 315, 64, null),

    GOLDEN_LEGGINGS("minecraft:golden_leggings", (short) 316, 64, null),

    GOLDEN_BOOTS("minecraft:golden_boots", (short) 317, 64, null),

    FLINT("minecraft:flint", (short) 318, 64, null),

    PORKCHOP("minecraft:porkchop", (short) 319, 64, null),

    COOKED_PORKCHOP("minecraft:cooked_porkchop", (short) 320, 64, null),

    PAINTING("minecraft:painting", (short) 321, 64, null),

    GOLDEN_APPLE("minecraft:golden_apple", (short) 322, 64, null),

    SIGN("minecraft:sign", (short) 323, 64, null),

    WOODEN_DOOR("minecraft:wooden_door", (short) 324, 64, Block.WOODEN_DOOR),

    BUCKET("minecraft:bucket", (short) 325, 16, null),

    WATER_BUCKET("minecraft:water_bucket", (short) 326, 64, null),

    LAVA_BUCKET("minecraft:lava_bucket", (short) 327, 64, null),

    MINECART("minecraft:minecart", (short) 328, 64, null),

    SADDLE("minecraft:saddle", (short) 329, 64, null),

    IRON_DOOR("minecraft:iron_door", (short) 330, 64, Block.IRON_DOOR),

    REDSTONE("minecraft:redstone", (short) 331, 64, Block.REDSTONE_WIRE),

    SNOWBALL("minecraft:snowball", (short) 332, 64, null),

    BOAT("minecraft:boat", (short) 333, 64, null),

    LEATHER("minecraft:leather", (short) 334, 64, null),

    MILK_BUCKET("minecraft:milk_bucket", (short) 335, 64, null),

    BRICK("minecraft:brick", (short) 336, 64, null),

    CLAY_BALL("minecraft:clay_ball", (short) 337, 64, null),

    REEDS("minecraft:reeds", (short) 338, 64, Block.REEDS),

    PAPER("minecraft:paper", (short) 339, 64, null),

    BOOK("minecraft:book", (short) 340, 64, null),

    SLIME_BALL("minecraft:slime_ball", (short) 341, 64, null),

    CHEST_MINECART("minecraft:chest_minecart", (short) 342, 64, null),

    FURNACE_MINECART("minecraft:furnace_minecart", (short) 343, 64, null),

    EGG("minecraft:egg", (short) 344, 64, null),

    COMPASS("minecraft:compass", (short) 345, 64, null),

    FISHING_ROD("minecraft:fishing_rod", (short) 346, 64, null),

    CLOCK("minecraft:clock", (short) 347, 64, null),

    GLOWSTONE_DUST("minecraft:glowstone_dust", (short) 348, 64, null),

    FISH("minecraft:fish", (short) 349, 64, null),

    COOKED_FISH("minecraft:cooked_fish", (short) 350, 64, null),

    DYE("minecraft:dye", (short) 351, 64, null),

    BONE("minecraft:bone", (short) 352, 64, null),

    SUGAR("minecraft:sugar", (short) 353, 64, null),

    CAKE("minecraft:cake", (short) 354, 1, Block.CAKE),

    BED("minecraft:bed", (short) 355, 1, Block.BED),

    REPEATER("minecraft:repeater", (short) 356, 64, Block.UNPOWERED_REPEATER),

    COOKIE("minecraft:cookie", (short) 357, 64, null),

    FILLED_MAP("minecraft:filled_map", (short) 358, 64, null),

    SHEARS("minecraft:shears", (short) 359, 64, null),

    MELON("minecraft:melon", (short) 360, 64, Block.MELON_BLOCK),

    PUMPKIN_SEEDS("minecraft:pumpkin_seeds", (short) 361, 64, null),

    MELON_SEEDS("minecraft:melon_seeds", (short) 362, 64, null),

    BEEF("minecraft:beef", (short) 363, 64, null),

    COOKED_BEEF("minecraft:cooked_beef", (short) 364, 64, null),

    CHICKEN("minecraft:chicken", (short) 365, 64, null),

    COOKED_CHICKEN("minecraft:cooked_chicken", (short) 366, 64, null),

    ROTTEN_FLESH("minecraft:rotten_flesh", (short) 367, 64, null),

    ENDER_PEARL("minecraft:ender_pearl", (short) 368, 64, null),

    BLAZE_ROD("minecraft:blaze_rod", (short) 369, 64, null),

    GHAST_TEAR("minecraft:ghast_tear", (short) 370, 64, null),

    GOLD_NUGGET("minecraft:gold_nugget", (short) 371, 64, null),

    NETHER_WART("minecraft:nether_wart", (short) 372, 64, Block.NETHER_WART),

    POTION("minecraft:potion", (short) 373, 64, null),

    GLASS_BOTTLE("minecraft:glass_bottle", (short) 374, 64, null),

    SPIDER_EYE("minecraft:spider_eye", (short) 375, 64, null),

    FERMENTED_SPIDER_EYE("minecraft:fermented_spider_eye", (short) 376, 64, null),

    BLAZE_POWDER("minecraft:blaze_powder", (short) 377, 64, null),

    MAGMA_CREAM("minecraft:magma_cream", (short) 378, 64, null),

    BREWING_STAND("minecraft:brewing_stand", (short) 379, 64, Block.BREWING_STAND),

    CAULDRON("minecraft:cauldron", (short) 380, 64, Block.CAULDRON),

    ENDER_EYE("minecraft:ender_eye", (short) 381, 64, null),

    SPECKLED_MELON("minecraft:speckled_melon", (short) 382, 64, null),

    SPAWN_EGG("minecraft:spawn_egg", (short) 383, 64, null),

    EXPERIENCE_BOTTLE("minecraft:experience_bottle", (short) 384, 64, null),

    FIRE_CHARGE("minecraft:fire_charge", (short) 385, 64, null),

    WRITABLE_BOOK("minecraft:writable_book", (short) 386, 64, null),

    WRITTEN_BOOK("minecraft:written_book", (short) 387, 16, null),

    EMERALD("minecraft:emerald", (short) 388, 64, null),

    ITEM_FRAME("minecraft:item_frame", (short) 389, 64, null),

    FLOWER_POT("minecraft:flower_pot", (short) 390, 64, Block.FLOWER_POT),

    CARROT("minecraft:carrot", (short) 391, 64, null),

    POTATO("minecraft:potato", (short) 392, 64, null),

    BAKED_POTATO("minecraft:baked_potato", (short) 393, 64, null),

    POISONOUS_POTATO("minecraft:poisonous_potato", (short) 394, 64, null),

    MAP("minecraft:map", (short) 395, 64, null),

    GOLDEN_CARROT("minecraft:golden_carrot", (short) 396, 64, null),

    SKULL("minecraft:skull", (short) 397, 64, Block.SKULL),

    CARROT_ON_A_STICK("minecraft:carrot_on_a_stick", (short) 398, 64, null),

    NETHER_STAR("minecraft:nether_star", (short) 399, 64, null),

    PUMPKIN_PIE("minecraft:pumpkin_pie", (short) 400, 64, null),

    FIREWORKS("minecraft:fireworks", (short) 401, 64, null),

    FIREWORK_CHARGE("minecraft:firework_charge", (short) 402, 64, null),

    ENCHANTED_BOOK("minecraft:enchanted_book", (short) 403, 1, null),

    COMPARATOR("minecraft:comparator", (short) 404, 64, Block.UNPOWERED_COMPARATOR),

    NETHERBRICK("minecraft:netherbrick", (short) 405, 64, null),

    QUARTZ("minecraft:quartz", (short) 406, 64, null),

    TNT_MINECART("minecraft:tnt_minecart", (short) 407, 64, null),

    HOPPER_MINECART("minecraft:hopper_minecart", (short) 408, 64, null),

    PRISMARINE_SHARD("minecraft:prismarine_shard", (short) 409, 64, null),

    PRISMARINE_CRYSTALS("minecraft:prismarine_crystals", (short) 410, 64, null),

    RABBIT("minecraft:rabbit", (short) 411, 64, null),

    COOKED_RABBIT("minecraft:cooked_rabbit", (short) 412, 64, null),

    RABBIT_STEW("minecraft:rabbit_stew", (short) 413, 64, null),

    RABBIT_FOOT("minecraft:rabbit_foot", (short) 414, 64, null),

    RABBIT_HIDE("minecraft:rabbit_hide", (short) 415, 64, null),

    ARMOR_STAND("minecraft:armor_stand", (short) 416, 16, null),

    IRON_HORSE_ARMOR("minecraft:iron_horse_armor", (short) 417, 1, null),

    GOLDEN_HORSE_ARMOR("minecraft:golden_horse_armor", (short) 418, 1, null),

    DIAMOND_HORSE_ARMOR("minecraft:diamond_horse_armor", (short) 419, 1, null),

    LEAD("minecraft:lead", (short) 420, 64, null),

    NAME_TAG("minecraft:name_tag", (short) 421, 64, null),

    COMMAND_BLOCK_MINECART("minecraft:command_block_minecart", (short) 422, 64, null),

    MUTTON("minecraft:mutton", (short) 423, 64, null),

    COOKED_MUTTON("minecraft:cooked_mutton", (short) 424, 64, null),

    BANNER("minecraft:banner", (short) 425, 64, null),

    SPRUCE_DOOR("minecraft:spruce_door", (short) 427, 64, Block.SPRUCE_DOOR),

    BIRCH_DOOR("minecraft:birch_door", (short) 428, 64, Block.BIRCH_DOOR),

    JUNGLE_DOOR("minecraft:jungle_door", (short) 429, 64, Block.JUNGLE_DOOR),

    ACACIA_DOOR("minecraft:acacia_door", (short) 430, 64, Block.ACACIA_DOOR),

    DARK_OAK_DOOR("minecraft:dark_oak_door", (short) 431, 64, Block.DARK_OAK_DOOR),

    RECORD_13("minecraft:record_13", (short) 2256, 64, null),

    RECORD_CAT("minecraft:record_cat", (short) 2257, 64, null),

    RECORD_BLOCKS("minecraft:record_blocks", (short) 2258, 64, null),

    RECORD_CHIRP("minecraft:record_chirp", (short) 2259, 64, null),

    RECORD_FAR("minecraft:record_far", (short) 2260, 64, null),

    RECORD_MALL("minecraft:record_mall", (short) 2261, 64, null),

    RECORD_MELLOHI("minecraft:record_mellohi", (short) 2262, 64, null),

    RECORD_STAL("minecraft:record_stal", (short) 2263, 64, null),

    RECORD_STRAD("minecraft:record_strad", (short) 2264, 64, null),

    RECORD_WARD("minecraft:record_ward", (short) 2265, 64, null),

    RECORD_11("minecraft:record_11", (short) 2266, 64, null),

    RECORD_WAIT("minecraft:record_wait", (short) 2267, 64, null);

    @NotNull
    private String namespaceID;

    private short id;

    private int maxDefaultStackSize;

    @Nullable
    private Block correspondingBlock;

    Material(@NotNull String namespaceID, short id, int maxDefaultStackSize,
            @Nullable Block correspondingBlock) {
        this.namespaceID = namespaceID;
        this.id = id;
        this.maxDefaultStackSize = maxDefaultStackSize;
        this.correspondingBlock = correspondingBlock;
        MaterialArray.materials[id] = this;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return namespaceID;
    }

    public int getMaxDefaultStackSize() {
        return maxDefaultStackSize;
    }

    public boolean isBlock() {
        return correspondingBlock != null && this != AIR;
    }

    public Block getBlock() {
        return correspondingBlock;
    }

    public static Material fromId(short id) {
        Material material = MaterialArray.materials[id];
        if(material != null) {
            return material;
        }
        return AIR;
    }

    public boolean isHelmet() {
        return toString().endsWith("HELMET");
    }

    public boolean isChestplate() {
        return toString().endsWith("CHESTPLATE");
    }

    public boolean isLeggings() {
        return toString().endsWith("LEGGINGS");
    }

    public boolean isBoots() {
        return toString().endsWith("BOOTS");
    }

    public boolean isArmor() {
        return isChestplate() || isHelmet() || isLeggings() || isBoots();
    }

    public boolean isFood() {
        return switch (this) {
            case APPLE, MUSHROOM_STEW, BREAD, PORKCHOP, COOKED_PORKCHOP, GOLDEN_APPLE, FISH, COOKED_FISH, CAKE, COOKIE, MELON, BEEF, COOKED_BEEF, CHICKEN, COOKED_CHICKEN, ROTTEN_FLESH, SPIDER_EYE, CARROT, POTATO, BAKED_POTATO, POISONOUS_POTATO, PUMPKIN_PIE, RABBIT, COOKED_RABBIT, RABBIT_STEW, MUTTON, COOKED_MUTTON ->
                    true;
            default -> false;
        };
    }

    public boolean hasState() {
        if (this == Material.BOW) {
            return true;
        }
        return isFood();
    }
}
