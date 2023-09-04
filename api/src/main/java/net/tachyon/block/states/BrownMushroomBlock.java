package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class BrownMushroomBlock {
    public static BlockVariation PORES_ON_ALL_SIDES = new BlockVariation((byte) 0, "Pores on all sides");

    public static BlockVariation CAP_TEXTURE_ON_TOP_WEST_AND_NORTH = new BlockVariation((byte) 1, "Cap texture on top, west and north");

    public static BlockVariation CAP_TEXTURE_ON_TOP_AND_NORTH = new BlockVariation((byte) 2, "Cap texture on top and north");

    public static BlockVariation CAP_TEXTURE_ON_TOP_NORTH_AND_EAST = new BlockVariation((byte) 3, "Cap texture on top, north and east");

    public static BlockVariation CAP_TEXTURE_ON_TOP_AND_WEST = new BlockVariation((byte) 4, "Cap texture on top and west");

    public static BlockVariation CAP_TEXTURE_ON_TOP = new BlockVariation((byte) 5, "Cap texture on top");

    public static BlockVariation CAP_TEXTURE_ON_TOP_AND_EAST = new BlockVariation((byte) 6, "Cap texture on top and east");

    public static BlockVariation CAP_TEXTURE_ON_TOP_SOUTH_AND_WEST = new BlockVariation((byte) 7, "Cap texture on top, south and west");

    public static BlockVariation CAP_TEXTURE_ON_TOP_AND_SOUTH = new BlockVariation((byte) 8, "Cap texture on top and south");

    public static BlockVariation CAP_TEXTURE_ON_TOP_EAST_AND_SOUTH = new BlockVariation((byte) 9, "Cap texture on top, east and south");

    public static BlockVariation STEM_TEXTURE_ON_ALL_FOUR_SIDES_PORES_ON_TOP_AND_BOTTOM = new BlockVariation((byte) 10, "Stem texture on all four sides, pores on top and bottom");

    public static BlockVariation CAP_TEXTURE_ON_ALL_SIX_SIDES = new BlockVariation((byte) 14, "Cap texture on all six sides");

    public static BlockVariation STEM_TEXTURE_ON_ALL_SIX_SIDES = new BlockVariation((byte) 15, "Stem texture on all six sides");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(13);
        list.add(PORES_ON_ALL_SIDES);
        variationsArray[0]= PORES_ON_ALL_SIDES;
        list.add(CAP_TEXTURE_ON_TOP_WEST_AND_NORTH);
        variationsArray[1]= CAP_TEXTURE_ON_TOP_WEST_AND_NORTH;
        list.add(CAP_TEXTURE_ON_TOP_AND_NORTH);
        variationsArray[2]= CAP_TEXTURE_ON_TOP_AND_NORTH;
        list.add(CAP_TEXTURE_ON_TOP_NORTH_AND_EAST);
        variationsArray[3]= CAP_TEXTURE_ON_TOP_NORTH_AND_EAST;
        list.add(CAP_TEXTURE_ON_TOP_AND_WEST);
        variationsArray[4]= CAP_TEXTURE_ON_TOP_AND_WEST;
        list.add(CAP_TEXTURE_ON_TOP);
        variationsArray[5]= CAP_TEXTURE_ON_TOP;
        list.add(CAP_TEXTURE_ON_TOP_AND_EAST);
        variationsArray[6]= CAP_TEXTURE_ON_TOP_AND_EAST;
        list.add(CAP_TEXTURE_ON_TOP_SOUTH_AND_WEST);
        variationsArray[7]= CAP_TEXTURE_ON_TOP_SOUTH_AND_WEST;
        list.add(CAP_TEXTURE_ON_TOP_AND_SOUTH);
        variationsArray[8]= CAP_TEXTURE_ON_TOP_AND_SOUTH;
        list.add(CAP_TEXTURE_ON_TOP_EAST_AND_SOUTH);
        variationsArray[9]= CAP_TEXTURE_ON_TOP_EAST_AND_SOUTH;
        list.add(STEM_TEXTURE_ON_ALL_FOUR_SIDES_PORES_ON_TOP_AND_BOTTOM);
        variationsArray[10]= STEM_TEXTURE_ON_ALL_FOUR_SIDES_PORES_ON_TOP_AND_BOTTOM;
        list.add(CAP_TEXTURE_ON_ALL_SIX_SIDES);
        variationsArray[14]= CAP_TEXTURE_ON_ALL_SIX_SIDES;
        list.add(STEM_TEXTURE_ON_ALL_SIX_SIDES);
        variationsArray[15]= STEM_TEXTURE_ON_ALL_SIX_SIDES;
        variations = Collections.unmodifiableList(list);
    }
}
