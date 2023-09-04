package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class Jukebox {
    public static BlockVariation NO_DISC_INSERTED = new BlockVariation((byte) 0, "No disc inserted");

    public static BlockVariation CONTAINS_A_DISC = new BlockVariation((byte) 1, "Contains a disc");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(NO_DISC_INSERTED);
        variationsArray[0]= NO_DISC_INSERTED;
        list.add(CONTAINS_A_DISC);
        variationsArray[1]= CONTAINS_A_DISC;
        variations = Collections.unmodifiableList(list);
    }
}
