package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Tnt {
    public static BlockVariation DROPS_A_TNT_ITEM_WHEN_BROKEN = new BlockVariation((byte) 0, "Drops a TNT item when broken");

    public static BlockVariation ACTIVATES_WHEN_BROKEN = new BlockVariation((byte) 1, "Activates when broken");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(2);
        list.add(DROPS_A_TNT_ITEM_WHEN_BROKEN);
        variationsArray[0]= DROPS_A_TNT_ITEM_WHEN_BROKEN;
        list.add(ACTIVATES_WHEN_BROKEN);
        variationsArray[1]= ACTIVATES_WHEN_BROKEN;
        variations = Collections.unmodifiableList(list);
    }
}
