package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class DoublePlant {
    public static BlockVariation SUNFLOWER = new BlockVariation((byte) 0, "Sunflower");

    public static BlockVariation LILAC = new BlockVariation((byte) 1, "Lilac");

    public static BlockVariation DOUBLE_TALLGRASS = new BlockVariation((byte) 2, "Double Tallgrass");

    public static BlockVariation LARGE_FERN = new BlockVariation((byte) 3, "Large Fern");

    public static BlockVariation ROSE_BUSH = new BlockVariation((byte) 4, "Rose Bush");

    public static BlockVariation PEONY = new BlockVariation((byte) 5, "Peony");

    public static BlockVariation TOP_HALF_OF_ANY_LARGE_PLANT_LOW_THREE_BITS_0X7_ARE_DERIVED_FROM_THE_BLOCK_BELOW = new BlockVariation((byte) 8, "Top Half of any Large Plant; low three bits 0x7 are derived from the block below.");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(7);
        list.add(SUNFLOWER);
        variationsArray[0]= SUNFLOWER;
        list.add(LILAC);
        variationsArray[1]= LILAC;
        list.add(DOUBLE_TALLGRASS);
        variationsArray[2]= DOUBLE_TALLGRASS;
        list.add(LARGE_FERN);
        variationsArray[3]= LARGE_FERN;
        list.add(ROSE_BUSH);
        variationsArray[4]= ROSE_BUSH;
        list.add(PEONY);
        variationsArray[5]= PEONY;
        list.add(TOP_HALF_OF_ANY_LARGE_PLANT_LOW_THREE_BITS_0X7_ARE_DERIVED_FROM_THE_BLOCK_BELOW);
        variationsArray[8]= TOP_HALF_OF_ANY_LARGE_PLANT_LOW_THREE_BITS_0X7_ARE_DERIVED_FROM_THE_BLOCK_BELOW;
        variations = Collections.unmodifiableList(list);
    }
}
