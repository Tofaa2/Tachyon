package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Prismarine {
    public static BlockVariation PRISMARINE = new BlockVariation((byte) 0, "Prismarine");

    public static BlockVariation PRISMARINE_BRICKS = new BlockVariation((byte) 1, "Prismarine Bricks");

    public static BlockVariation DARK_PRISMARINE = new BlockVariation((byte) 2, "Dark Prismarine");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(3);
        list.add(PRISMARINE);
        variationsArray[0]= PRISMARINE;
        list.add(PRISMARINE_BRICKS);
        variationsArray[1]= PRISMARINE_BRICKS;
        list.add(DARK_PRISMARINE);
        variationsArray[2]= DARK_PRISMARINE;
        variations = Collections.unmodifiableList(list);
    }
}
