package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Sandstone {
    public static BlockVariation SANDSTONE = new BlockVariation((byte) 0, "Sandstone");

    public static BlockVariation CHISELED_SANDSTONE = new BlockVariation((byte) 1, "Chiseled sandstone");

    public static BlockVariation SMOOTH_SANDSTONE = new BlockVariation((byte) 2, "Smooth sandstone");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(3);
        list.add(SANDSTONE);
        variationsArray[0]= SANDSTONE;
        list.add(CHISELED_SANDSTONE);
        variationsArray[1]= CHISELED_SANDSTONE;
        list.add(SMOOTH_SANDSTONE);
        variationsArray[2]= SMOOTH_SANDSTONE;
        variations = Collections.unmodifiableList(list);
    }
}
