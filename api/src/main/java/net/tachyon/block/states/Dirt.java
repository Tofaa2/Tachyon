package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Dirt {
    public static BlockVariation DIRT = new BlockVariation((byte) 0, "Dirt");

    public static BlockVariation COARSE_DIRT = new BlockVariation((byte) 1, "Coarse Dirt");

    public static BlockVariation PODZOL = new BlockVariation((byte) 2, "Podzol");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(3);
        list.add(DIRT);
        variationsArray[0]= DIRT;
        list.add(COARSE_DIRT);
        variationsArray[1]= COARSE_DIRT;
        list.add(PODZOL);
        variationsArray[2]= PODZOL;
        variations = Collections.unmodifiableList(list);
    }
}
