package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class StoneSlab2 {
    public static BlockVariation RED_SANDSTONE_SLAB = new BlockVariation((byte) 0, "Red Sandstone Slab");

    public static BlockVariation UPPER_RED_SANDSTONE_SLAB = new BlockVariation((byte) 8, "Upper Red Sandstone Slab");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(RED_SANDSTONE_SLAB);
        variationsArray[0]= RED_SANDSTONE_SLAB;
        list.add(UPPER_RED_SANDSTONE_SLAB);
        variationsArray[8]= UPPER_RED_SANDSTONE_SLAB;
        variations = Collections.unmodifiableList(list);
    }
}
