package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class DoubleWoodenSlab {
    public static BlockVariation DOUBLE_OAK_WOOD_SLAB = new BlockVariation((byte) 0, "Double Oak Wood Slab");

    public static BlockVariation DOUBLE_SPRUCE_WOOD_SLAB = new BlockVariation((byte) 1, "Double Spruce Wood Slab");

    public static BlockVariation DOUBLE_BIRCH_WOOD_SLAB = new BlockVariation((byte) 2, "Double Birch Wood Slab");

    public static BlockVariation DOUBLE_JUNGLE_WOOD_SLAB = new BlockVariation((byte) 3, "Double Jungle Wood Slab");

    public static BlockVariation DOUBLE_ACACIA_WOOD_SLAB = new BlockVariation((byte) 4, "Double Acacia Wood Slab");

    public static BlockVariation DOUBLE_DARK_OAK_WOOD_SLAB = new BlockVariation((byte) 5, "Double Dark Oak Wood Slab");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(6);
        list.add(DOUBLE_OAK_WOOD_SLAB);
        variationsArray[0]= DOUBLE_OAK_WOOD_SLAB;
        list.add(DOUBLE_SPRUCE_WOOD_SLAB);
        variationsArray[1]= DOUBLE_SPRUCE_WOOD_SLAB;
        list.add(DOUBLE_BIRCH_WOOD_SLAB);
        variationsArray[2]= DOUBLE_BIRCH_WOOD_SLAB;
        list.add(DOUBLE_JUNGLE_WOOD_SLAB);
        variationsArray[3]= DOUBLE_JUNGLE_WOOD_SLAB;
        list.add(DOUBLE_ACACIA_WOOD_SLAB);
        variationsArray[4]= DOUBLE_ACACIA_WOOD_SLAB;
        list.add(DOUBLE_DARK_OAK_WOOD_SLAB);
        variationsArray[5]= DOUBLE_DARK_OAK_WOOD_SLAB;
        variations = Collections.unmodifiableList(list);
    }
}
