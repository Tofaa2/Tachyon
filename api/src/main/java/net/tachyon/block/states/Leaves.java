package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class Leaves {
    public static BlockVariation OAK_LEAVES = new BlockVariation((byte) 0, "Oak Leaves");

    public static BlockVariation SPRUCE_LEAVES = new BlockVariation((byte) 1, "Spruce Leaves");

    public static BlockVariation BIRCH_LEAVES = new BlockVariation((byte) 2, "Birch Leaves");

    public static BlockVariation JUNGLE_LEAVES = new BlockVariation((byte) 3, "Jungle Leaves");

    public static BlockVariation OAK_LEAVES_NO_DECAY = new BlockVariation((byte) 4, "Oak Leaves (no decay)");

    public static BlockVariation SPRUCE_LEAVES_NO_DECAY = new BlockVariation((byte) 5, "Spruce Leaves (no decay)");

    public static BlockVariation BIRCH_LEAVES_NO_DECAY = new BlockVariation((byte) 6, "Birch Leaves (no decay)");

    public static BlockVariation JUNGLE_LEAVES_NO_DECAY = new BlockVariation((byte) 7, "Jungle Leaves (no decay)");

    public static BlockVariation OAK_LEAVES_CHECK_DECAY = new BlockVariation((byte) 8, "Oak Leaves (check decay)");

    public static BlockVariation SPRUCE_LEAVES_CHECK_DECAY = new BlockVariation((byte) 9, "Spruce Leaves (check decay)");

    public static BlockVariation BIRCH_LEAVES_CHECK_DECAY = new BlockVariation((byte) 10, "Birch Leaves (check decay)");

    public static BlockVariation JUNGLE_LEAVES_CHECK_DECAY = new BlockVariation((byte) 11, "Jungle Leaves (check decay)");

    public static BlockVariation OAK_LEAVES_NO_DECAY_AND_CHECK_DECAY = new BlockVariation((byte) 12, "Oak Leaves (no decay and check decay)");

    public static BlockVariation SPRUCE_LEAVES_NO_DECAY_AND_CHECK_DECAY = new BlockVariation((byte) 13, "Spruce Leaves (no decay and check decay)");

    public static BlockVariation BIRCH_LEAVES_NO_DECAY_AND_CHECK_DECAY = new BlockVariation((byte) 14, "Birch Leaves (no decay and check decay)");

    public static BlockVariation JUNGLE_LEAVES_NO_DECAY_AND_CHECK_DECAY = new BlockVariation((byte) 15, "Jungle Leaves (no decay and check decay)");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(16);
        list.add(OAK_LEAVES);
        variationsArray[0]= OAK_LEAVES;
        list.add(SPRUCE_LEAVES);
        variationsArray[1]= SPRUCE_LEAVES;
        list.add(BIRCH_LEAVES);
        variationsArray[2]= BIRCH_LEAVES;
        list.add(JUNGLE_LEAVES);
        variationsArray[3]= JUNGLE_LEAVES;
        list.add(OAK_LEAVES_NO_DECAY);
        variationsArray[4]= OAK_LEAVES_NO_DECAY;
        list.add(SPRUCE_LEAVES_NO_DECAY);
        variationsArray[5]= SPRUCE_LEAVES_NO_DECAY;
        list.add(BIRCH_LEAVES_NO_DECAY);
        variationsArray[6]= BIRCH_LEAVES_NO_DECAY;
        list.add(JUNGLE_LEAVES_NO_DECAY);
        variationsArray[7]= JUNGLE_LEAVES_NO_DECAY;
        list.add(OAK_LEAVES_CHECK_DECAY);
        variationsArray[8]= OAK_LEAVES_CHECK_DECAY;
        list.add(SPRUCE_LEAVES_CHECK_DECAY);
        variationsArray[9]= SPRUCE_LEAVES_CHECK_DECAY;
        list.add(BIRCH_LEAVES_CHECK_DECAY);
        variationsArray[10]= BIRCH_LEAVES_CHECK_DECAY;
        list.add(JUNGLE_LEAVES_CHECK_DECAY);
        variationsArray[11]= JUNGLE_LEAVES_CHECK_DECAY;
        list.add(OAK_LEAVES_NO_DECAY_AND_CHECK_DECAY);
        variationsArray[12]= OAK_LEAVES_NO_DECAY_AND_CHECK_DECAY;
        list.add(SPRUCE_LEAVES_NO_DECAY_AND_CHECK_DECAY);
        variationsArray[13]= SPRUCE_LEAVES_NO_DECAY_AND_CHECK_DECAY;
        list.add(BIRCH_LEAVES_NO_DECAY_AND_CHECK_DECAY);
        variationsArray[14]= BIRCH_LEAVES_NO_DECAY_AND_CHECK_DECAY;
        list.add(JUNGLE_LEAVES_NO_DECAY_AND_CHECK_DECAY);
        variationsArray[15]= JUNGLE_LEAVES_NO_DECAY_AND_CHECK_DECAY;
        variations = Collections.unmodifiableList(list);
    }
}
