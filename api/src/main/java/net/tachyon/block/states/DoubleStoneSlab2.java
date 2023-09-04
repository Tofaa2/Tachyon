package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class DoubleStoneSlab2 {
    public static BlockVariation DOUBLE_RED_SANDSTONE_SLAB = new BlockVariation((byte) 0, "Double Red Sandstone Slab");

    public static BlockVariation SMOOTH_DOUBLE_RED_SANDSTONE_SLAB = new BlockVariation((byte) 8, "Smooth Double Red Sandstone Slab");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(DOUBLE_RED_SANDSTONE_SLAB);
        variationsArray[0]= DOUBLE_RED_SANDSTONE_SLAB;
        list.add(SMOOTH_DOUBLE_RED_SANDSTONE_SLAB);
        variationsArray[8]= SMOOTH_DOUBLE_RED_SANDSTONE_SLAB;
        variations = Collections.unmodifiableList(list);
    }
}
