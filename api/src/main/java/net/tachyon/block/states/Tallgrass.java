package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Tallgrass {
    public static BlockVariation SHRUB = new BlockVariation((byte) 0, "Shrub");

    public static BlockVariation TALL_GRASS = new BlockVariation((byte) 1, "Tall Grass");

    public static BlockVariation FERN = new BlockVariation((byte) 2, "Fern");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(3);
        list.add(SHRUB);
        variationsArray[0]= SHRUB;
        list.add(TALL_GRASS);
        variationsArray[1]= TALL_GRASS;
        list.add(FERN);
        variationsArray[2]= FERN;
        variations = Collections.unmodifiableList(list);
    }
}
