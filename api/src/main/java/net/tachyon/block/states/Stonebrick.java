package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Stonebrick {
    public static BlockVariation STONE_BRICK = new BlockVariation((byte) 0, "Stone brick");

    public static BlockVariation MOSSY_STONE_BRICK = new BlockVariation((byte) 1, "Mossy stone brick");

    public static BlockVariation CRACKED_STONE_BRICK = new BlockVariation((byte) 2, "Cracked stone brick");

    public static BlockVariation CHISELED_STONE_BRICK = new BlockVariation((byte) 3, "Chiseled stone brick");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(4);
        list.add(STONE_BRICK);
        variationsArray[0]= STONE_BRICK;
        list.add(MOSSY_STONE_BRICK);
        variationsArray[1]= MOSSY_STONE_BRICK;
        list.add(CRACKED_STONE_BRICK);
        variationsArray[2]= CRACKED_STONE_BRICK;
        list.add(CHISELED_STONE_BRICK);
        variationsArray[3]= CHISELED_STONE_BRICK;
        variations = Collections.unmodifiableList(list);
    }
}
