package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Stone {
    public static BlockVariation STONE = new BlockVariation((byte) 0, "Stone");

    public static BlockVariation GRANITE = new BlockVariation((byte) 1, "Granite");

    public static BlockVariation POLISHED_GRANITE = new BlockVariation((byte) 2, "Polished Granite");

    public static BlockVariation DIORITE = new BlockVariation((byte) 3, "Diorite");

    public static BlockVariation POLISHED_DIORITE = new BlockVariation((byte) 4, "Polished Diorite");

    public static BlockVariation ANDESITE = new BlockVariation((byte) 5, "Andesite");

    public static BlockVariation POLISHED_ANDESITE = new BlockVariation((byte) 6, "Polished Andesite");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(7);
        list.add(STONE);
        variationsArray[0]= STONE;
        list.add(GRANITE);
        variationsArray[1]= GRANITE;
        list.add(POLISHED_GRANITE);
        variationsArray[2]= POLISHED_GRANITE;
        list.add(DIORITE);
        variationsArray[3]= DIORITE;
        list.add(POLISHED_DIORITE);
        variationsArray[4]= POLISHED_DIORITE;
        list.add(ANDESITE);
        variationsArray[5]= ANDESITE;
        list.add(POLISHED_ANDESITE);
        variationsArray[6]= POLISHED_ANDESITE;
        variations = Collections.unmodifiableList(list);
    }
}
