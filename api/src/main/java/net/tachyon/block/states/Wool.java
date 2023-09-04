package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Wool {
    public static BlockVariation WHITE_WOOL = new BlockVariation((byte) 0, "White Wool");

    public static BlockVariation ORANGE_WOOL = new BlockVariation((byte) 1, "Orange Wool");

    public static BlockVariation MAGENTA_WOOL = new BlockVariation((byte) 2, "Magenta Wool");

    public static BlockVariation LIGHT_BLUE_WOOL = new BlockVariation((byte) 3, "Light blue Wool");

    public static BlockVariation YELLOW_WOOL = new BlockVariation((byte) 4, "Yellow Wool");

    public static BlockVariation LIME_WOOL = new BlockVariation((byte) 5, "Lime Wool");

    public static BlockVariation PINK_WOOL = new BlockVariation((byte) 6, "Pink Wool");

    public static BlockVariation GRAY_WOOL = new BlockVariation((byte) 7, "Gray Wool");

    public static BlockVariation LIGHT_GRAY_WOOL = new BlockVariation((byte) 8, "Light gray Wool");

    public static BlockVariation CYAN_WOOL = new BlockVariation((byte) 9, "Cyan Wool");

    public static BlockVariation PURPLE_WOOL = new BlockVariation((byte) 10, "Purple Wool");

    public static BlockVariation BLUE_WOOL = new BlockVariation((byte) 11, "Blue Wool");

    public static BlockVariation BROWN_WOOL = new BlockVariation((byte) 12, "Brown Wool");

    public static BlockVariation GREEN_WOOL = new BlockVariation((byte) 13, "Green Wool");

    public static BlockVariation RED_WOOL = new BlockVariation((byte) 14, "Red Wool");

    public static BlockVariation BLACK_WOOL = new BlockVariation((byte) 15, "Black Wool");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(16);
        list.add(WHITE_WOOL);
        variationsArray[0]= WHITE_WOOL;
        list.add(ORANGE_WOOL);
        variationsArray[1]= ORANGE_WOOL;
        list.add(MAGENTA_WOOL);
        variationsArray[2]= MAGENTA_WOOL;
        list.add(LIGHT_BLUE_WOOL);
        variationsArray[3]= LIGHT_BLUE_WOOL;
        list.add(YELLOW_WOOL);
        variationsArray[4]= YELLOW_WOOL;
        list.add(LIME_WOOL);
        variationsArray[5]= LIME_WOOL;
        list.add(PINK_WOOL);
        variationsArray[6]= PINK_WOOL;
        list.add(GRAY_WOOL);
        variationsArray[7]= GRAY_WOOL;
        list.add(LIGHT_GRAY_WOOL);
        variationsArray[8]= LIGHT_GRAY_WOOL;
        list.add(CYAN_WOOL);
        variationsArray[9]= CYAN_WOOL;
        list.add(PURPLE_WOOL);
        variationsArray[10]= PURPLE_WOOL;
        list.add(BLUE_WOOL);
        variationsArray[11]= BLUE_WOOL;
        list.add(BROWN_WOOL);
        variationsArray[12]= BROWN_WOOL;
        list.add(GREEN_WOOL);
        variationsArray[13]= GREEN_WOOL;
        list.add(RED_WOOL);
        variationsArray[14]= RED_WOOL;
        list.add(BLACK_WOOL);
        variationsArray[15]= BLACK_WOOL;
        variations = Collections.unmodifiableList(list);
    }
}
