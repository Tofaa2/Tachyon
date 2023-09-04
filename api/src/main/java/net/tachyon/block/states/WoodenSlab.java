package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class WoodenSlab {
    public static BlockVariation OAK_WOOD_SLAB = new BlockVariation((byte) 0, "Oak Wood Slab");

    public static BlockVariation SPRUCE_WOOD_SLAB = new BlockVariation((byte) 1, "Spruce Wood Slab");

    public static BlockVariation BIRCH_WOOD_SLAB = new BlockVariation((byte) 2, "Birch Wood Slab");

    public static BlockVariation JUNGLE_WOOD_SLAB = new BlockVariation((byte) 3, "Jungle Wood Slab");

    public static BlockVariation ACACIA_WOOD_SLAB = new BlockVariation((byte) 4, "Acacia Wood Slab");

    public static BlockVariation DARK_OAK_WOOD_SLAB = new BlockVariation((byte) 5, "Dark Oak Wood Slab");

    public static BlockVariation UPPER_OAK_WOOD_SLAB = new BlockVariation((byte) 8, "Upper Oak Wood Slab");

    public static BlockVariation UPPER_SPRUCE_WOOD_SLAB = new BlockVariation((byte) 9, "Upper Spruce Wood Slab");

    public static BlockVariation UPPER_BIRCH_WOOD_SLAB = new BlockVariation((byte) 10, "Upper Birch Wood Slab");

    public static BlockVariation UPPER_JUNGLE_WOOD_SLAB = new BlockVariation((byte) 11, "Upper Jungle Wood Slab");

    public static BlockVariation UPPER_ACACIA_WOOD_SLAB = new BlockVariation((byte) 12, "Upper Acacia Wood Slab");

    public static BlockVariation UPPER_DARK_OAK_WOOD_SLAB = new BlockVariation((byte) 13, "Upper Dark Oak Wood Slab");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(12);
        list.add(OAK_WOOD_SLAB);
        variationsArray[0]= OAK_WOOD_SLAB;
        list.add(SPRUCE_WOOD_SLAB);
        variationsArray[1]= SPRUCE_WOOD_SLAB;
        list.add(BIRCH_WOOD_SLAB);
        variationsArray[2]= BIRCH_WOOD_SLAB;
        list.add(JUNGLE_WOOD_SLAB);
        variationsArray[3]= JUNGLE_WOOD_SLAB;
        list.add(ACACIA_WOOD_SLAB);
        variationsArray[4]= ACACIA_WOOD_SLAB;
        list.add(DARK_OAK_WOOD_SLAB);
        variationsArray[5]= DARK_OAK_WOOD_SLAB;
        list.add(UPPER_OAK_WOOD_SLAB);
        variationsArray[8]= UPPER_OAK_WOOD_SLAB;
        list.add(UPPER_SPRUCE_WOOD_SLAB);
        variationsArray[9]= UPPER_SPRUCE_WOOD_SLAB;
        list.add(UPPER_BIRCH_WOOD_SLAB);
        variationsArray[10]= UPPER_BIRCH_WOOD_SLAB;
        list.add(UPPER_JUNGLE_WOOD_SLAB);
        variationsArray[11]= UPPER_JUNGLE_WOOD_SLAB;
        list.add(UPPER_ACACIA_WOOD_SLAB);
        variationsArray[12]= UPPER_ACACIA_WOOD_SLAB;
        list.add(UPPER_DARK_OAK_WOOD_SLAB);
        variationsArray[13]= UPPER_DARK_OAK_WOOD_SLAB;
        variations = Collections.unmodifiableList(list);
    }
}
