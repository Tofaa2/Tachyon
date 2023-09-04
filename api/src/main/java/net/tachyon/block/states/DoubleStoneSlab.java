package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class DoubleStoneSlab {
    public static BlockVariation DOUBLE_STONE_SLAB = new BlockVariation((byte) 0, "Double Stone Slab");

    public static BlockVariation DOUBLE_SANDSTONE_SLAB = new BlockVariation((byte) 1, "Double Sandstone Slab");

    public static BlockVariation DOUBLE_STONE_WOODEN_SLAB = new BlockVariation((byte) 2, "Double (Stone) Wooden Slab");

    public static BlockVariation DOUBLE_COBBLESTONE_SLAB = new BlockVariation((byte) 3, "Double Cobblestone Slab");

    public static BlockVariation DOUBLE_BRICKS_SLAB = new BlockVariation((byte) 4, "Double Bricks Slab");

    public static BlockVariation DOUBLE_STONE_BRICK_SLAB = new BlockVariation((byte) 5, "Double Stone Brick Slab");

    public static BlockVariation DOUBLE_NETHER_BRICK_SLAB = new BlockVariation((byte) 6, "Double Nether Brick Slab");

    public static BlockVariation DOUBLE_QUARTZ_SLAB = new BlockVariation((byte) 7, "Double Quartz Slab");

    public static BlockVariation SMOOTH_DOUBLE_STONE_SLAB = new BlockVariation((byte) 8, "Smooth Double Stone Slab");

    public static BlockVariation SMOOTH_DOUBLE_SANDSTONE_SLAB = new BlockVariation((byte) 9, "Smooth Double Sandstone Slab");

    public static BlockVariation TILE_DOUBLE_QUARTZ_SLAB_NOTE_THE_UNDERSIDE = new BlockVariation((byte) 15, "Tile Double Quartz Slab (note the underside)");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(11);
        list.add(DOUBLE_STONE_SLAB);
        variationsArray[0]= DOUBLE_STONE_SLAB;
        list.add(DOUBLE_SANDSTONE_SLAB);
        variationsArray[1]= DOUBLE_SANDSTONE_SLAB;
        list.add(DOUBLE_STONE_WOODEN_SLAB);
        variationsArray[2]= DOUBLE_STONE_WOODEN_SLAB;
        list.add(DOUBLE_COBBLESTONE_SLAB);
        variationsArray[3]= DOUBLE_COBBLESTONE_SLAB;
        list.add(DOUBLE_BRICKS_SLAB);
        variationsArray[4]= DOUBLE_BRICKS_SLAB;
        list.add(DOUBLE_STONE_BRICK_SLAB);
        variationsArray[5]= DOUBLE_STONE_BRICK_SLAB;
        list.add(DOUBLE_NETHER_BRICK_SLAB);
        variationsArray[6]= DOUBLE_NETHER_BRICK_SLAB;
        list.add(DOUBLE_QUARTZ_SLAB);
        variationsArray[7]= DOUBLE_QUARTZ_SLAB;
        list.add(SMOOTH_DOUBLE_STONE_SLAB);
        variationsArray[8]= SMOOTH_DOUBLE_STONE_SLAB;
        list.add(SMOOTH_DOUBLE_SANDSTONE_SLAB);
        variationsArray[9]= SMOOTH_DOUBLE_SANDSTONE_SLAB;
        list.add(TILE_DOUBLE_QUARTZ_SLAB_NOTE_THE_UNDERSIDE);
        variationsArray[15]= TILE_DOUBLE_QUARTZ_SLAB_NOTE_THE_UNDERSIDE;
        variations = Collections.unmodifiableList(list);
    }
}
