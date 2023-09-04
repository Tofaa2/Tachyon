package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class CobblestoneWall {
    public static BlockVariation COBBLESTONE_WALL = new BlockVariation((byte) 0, "Cobblestone Wall");

    public static BlockVariation MOSSY_COBBLESTONE_WALL = new BlockVariation((byte) 1, "Mossy Cobblestone Wall");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(COBBLESTONE_WALL);
        variationsArray[0]= COBBLESTONE_WALL;
        list.add(MOSSY_COBBLESTONE_WALL);
        variationsArray[1]= MOSSY_COBBLESTONE_WALL;
        variations = Collections.unmodifiableList(list);
    }
}
