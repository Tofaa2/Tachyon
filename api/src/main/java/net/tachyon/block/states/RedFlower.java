package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class RedFlower {
    public static BlockVariation POPPY = new BlockVariation((byte) 0, "Poppy");

    public static BlockVariation BLUE_ORCHID = new BlockVariation((byte) 1, "Blue Orchid");

    public static BlockVariation ALLIUM = new BlockVariation((byte) 2, "Allium");

    public static BlockVariation AZURE_BLUET = new BlockVariation((byte) 3, "Azure Bluet");

    public static BlockVariation RED_TULIP = new BlockVariation((byte) 4, "Red Tulip");

    public static BlockVariation ORANGE_TULIP = new BlockVariation((byte) 5, "Orange Tulip");

    public static BlockVariation WHITE_TULIP = new BlockVariation((byte) 6, "White Tulip");

    public static BlockVariation PINK_TULIP = new BlockVariation((byte) 7, "Pink Tulip");

    public static BlockVariation OXEYE_DAISY = new BlockVariation((byte) 8, "Oxeye Daisy");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(9);
        list.add(POPPY);
        variationsArray[0]= POPPY;
        list.add(BLUE_ORCHID);
        variationsArray[1]= BLUE_ORCHID;
        list.add(ALLIUM);
        variationsArray[2]= ALLIUM;
        list.add(AZURE_BLUET);
        variationsArray[3]= AZURE_BLUET;
        list.add(RED_TULIP);
        variationsArray[4]= RED_TULIP;
        list.add(ORANGE_TULIP);
        variationsArray[5]= ORANGE_TULIP;
        list.add(WHITE_TULIP);
        variationsArray[6]= WHITE_TULIP;
        list.add(PINK_TULIP);
        variationsArray[7]= PINK_TULIP;
        list.add(OXEYE_DAISY);
        variationsArray[8]= OXEYE_DAISY;
        variations = Collections.unmodifiableList(list);
    }
}
