package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Sponge {
    public static BlockVariation SPONGE = new BlockVariation((byte) 0, "Sponge");

    public static BlockVariation WET_SPONGE = new BlockVariation((byte) 1, "Wet Sponge");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(SPONGE);
        variationsArray[0]= SPONGE;
        list.add(WET_SPONGE);
        variationsArray[1]= WET_SPONGE;
        variations = Collections.unmodifiableList(list);
    }
}
