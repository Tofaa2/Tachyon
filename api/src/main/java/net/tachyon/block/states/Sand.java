package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Sand {
    public static BlockVariation SAND = new BlockVariation((byte) 0, "Sand");

    public static BlockVariation RED_SAND = new BlockVariation((byte) 1, "Red sand");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(SAND);
        variationsArray[0]= SAND;
        list.add(RED_SAND);
        variationsArray[1]= RED_SAND;
        variations = Collections.unmodifiableList(list);
    }
}
