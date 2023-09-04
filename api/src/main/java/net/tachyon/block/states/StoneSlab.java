package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class StoneSlab {
    public static BlockVariation STONE_SLAB = new BlockVariation((byte) 0, "Stone Slab");

    public static BlockVariation SANDSTONE_SLAB = new BlockVariation((byte) 1, "Sandstone Slab");

    public static BlockVariation STONE_WOODEN_SLAB = new BlockVariation((byte) 2, "(Stone) Wooden Slab");

    public static BlockVariation COBBLESTONE_SLAB = new BlockVariation((byte) 3, "Cobblestone Slab");

    public static BlockVariation BRICKS_SLAB = new BlockVariation((byte) 4, "Bricks Slab");

    public static BlockVariation STONE_BRICK_SLAB = new BlockVariation((byte) 5, "Stone Brick Slab");

    public static BlockVariation NETHER_BRICK_SLAB = new BlockVariation((byte) 6, "Nether Brick Slab");

    public static BlockVariation QUARTZ_SLAB = new BlockVariation((byte) 7, "Quartz Slab");

    public static BlockVariation UPPER_STONE_SLAB = new BlockVariation((byte) 8, "Upper Stone Slab");

    public static BlockVariation UPPER_SANDSTONE_SLAB = new BlockVariation((byte) 9, "Upper Sandstone Slab");

    public static BlockVariation UPPER_STONE_WOODEN_SLAB = new BlockVariation((byte) 10, "Upper (Stone) Wooden Slab");

    public static BlockVariation UPPER_COBBLESTONE_SLAB = new BlockVariation((byte) 11, "Upper Cobblestone Slab");

    public static BlockVariation UPPER_BRICKS_SLAB = new BlockVariation((byte) 12, "Upper Bricks Slab");

    public static BlockVariation UPPER_STONE_BRICK_SLAB = new BlockVariation((byte) 13, "Upper Stone Brick Slab");

    public static BlockVariation UPPER_NETHER_BRICK_SLAB = new BlockVariation((byte) 14, "Upper Nether Brick Slab");

    public static BlockVariation UPPER_QUARTZ_SLAB = new BlockVariation((byte) 15, "Upper Quartz Slab");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(16);
        list.add(STONE_SLAB);
        variationsArray[0]= STONE_SLAB;
        list.add(SANDSTONE_SLAB);
        variationsArray[1]= SANDSTONE_SLAB;
        list.add(STONE_WOODEN_SLAB);
        variationsArray[2]= STONE_WOODEN_SLAB;
        list.add(COBBLESTONE_SLAB);
        variationsArray[3]= COBBLESTONE_SLAB;
        list.add(BRICKS_SLAB);
        variationsArray[4]= BRICKS_SLAB;
        list.add(STONE_BRICK_SLAB);
        variationsArray[5]= STONE_BRICK_SLAB;
        list.add(NETHER_BRICK_SLAB);
        variationsArray[6]= NETHER_BRICK_SLAB;
        list.add(QUARTZ_SLAB);
        variationsArray[7]= QUARTZ_SLAB;
        list.add(UPPER_STONE_SLAB);
        variationsArray[8]= UPPER_STONE_SLAB;
        list.add(UPPER_SANDSTONE_SLAB);
        variationsArray[9]= UPPER_SANDSTONE_SLAB;
        list.add(UPPER_STONE_WOODEN_SLAB);
        variationsArray[10]= UPPER_STONE_WOODEN_SLAB;
        list.add(UPPER_COBBLESTONE_SLAB);
        variationsArray[11]= UPPER_COBBLESTONE_SLAB;
        list.add(UPPER_BRICKS_SLAB);
        variationsArray[12]= UPPER_BRICKS_SLAB;
        list.add(UPPER_STONE_BRICK_SLAB);
        variationsArray[13]= UPPER_STONE_BRICK_SLAB;
        list.add(UPPER_NETHER_BRICK_SLAB);
        variationsArray[14]= UPPER_NETHER_BRICK_SLAB;
        list.add(UPPER_QUARTZ_SLAB);
        variationsArray[15]= UPPER_QUARTZ_SLAB;
        variations = Collections.unmodifiableList(list);
    }
}
