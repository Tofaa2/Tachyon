package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Planks {
    public static BlockVariation OAK_WOOD_PLANKS = new BlockVariation((byte) 0, "Oak Wood Planks");

    public static BlockVariation SPRUCE_WOOD_PLANKS = new BlockVariation((byte) 1, "Spruce Wood Planks");

    public static BlockVariation BIRCH_WOOD_PLANKS = new BlockVariation((byte) 2, "Birch Wood Planks");

    public static BlockVariation JUNGLE_WOOD_PLANKS = new BlockVariation((byte) 3, "Jungle Wood Planks");

    public static BlockVariation ACACIA_WOOD_PLANKS = new BlockVariation((byte) 4, "Acacia Wood Planks");

    public static BlockVariation DARK_OAK_WOOD_PLANKS = new BlockVariation((byte) 5, "Dark Oak Wood Planks");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(6);
        list.add(OAK_WOOD_PLANKS);
        variationsArray[0]= OAK_WOOD_PLANKS;
        list.add(SPRUCE_WOOD_PLANKS);
        variationsArray[1]= SPRUCE_WOOD_PLANKS;
        list.add(BIRCH_WOOD_PLANKS);
        variationsArray[2]= BIRCH_WOOD_PLANKS;
        list.add(JUNGLE_WOOD_PLANKS);
        variationsArray[3]= JUNGLE_WOOD_PLANKS;
        list.add(ACACIA_WOOD_PLANKS);
        variationsArray[4]= ACACIA_WOOD_PLANKS;
        list.add(DARK_OAK_WOOD_PLANKS);
        variationsArray[5]= DARK_OAK_WOOD_PLANKS;
        variations = Collections.unmodifiableList(list);
    }
}
