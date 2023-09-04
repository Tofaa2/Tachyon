package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Skull {
    public static BlockVariation SKELETON_SKULL = new BlockVariation((byte) 0, "Skeleton Skull");

    public static BlockVariation WITHER_SKELETON_SKULL = new BlockVariation((byte) 1, "Wither Skeleton Skull");

    public static BlockVariation ZOMBIE_HEAD = new BlockVariation((byte) 2, "Zombie Head");

    public static BlockVariation HEAD = new BlockVariation((byte) 3, "Head");

    public static BlockVariation CREEPER_HEAD = new BlockVariation((byte) 4, "Creeper Head");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(5);
        list.add(SKELETON_SKULL);
        variationsArray[0]= SKELETON_SKULL;
        list.add(WITHER_SKELETON_SKULL);
        variationsArray[1]= WITHER_SKELETON_SKULL;
        list.add(ZOMBIE_HEAD);
        variationsArray[2]= ZOMBIE_HEAD;
        list.add(HEAD);
        variationsArray[3]= HEAD;
        list.add(CREEPER_HEAD);
        variationsArray[4]= CREEPER_HEAD;
        variations = Collections.unmodifiableList(list);
    }
}
