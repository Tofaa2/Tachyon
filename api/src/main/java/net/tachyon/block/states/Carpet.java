package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Carpet {
    public static BlockVariation WHITE_CARPET = new BlockVariation((byte) 0, "White Carpet");

    public static BlockVariation ORANGE_CARPET = new BlockVariation((byte) 1, "Orange Carpet");

    public static BlockVariation MAGENTA_CARPET = new BlockVariation((byte) 2, "Magenta Carpet");

    public static BlockVariation LIGHT_BLUE_CARPET = new BlockVariation((byte) 3, "Light Blue Carpet");

    public static BlockVariation YELLOW_CARPET = new BlockVariation((byte) 4, "Yellow Carpet");

    public static BlockVariation LIME_CARPET = new BlockVariation((byte) 5, "Lime Carpet");

    public static BlockVariation PINK_CARPET = new BlockVariation((byte) 6, "Pink Carpet");

    public static BlockVariation GRAY_CARPET = new BlockVariation((byte) 7, "Gray Carpet");

    public static BlockVariation LIGHT_GRAY_CARPET = new BlockVariation((byte) 8, "Light Gray Carpet");

    public static BlockVariation CYAN_CARPET = new BlockVariation((byte) 9, "Cyan Carpet");

    public static BlockVariation PURPLE_CARPET = new BlockVariation((byte) 10, "Purple Carpet");

    public static BlockVariation BLUE_CARPET = new BlockVariation((byte) 11, "Blue Carpet");

    public static BlockVariation BROWN_CARPET = new BlockVariation((byte) 12, "Brown Carpet");

    public static BlockVariation GREEN_CARPET = new BlockVariation((byte) 13, "Green Carpet");

    public static BlockVariation RED_CARPET = new BlockVariation((byte) 14, "Red Carpet");

    public static BlockVariation BLACK_CARPET = new BlockVariation((byte) 15, "Black Carpet");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(16);
        list.add(WHITE_CARPET);
        variationsArray[0]= WHITE_CARPET;
        list.add(ORANGE_CARPET);
        variationsArray[1]= ORANGE_CARPET;
        list.add(MAGENTA_CARPET);
        variationsArray[2]= MAGENTA_CARPET;
        list.add(LIGHT_BLUE_CARPET);
        variationsArray[3]= LIGHT_BLUE_CARPET;
        list.add(YELLOW_CARPET);
        variationsArray[4]= YELLOW_CARPET;
        list.add(LIME_CARPET);
        variationsArray[5]= LIME_CARPET;
        list.add(PINK_CARPET);
        variationsArray[6]= PINK_CARPET;
        list.add(GRAY_CARPET);
        variationsArray[7]= GRAY_CARPET;
        list.add(LIGHT_GRAY_CARPET);
        variationsArray[8]= LIGHT_GRAY_CARPET;
        list.add(CYAN_CARPET);
        variationsArray[9]= CYAN_CARPET;
        list.add(PURPLE_CARPET);
        variationsArray[10]= PURPLE_CARPET;
        list.add(BLUE_CARPET);
        variationsArray[11]= BLUE_CARPET;
        list.add(BROWN_CARPET);
        variationsArray[12]= BROWN_CARPET;
        list.add(GREEN_CARPET);
        variationsArray[13]= GREEN_CARPET;
        list.add(RED_CARPET);
        variationsArray[14]= RED_CARPET;
        list.add(BLACK_CARPET);
        variationsArray[15]= BLACK_CARPET;
        variations = Collections.unmodifiableList(list);
    }
}
