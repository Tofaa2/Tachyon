package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class Leaves2 {
    public static BlockVariation ACACIA_LEAVES = new BlockVariation((byte) 0, "Acacia Leaves");

    public static BlockVariation DARK_OAK_LEAVES = new BlockVariation((byte) 1, "Dark Oak Leaves");

    public static BlockVariation ACACIA_LEAVES_NO_DECAY = new BlockVariation((byte) 4, "Acacia Leaves (no decay)");

    public static BlockVariation DARK_OAK_LEAVES_NO_DECAY = new BlockVariation((byte) 5, "Dark Oak Leaves (no decay)");

    public static BlockVariation ACACIA_LEAVES_CHECK_DECAY = new BlockVariation((byte) 8, "Acacia Leaves (check decay)");

    public static BlockVariation DARK_OAK_LEAVES_CHECK_DECAY = new BlockVariation((byte) 9, "Dark Oak Leaves (check decay)");

    public static BlockVariation ACACIA_LEAVES_NO_DECAY_AND_CHECK_DECAY = new BlockVariation((byte) 12, "Acacia Leaves (no decay and check decay)");

    public static BlockVariation DARK_OAK_LEAVES_NO_DECAY_AND_CHECK_DECAY = new BlockVariation((byte) 13, "Dark Oak Leaves (no decay and check decay)");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(8);
        list.add(ACACIA_LEAVES);
        variationsArray[0]= ACACIA_LEAVES;
        list.add(DARK_OAK_LEAVES);
        variationsArray[1]= DARK_OAK_LEAVES;
        list.add(ACACIA_LEAVES_NO_DECAY);
        variationsArray[4]= ACACIA_LEAVES_NO_DECAY;
        list.add(DARK_OAK_LEAVES_NO_DECAY);
        variationsArray[5]= DARK_OAK_LEAVES_NO_DECAY;
        list.add(ACACIA_LEAVES_CHECK_DECAY);
        variationsArray[8]= ACACIA_LEAVES_CHECK_DECAY;
        list.add(DARK_OAK_LEAVES_CHECK_DECAY);
        variationsArray[9]= DARK_OAK_LEAVES_CHECK_DECAY;
        list.add(ACACIA_LEAVES_NO_DECAY_AND_CHECK_DECAY);
        variationsArray[12]= ACACIA_LEAVES_NO_DECAY_AND_CHECK_DECAY;
        list.add(DARK_OAK_LEAVES_NO_DECAY_AND_CHECK_DECAY);
        variationsArray[13]= DARK_OAK_LEAVES_NO_DECAY_AND_CHECK_DECAY;
        variations = Collections.unmodifiableList(list);
    }
}
