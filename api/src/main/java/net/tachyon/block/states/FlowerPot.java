package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class FlowerPot {
    public static BlockVariation EMPTY_FLOWER_POT = new BlockVariation((byte) 0, "Empty Flower Pot");

    public static BlockVariation POPPY_FLOWER_POT = new BlockVariation((byte) 1, "Poppy Flower Pot");

    public static BlockVariation DANDELION_FLOWER_POT = new BlockVariation((byte) 2, "Dandelion Flower Pot");

    public static BlockVariation OAK_SAPLING_FLOWER_POT = new BlockVariation((byte) 3, "Oak sapling Flower Pot");

    public static BlockVariation SPRUCE_SAPLING_FLOWER_POT = new BlockVariation((byte) 4, "Spruce sapling Flower Pot");

    public static BlockVariation BIRCH_SAPLING_FLOWER_POT = new BlockVariation((byte) 5, "Birch sapling Flower Pot");

    public static BlockVariation JUNGLE_SAPLING_FLOWER_POT = new BlockVariation((byte) 6, "Jungle sapling Flower Pot");

    public static BlockVariation RED_MUSHROOM_FLOWER_POT = new BlockVariation((byte) 7, "Red mushroom Flower Pot");

    public static BlockVariation BROWN_MUSHROOM_FLOWER_POT = new BlockVariation((byte) 8, "Brown mushroom Flower Pot");

    public static BlockVariation CACTUS_FLOWER_POT = new BlockVariation((byte) 9, "Cactus Flower Pot");

    public static BlockVariation DEAD_BUSH_FLOWER_POT = new BlockVariation((byte) 10, "Dead bush Flower Pot");

    public static BlockVariation FERN_FLOWER_POT = new BlockVariation((byte) 11, "Fern Flower Pot");

    public static BlockVariation ACACIA_SAPLING_FLOWER_POT = new BlockVariation((byte) 12, "Acacia sapling Flower Pot");

    public static BlockVariation DARK_OAK_SAPLING_FLOWER_POT = new BlockVariation((byte) 13, "Dark oak sapling Flower Pot");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(14);
        list.add(EMPTY_FLOWER_POT);
        variationsArray[0]= EMPTY_FLOWER_POT;
        list.add(POPPY_FLOWER_POT);
        variationsArray[1]= POPPY_FLOWER_POT;
        list.add(DANDELION_FLOWER_POT);
        variationsArray[2]= DANDELION_FLOWER_POT;
        list.add(OAK_SAPLING_FLOWER_POT);
        variationsArray[3]= OAK_SAPLING_FLOWER_POT;
        list.add(SPRUCE_SAPLING_FLOWER_POT);
        variationsArray[4]= SPRUCE_SAPLING_FLOWER_POT;
        list.add(BIRCH_SAPLING_FLOWER_POT);
        variationsArray[5]= BIRCH_SAPLING_FLOWER_POT;
        list.add(JUNGLE_SAPLING_FLOWER_POT);
        variationsArray[6]= JUNGLE_SAPLING_FLOWER_POT;
        list.add(RED_MUSHROOM_FLOWER_POT);
        variationsArray[7]= RED_MUSHROOM_FLOWER_POT;
        list.add(BROWN_MUSHROOM_FLOWER_POT);
        variationsArray[8]= BROWN_MUSHROOM_FLOWER_POT;
        list.add(CACTUS_FLOWER_POT);
        variationsArray[9]= CACTUS_FLOWER_POT;
        list.add(DEAD_BUSH_FLOWER_POT);
        variationsArray[10]= DEAD_BUSH_FLOWER_POT;
        list.add(FERN_FLOWER_POT);
        variationsArray[11]= FERN_FLOWER_POT;
        list.add(ACACIA_SAPLING_FLOWER_POT);
        variationsArray[12]= ACACIA_SAPLING_FLOWER_POT;
        list.add(DARK_OAK_SAPLING_FLOWER_POT);
        variationsArray[13]= DARK_OAK_SAPLING_FLOWER_POT;
        variations = Collections.unmodifiableList(list);
    }
}
