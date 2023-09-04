package net.tachyon.block;

import java.util.List;

import net.tachyon.namespace.NamespaceID;
import net.tachyon.block.states.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Block {
    AIR("minecraft:air", (short) 0, 0.0, 0.0, true, false, null, null, null),

    STONE("minecraft:stone", (short) 1, 1.5, 6.0, false, true, null, Stone.variations, Stone.variationsArray),

    GRASS("minecraft:grass", (short) 2, 0.6, 0.6, false, true, null, null, null),

    DIRT("minecraft:dirt", (short) 3, 0.5, 0.5, false, true, null, Dirt.variations, Dirt.variationsArray),

    COBBLESTONE("minecraft:cobblestone", (short) 4, 2.0, 6.0, false, true, null, null, null),

    PLANKS("minecraft:planks", (short) 5, 2.0, 3.0, false, true, null, Planks.variations, Planks.variationsArray),

    SAPLING("minecraft:sapling", (short) 6, 0.0, 0.0, false, false, null, null, null),

    BEDROCK("minecraft:bedrock", (short) 7, 0.0, 3600000.0, false, true, null, null, null),

    FLOWING_WATER("minecraft:flowing_water", (short) 8, 100.0, 100.0, false, false, null, null, null),

    WATER("minecraft:water", (short) 9, 100.0, 100.0, false, false, null, null, null),

    FLOWING_LAVA("minecraft:flowing_lava", (short) 10, 100.0, 100.0, false, false, null, null, null),

    LAVA("minecraft:lava", (short) 11, 100.0, 100.0, false, false, null, null, null),

    SAND("minecraft:sand", (short) 12, 0.5, 0.5, false, true, null, Sand.variations, Sand.variationsArray),

    GRAVEL("minecraft:gravel", (short) 13, 0.6, 0.6, false, true, null, null, null),

    GOLD_ORE("minecraft:gold_ore", (short) 14, 3.0, 3.0, false, true, null, null, null),

    IRON_ORE("minecraft:iron_ore", (short) 15, 3.0, 3.0, false, true, null, null, null),

    COAL_ORE("minecraft:coal_ore", (short) 16, 3.0, 3.0, false, true, null, null, null),

    LOG("minecraft:log", (short) 17, 2.0, 0.0, false, true, null, Log.variations, Log.variationsArray),

    LEAVES("minecraft:leaves", (short) 18, 0.2, 0.0, false, true, null, Leaves.variations, Leaves.variationsArray),

    SPONGE("minecraft:sponge", (short) 19, 0.6, 0.6, false, true, null, Sponge.variations, Sponge.variationsArray),

    GLASS("minecraft:glass", (short) 20, 0.3, 0.3, false, true, null, null, null),

    LAPIS_ORE("minecraft:lapis_ore", (short) 21, 3.0, 3.0, false, true, null, null, null),

    LAPIS_BLOCK("minecraft:lapis_block", (short) 22, 3.0, 3.0, false, true, null, null, null),

    DISPENSER("minecraft:dispenser", (short) 23, 3.5, 3.5, false, true, NamespaceID.from("minecraft:Trap"), null, null),

    SANDSTONE("minecraft:sandstone", (short) 24, 0.8, 0.8, false, true, null, Sandstone.variations, Sandstone.variationsArray),

    NOTEBLOCK("minecraft:noteblock", (short) 25, 0.8, 0.8, false, true, NamespaceID.from("minecraft:Music"), null, null),

    BED("minecraft:bed", (short) 26, 0.2, 0.2, false, true, null, null, null),

    GOLDEN_RAIL("minecraft:golden_rail", (short) 27, 0.7, 0.7, false, false, null, null, null),

    DETECTOR_RAIL("minecraft:detector_rail", (short) 28, 0.7, 0.7, false, false, null, null, null),

    STICKY_PISTON("minecraft:sticky_piston", (short) 29, 0.5, 0.0, false, true, null, null, null),

    WEB("minecraft:web", (short) 30, 4.0, 4.0, false, false, null, null, null),

    TALLGRASS("minecraft:tallgrass", (short) 31, 0.0, 0.0, false, false, null, Tallgrass.variations, Tallgrass.variationsArray),

    DEADBUSH("minecraft:deadbush", (short) 32, 0.0, 0.0, false, false, null, null, null),

    PISTON("minecraft:piston", (short) 33, 0.5, 0.0, false, true, null, null, null),

    PISTON_HEAD("minecraft:piston_head", (short) 34, 0.5, 0.0, false, true, null, null, null),

    WOOL("minecraft:wool", (short) 35, 0.8, 0.8, false, true, null, Wool.variations, Wool.variationsArray),

    PISTON_EXTENSION("minecraft:piston_extension", (short) 36, 0.0, 0.0, false, false, null, null, null),

    YELLOW_FLOWER("minecraft:yellow_flower", (short) 37, 0.0, 0.0, false, false, null, null, null),

    RED_FLOWER("minecraft:red_flower", (short) 38, 0.0, 0.0, false, false, null, RedFlower.variations, RedFlower.variationsArray),

    BROWN_MUSHROOM("minecraft:brown_mushroom", (short) 39, 0.0, 0.0, false, false, null, null, null),

    RED_MUSHROOM("minecraft:red_mushroom", (short) 40, 0.0, 0.0, false, false, null, null, null),

    GOLD_BLOCK("minecraft:gold_block", (short) 41, 3.0, 6.0, false, true, null, null, null),

    IRON_BLOCK("minecraft:iron_block", (short) 42, 5.0, 6.0, false, true, null, null, null),

    DOUBLE_STONE_SLAB("minecraft:double_stone_slab", (short) 43, 2.0, 6.0, false, true, null, DoubleStoneSlab.variations, DoubleStoneSlab.variationsArray),

    STONE_SLAB("minecraft:stone_slab", (short) 44, 2.0, 6.0, false, true, null, StoneSlab.variations, StoneSlab.variationsArray),

    BRICK_BLOCK("minecraft:brick_block", (short) 45, 2.0, 6.0, false, true, null, null, null),

    TNT("minecraft:tnt", (short) 46, 0.0, 0.0, false, true, null, Tnt.variations, Tnt.variationsArray),

    BOOKSHELF("minecraft:bookshelf", (short) 47, 1.5, 1.5, false, true, null, null, null),

    MOSSY_COBBLESTONE("minecraft:mossy_cobblestone", (short) 48, 2.0, 6.0, false, true, null, null, null),

    OBSIDIAN("minecraft:obsidian", (short) 49, 50.0, 1200.0, false, true, null, null, null),

    TORCH("minecraft:torch", (short) 50, 0.0, 0.0, false, false, null, Torch.variations, Torch.variationsArray),

    FIRE("minecraft:fire", (short) 51, 0.0, 0.0, false, false, null, null, null),

    MOB_SPAWNER("minecraft:mob_spawner", (short) 52, 5.0, 5.0, false, true, NamespaceID.from("minecraft:MobSpawner"), null, null),

    OAK_STAIRS("minecraft:oak_stairs", (short) 53, 2.0, 0.0, false, true, null, null, null),

    CHEST("minecraft:chest", (short) 54, 2.5, 2.5, false, true, NamespaceID.from("minecraft:Chest"), null, null),

    REDSTONE_WIRE("minecraft:redstone_wire", (short) 55, 0.0, 0.0, false, false, null, null, null),

    DIAMOND_ORE("minecraft:diamond_ore", (short) 56, 3.0, 3.0, false, true, null, null, null),

    DIAMOND_BLOCK("minecraft:diamond_block", (short) 57, 5.0, 6.0, false, true, null, null, null),

    CRAFTING_TABLE("minecraft:crafting_table", (short) 58, 2.5, 2.5, false, true, null, null, null),

    WHEAT("minecraft:wheat", (short) 59, 0.0, 0.0, false, false, null, null, null),

    FARMLAND("minecraft:farmland", (short) 60, 0.6, 0.6, false, true, null, null, null),

    FURNACE("minecraft:furnace", (short) 61, 3.5, 3.5, false, true, NamespaceID.from("minecraft:Furnace"), null, null),

    LIT_FURNACE("minecraft:lit_furnace", (short) 62, 3.5, 3.5, false, true, NamespaceID.from("minecraft:Furnace"), null, null),

    STANDING_SIGN("minecraft:standing_sign", (short) 63, 1.0, 1.0, false, false, NamespaceID.from("minecraft:Sign"), null, null),

    WOODEN_DOOR("minecraft:wooden_door", (short) 64, 3.0, 3.0, false, true, null, null, null),

    LADDER("minecraft:ladder", (short) 65, 0.4, 0.4, false, true, null, null, null),

    RAIL("minecraft:rail", (short) 66, 0.7, 0.7, false, false, null, null, null),

    STONE_STAIRS("minecraft:stone_stairs", (short) 67, 2.0, 0.0, false, true, null, null, null),

    WALL_SIGN("minecraft:wall_sign", (short) 68, 1.0, 1.0, false, false, NamespaceID.from("minecraft:Sign"), null, null),

    LEVER("minecraft:lever", (short) 69, 0.5, 0.5, false, false, null, null, null),

    STONE_PRESSURE_PLATE("minecraft:stone_pressure_plate", (short) 70, 0.5, 0.5, false, false, null, null, null),

    IRON_DOOR("minecraft:iron_door", (short) 71, 5.0, 5.0, false, true, null, null, null),

    WOODEN_PRESSURE_PLATE("minecraft:wooden_pressure_plate", (short) 72, 0.5, 0.5, false, false, null, null, null),

    REDSTONE_ORE("minecraft:redstone_ore", (short) 73, 3.0, 3.0, false, true, null, null, null),

    LIT_REDSTONE_ORE("minecraft:lit_redstone_ore", (short) 74, 3.0, 3.0, false, true, null, null, null),

    UNLIT_REDSTONE_TORCH("minecraft:unlit_redstone_torch", (short) 75, 0.0, 0.0, false, false, null, UnlitRedstoneTorch.variations, UnlitRedstoneTorch.variationsArray),

    REDSTONE_TORCH("minecraft:redstone_torch", (short) 76, 0.0, 0.0, false, false, null, RedstoneTorch.variations, RedstoneTorch.variationsArray),

    STONE_BUTTON("minecraft:stone_button", (short) 77, 0.5, 0.5, false, false, null, null, null),

    SNOW_LAYER("minecraft:snow_layer", (short) 78, 0.2, 0.1, false, true, null, SnowLayer.variations, SnowLayer.variationsArray),

    ICE("minecraft:ice", (short) 79, 0.5, 0.5, false, true, null, null, null),

    SNOW("minecraft:snow", (short) 80, 0.2, 0.2, false, true, null, null, null),

    CACTUS("minecraft:cactus", (short) 81, 0.4, 0.4, false, true, null, null, null),

    CLAY("minecraft:clay", (short) 82, 0.6, 0.6, false, true, null, null, null),

    REEDS("minecraft:reeds", (short) 83, 0.0, 0.0, false, false, null, null, null),

    JUKEBOX("minecraft:jukebox", (short) 84, 2.0, 6.0, false, true, NamespaceID.from("minecraft:RecordPlayer"), Jukebox.variations, Jukebox.variationsArray),

    FENCE("minecraft:fence", (short) 85, 2.0, 3.0, false, true, null, null, null),

    PUMPKIN("minecraft:pumpkin", (short) 86, 1.0, 1.0, false, true, null, null, null),

    NETHERRACK("minecraft:netherrack", (short) 87, 0.4, 0.4, false, true, null, null, null),

    SOUL_SAND("minecraft:soul_sand", (short) 88, 0.5, 0.5, false, true, null, null, null),

    GLOWSTONE("minecraft:glowstone", (short) 89, 0.3, 0.3, false, true, null, null, null),

    PORTAL("minecraft:portal", (short) 90, 0.0, -1.0, false, false, null, null, null),

    LIT_PUMPKIN("minecraft:lit_pumpkin", (short) 91, 1.0, 1.0, false, true, null, null, null),

    CAKE("minecraft:cake", (short) 92, 0.5, 0.5, false, true, null, null, null),

    UNPOWERED_REPEATER("minecraft:unpowered_repeater", (short) 93, 0.0, 0.0, false, true, null, null, null),

    POWERED_REPEATER("minecraft:powered_repeater", (short) 94, 0.0, 0.0, false, true, null, null, null),

    STAINED_GLASS("minecraft:stained_glass", (short) 95, 0.3, 0.3, false, true, null, null, null),

    TRAPDOOR("minecraft:trapdoor", (short) 96, 3.0, 3.0, false, true, null, null, null),

    MONSTER_EGG("minecraft:monster_egg", (short) 97, 0.75, 0.75, false, true, null, MonsterEgg.variations, MonsterEgg.variationsArray),

    STONEBRICK("minecraft:stonebrick", (short) 98, 1.5, 6.0, false, true, null, Stonebrick.variations, Stonebrick.variationsArray),

    BROWN_MUSHROOM_BLOCK("minecraft:brown_mushroom_block", (short) 99, 0.0, 0.2, false, true, null, BrownMushroomBlock.variations, BrownMushroomBlock.variationsArray),

    RED_MUSHROOM_BLOCK("minecraft:red_mushroom_block", (short) 100, 0.0, 0.2, false, true, null, RedMushroomBlock.variations, RedMushroomBlock.variationsArray),

    IRON_BARS("minecraft:iron_bars", (short) 101, 5.0, 6.0, false, true, null, null, null),

    GLASS_PANE("minecraft:glass_pane", (short) 102, 0.3, 0.3, false, true, null, null, null),

    MELON_BLOCK("minecraft:melon_block", (short) 103, 1.0, 1.0, false, true, null, null, null),

    PUMPKIN_STEM("minecraft:pumpkin_stem", (short) 104, 0.0, 0.0, false, false, null, null, null),

    MELON_STEM("minecraft:melon_stem", (short) 105, 0.0, 0.0, false, false, null, null, null),

    VINE("minecraft:vine", (short) 106, 0.2, 0.2, false, false, null, null, null),

    FENCE_GATE("minecraft:fence_gate", (short) 107, 2.0, 3.0, false, true, null, null, null),

    BRICK_STAIRS("minecraft:brick_stairs", (short) 108, 2.0, 0.0, false, true, null, null, null),

    STONE_BRICK_STAIRS("minecraft:stone_brick_stairs", (short) 109, 1.5, 0.0, false, true, null, null, null),

    MYCELIUM("minecraft:mycelium", (short) 110, 0.6, 0.6, false, true, null, null, null),

    WATERLILY("minecraft:waterlily", (short) 111, 0.6, 0.0, false, true, null, null, null),

    NETHER_BRICK("minecraft:nether_brick", (short) 112, 2.0, 6.0, false, true, null, null, null),

    NETHER_BRICK_FENCE("minecraft:nether_brick_fence", (short) 113, 2.0, 6.0, false, true, null, null, null),

    NETHER_BRICK_STAIRS("minecraft:nether_brick_stairs", (short) 114, 2.0, 0.0, false, true, null, null, null),

    NETHER_WART("minecraft:nether_wart", (short) 115, 0.0, 0.0, false, false, null, null, null),

    ENCHANTING_TABLE("minecraft:enchanting_table", (short) 116, 5.0, 1200.0, false, true, NamespaceID.from("minecraft:EnchantTable"), null, null),

    BREWING_STAND("minecraft:brewing_stand", (short) 117, 0.5, 0.5, false, true, NamespaceID.from("minecraft:Cauldron"), null, null),

    CAULDRON("minecraft:cauldron", (short) 118, 2.0, 2.0, false, true, null, null, null),

    END_PORTAL("minecraft:end_portal", (short) 119, 0.0, 3600000.0, false, false, NamespaceID.from("minecraft:Airportal"), null, null),

    END_PORTAL_FRAME("minecraft:end_portal_frame", (short) 120, 0.0, 3600000.0, false, true, null, null, null),

    END_STONE("minecraft:end_stone", (short) 121, 3.0, 9.0, false, true, null, null, null),

    DRAGON_EGG("minecraft:dragon_egg", (short) 122, 3.0, 9.0, false, true, null, null, null),

    REDSTONE_LAMP("minecraft:redstone_lamp", (short) 123, 0.3, 0.3, false, true, null, null, null),

    LIT_REDSTONE_LAMP("minecraft:lit_redstone_lamp", (short) 124, 0.3, 0.3, false, true, null, null, null),

    DOUBLE_WOODEN_SLAB("minecraft:double_wooden_slab", (short) 125, 2.0, 3.0, false, true, null, DoubleWoodenSlab.variations, DoubleWoodenSlab.variationsArray),

    WOODEN_SLAB("minecraft:wooden_slab", (short) 126, 2.0, 3.0, false, true, null, WoodenSlab.variations, WoodenSlab.variationsArray),

    COCOA("minecraft:cocoa", (short) 127, 0.2, 3.0, false, true, null, null, null),

    SANDSTONE_STAIRS("minecraft:sandstone_stairs", (short) 128, 0.8, 0.0, false, true, null, null, null),

    EMERALD_ORE("minecraft:emerald_ore", (short) 129, 3.0, 3.0, false, true, null, null, null),

    ENDER_CHEST("minecraft:ender_chest", (short) 130, 22.5, 600.0, false, true, NamespaceID.from("minecraft:EnderChest"), null, null),

    TRIPWIRE_HOOK("minecraft:tripwire_hook", (short) 131, 0.0, 0.0, false, false, null, null, null),

    TRIPWIRE("minecraft:tripwire", (short) 132, 0.0, 0.0, false, false, null, null, null),

    EMERALD_BLOCK("minecraft:emerald_block", (short) 133, 5.0, 6.0, false, true, null, null, null),

    SPRUCE_STAIRS("minecraft:spruce_stairs", (short) 134, 2.0, 0.0, false, true, null, null, null),

    BIRCH_STAIRS("minecraft:birch_stairs", (short) 135, 2.0, 0.0, false, true, null, null, null),

    JUNGLE_STAIRS("minecraft:jungle_stairs", (short) 136, 2.0, 0.0, false, true, null, null, null),

    COMMAND_BLOCK("minecraft:command_block", (short) 137, 0.0, 3600000.0, false, true, NamespaceID.from("minecraft:Control"), null, null),

    BEACON("minecraft:beacon", (short) 138, 3.0, 0.0, false, true, NamespaceID.from("minecraft:Beacon"), null, null),

    COBBLESTONE_WALL("minecraft:cobblestone_wall", (short) 139, 2.0, 0.0, false, true, null, CobblestoneWall.variations, CobblestoneWall.variationsArray),

    FLOWER_POT("minecraft:flower_pot", (short) 140, 0.0, 0.0, false, true, NamespaceID.from("minecraft:FlowerPot"), FlowerPot.variations, FlowerPot.variationsArray),

    CARROTS("minecraft:carrots", (short) 141, 0.0, 0.0, false, false, null, null, null),

    POTATOES("minecraft:potatoes", (short) 142, 0.0, 0.0, false, false, null, null, null),

    WOODEN_BUTTON("minecraft:wooden_button", (short) 143, 0.5, 0.5, false, false, null, null, null),

    SKULL("minecraft:skull", (short) 144, 1.0, 1.0, false, true, NamespaceID.from("minecraft:Skull"), Skull.variations, Skull.variationsArray),

    ANVIL("minecraft:anvil", (short) 145, 5.0, 1200.0, false, true, null, Anvil.variations, Anvil.variationsArray),

    TRAPPED_CHEST("minecraft:trapped_chest", (short) 146, 2.5, 2.5, false, true, NamespaceID.from("minecraft:Chest"), null, null),

    LIGHT_WEIGHTED_PRESSURE_PLATE("minecraft:light_weighted_pressure_plate", (short) 147, 0.5, 0.5, false, false, null, null, null),

    HEAVY_WEIGHTED_PRESSURE_PLATE("minecraft:heavy_weighted_pressure_plate", (short) 148, 0.5, 0.5, false, false, null, null, null),

    UNPOWERED_COMPARATOR("minecraft:unpowered_comparator", (short) 149, 0.0, 0.0, false, true, NamespaceID.from("minecraft:Comparator"), null, null),

    POWERED_COMPARATOR("minecraft:powered_comparator", (short) 150, 0.0, 0.0, false, true, NamespaceID.from("minecraft:Comparator"), null, null),

    DAYLIGHT_DETECTOR("minecraft:daylight_detector", (short) 151, 0.2, 0.0, false, true, NamespaceID.from("minecraft:DLDetector"), null, null),

    REDSTONE_BLOCK("minecraft:redstone_block", (short) 152, 5.0, 6.0, false, true, null, null, null),

    QUARTZ_ORE("minecraft:quartz_ore", (short) 153, 3.0, 3.0, false, true, null, null, null),

    HOPPER("minecraft:hopper", (short) 154, 3.0, 4.8, false, true, NamespaceID.from("minecraft:Hopper"), null, null),

    QUARTZ_BLOCK("minecraft:quartz_block", (short) 155, 0.8, 0.8, false, true, null, QuartzBlock.variations, QuartzBlock.variationsArray),

    QUARTZ_STAIRS("minecraft:quartz_stairs", (short) 156, 0.8, 0.0, false, true, null, null, null),

    ACTIVATOR_RAIL("minecraft:activator_rail", (short) 157, 0.7, 0.7, false, false, null, null, null),

    DROPPER("minecraft:dropper", (short) 158, 3.5, 3.5, false, true, NamespaceID.from("minecraft:Dropper"), null, null),

    STAINED_HARDENED_CLAY("minecraft:stained_hardened_clay", (short) 159, 1.25, 4.2, false, true, null, null, null),

    STAINED_GLASS_PANE("minecraft:stained_glass_pane", (short) 160, 0.3, 0.3, false, true, null, null, null),

    LEAVES2("minecraft:leaves2", (short) 161, 0.2, 0.0, false, true, null, Leaves2.variations, Leaves2.variationsArray),

    LOG2("minecraft:log2", (short) 162, 2.0, 0.0, false, true, null, Log2.variations, Log2.variationsArray),

    ACACIA_STAIRS("minecraft:acacia_stairs", (short) 163, 2.0, 0.0, false, true, null, null, null),

    DARK_OAK_STAIRS("minecraft:dark_oak_stairs", (short) 164, 2.0, 0.0, false, true, null, null, null),

    SLIME("minecraft:slime", (short) 165, 0.0, 0.0, false, true, null, null, null),

    BARRIER("minecraft:barrier", (short) 166, 0.0, 0.0, false, true, null, null, null),

    IRON_TRAPDOOR("minecraft:iron_trapdoor", (short) 167, 5.0, 5.0, false, true, null, null, null),

    PRISMARINE("minecraft:prismarine", (short) 168, 1.5, 6.0, false, true, null, Prismarine.variations, Prismarine.variationsArray),

    SEA_LANTERN("minecraft:sea_lantern", (short) 169, 0.3, 0.3, false, true, null, null, null),

    HAY_BLOCK("minecraft:hay_block", (short) 170, 0.5, 0.5, false, true, null, null, null),

    CARPET("minecraft:carpet", (short) 171, 0.1, 0.1, false, true, null, Carpet.variations, Carpet.variationsArray),

    HARDENED_CLAY("minecraft:hardened_clay", (short) 172, 1.25, 4.2, false, true, null, null, null),

    COAL_BLOCK("minecraft:coal_block", (short) 173, 5.0, 6.0, false, true, null, null, null),

    PACKED_ICE("minecraft:packed_ice", (short) 174, 0.5, 0.5, false, true, null, null, null),

    DOUBLE_PLANT("minecraft:double_plant", (short) 175, 0.0, 0.0, false, false, null, DoublePlant.variations, DoublePlant.variationsArray),

    STANDING_BANNER("minecraft:standing_banner", (short) 176, 1.0, 1.0, false, false, NamespaceID.from("minecraft:Banner"), null, null),

    WALL_BANNER("minecraft:wall_banner", (short) 177, 1.0, 1.0, false, false, NamespaceID.from("minecraft:Banner"), null, null),

    DAYLIGHT_DETECTOR_INVERTED("minecraft:daylight_detector_inverted", (short) 178, 0.2, 0.0, false, true, NamespaceID.from("minecraft:DLDetector"), null, null),

    RED_SANDSTONE("minecraft:red_sandstone", (short) 179, 0.8, 0.8, false, true, null, RedSandstone.variations, RedSandstone.variationsArray),

    RED_SANDSTONE_STAIRS("minecraft:red_sandstone_stairs", (short) 180, 0.8, 0.0, false, true, null, null, null),

    DOUBLE_STONE_SLAB2("minecraft:double_stone_slab2", (short) 181, 2.0, 6.0, false, true, null, DoubleStoneSlab2.variations, DoubleStoneSlab2.variationsArray),

    STONE_SLAB2("minecraft:stone_slab2", (short) 182, 2.0, 6.0, false, true, null, StoneSlab2.variations, StoneSlab2.variationsArray),

    SPRUCE_FENCE_GATE("minecraft:spruce_fence_gate", (short) 183, 2.0, 3.0, false, true, null, null, null),

    BIRCH_FENCE_GATE("minecraft:birch_fence_gate", (short) 184, 2.0, 3.0, false, true, null, null, null),

    JUNGLE_FENCE_GATE("minecraft:jungle_fence_gate", (short) 185, 2.0, 3.0, false, true, null, null, null),

    DARK_OAK_FENCE_GATE("minecraft:dark_oak_fence_gate", (short) 186, 2.0, 3.0, false, true, null, null, null),

    ACACIA_FENCE_GATE("minecraft:acacia_fence_gate", (short) 187, 2.0, 3.0, false, true, null, null, null),

    SPRUCE_FENCE("minecraft:spruce_fence", (short) 188, 2.0, 3.0, false, true, null, null, null),

    BIRCH_FENCE("minecraft:birch_fence", (short) 189, 2.0, 3.0, false, true, null, null, null),

    JUNGLE_FENCE("minecraft:jungle_fence", (short) 190, 2.0, 3.0, false, true, null, null, null),

    DARK_OAK_FENCE("minecraft:dark_oak_fence", (short) 191, 2.0, 3.0, false, true, null, null, null),

    ACACIA_FENCE("minecraft:acacia_fence", (short) 192, 2.0, 3.0, false, true, null, null, null),

    SPRUCE_DOOR("minecraft:spruce_door", (short) 193, 3.0, 3.0, false, true, null, null, null),

    BIRCH_DOOR("minecraft:birch_door", (short) 194, 3.0, 3.0, false, true, null, null, null),

    JUNGLE_DOOR("minecraft:jungle_door", (short) 195, 3.0, 3.0, false, true, null, null, null),

    ACACIA_DOOR("minecraft:acacia_door", (short) 196, 3.0, 3.0, false, true, null, null, null),

    DARK_OAK_DOOR("minecraft:dark_oak_door", (short) 197, 3.0, 3.0, false, true, null, null, null);

    @NotNull
    private String namespaceID;

    private short id;

    private double hardness;

    private double resistance;

    private boolean isAir;

    private boolean isSolid;

    @Nullable
    private final NamespaceID blockEntity;

    @Nullable
    private final List<BlockVariation> variations;

    @Nullable
    private BlockVariation[] variationsArray;

    Block(@NotNull String namespaceID, short id, double hardness, double resistance, boolean isAir,
            boolean isSolid, @Nullable NamespaceID blockEntity,
            @Nullable List<BlockVariation> variations, @Nullable BlockVariation[] variationsArray) {
        this.namespaceID = namespaceID;
        this.id = id;
        this.hardness = hardness;
        this.resistance = resistance;
        this.isAir = isAir;
        this.isSolid = isSolid;
        this.blockEntity = blockEntity;
        this.variations = variations;
        this.variationsArray = variationsArray;
        BlockArray.blocks[id] = this;
    }

    public short getBlockId() {
        return id;
    }

    public String getName() {
        return namespaceID;
    }

    public boolean isAir() {
        return isAir;
    }

    public boolean hasBlockEntity() {
        return blockEntity != null;
    }

    public NamespaceID getBlockEntityName() {
        return blockEntity;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isLiquid() {
        return this == WATER || this == LAVA;
    }

    public double getHardness() {
        return hardness;
    }

    public double getResistance() {
        return resistance;
    }

    public boolean breaksInstantaneously() {
        return hardness == 0;
    }

    public BlockVariation getVariation(byte metadata) {
        if(metadata < 0 || metadata > 15 || variations == null) {
            return null;
        }
        return variationsArray[metadata];
    }

    @Nullable
    public List<BlockVariation> getVariations() {
        return variations;
    }

    public short toStateId(byte metadata) {
        return (short) ((id << 4) | (metadata & 15));
    }

    public static Block fromStateId(short blockStateId) {
        return BlockArray.blocks[blockStateId >> 4];
    }

    public static byte toMetadata(short blockStateId) {
        return (byte) (blockStateId & 0x0F);
    }
}
