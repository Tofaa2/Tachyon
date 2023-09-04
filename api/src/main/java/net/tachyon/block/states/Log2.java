package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class Log2 {
    public static BlockVariation ACACIA_WOOD_FACING_UPDOWN = new BlockVariation((byte) 0, "Acacia wood facing up/down");

    public static BlockVariation DARK_OAK_WOOD_FACING_UPDOWN = new BlockVariation((byte) 1, "Dark Oak wood facing up/down");

    public static BlockVariation ACACIA_WOOD_FACING_EASTWEST = new BlockVariation((byte) 4, "Acacia wood facing East/West");

    public static BlockVariation DARK_OAK_WOOD_FACING_EASTWEST = new BlockVariation((byte) 5, "Dark Oak wood facing East/West");

    public static BlockVariation ACACIA_WOOD_FACING_NORTHSOUTH = new BlockVariation((byte) 8, "Acacia wood facing North/South");

    public static BlockVariation DARK_OAK_WOOD_FACING_NORTHSOUTH = new BlockVariation((byte) 9, "Dark Oak wood facing North/South");

    public static BlockVariation ACACIA_WOOD_WITH_ONLY_BARK = new BlockVariation((byte) 12, "Acacia wood with only bark");

    public static BlockVariation DARK_OAK_WOOD_WITH_ONLY_BARK = new BlockVariation((byte) 13, "Dark Oak wood with only bark");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(8);
        list.add(ACACIA_WOOD_FACING_UPDOWN);
        variationsArray[0]= ACACIA_WOOD_FACING_UPDOWN;
        list.add(DARK_OAK_WOOD_FACING_UPDOWN);
        variationsArray[1]= DARK_OAK_WOOD_FACING_UPDOWN;
        list.add(ACACIA_WOOD_FACING_EASTWEST);
        variationsArray[4]= ACACIA_WOOD_FACING_EASTWEST;
        list.add(DARK_OAK_WOOD_FACING_EASTWEST);
        variationsArray[5]= DARK_OAK_WOOD_FACING_EASTWEST;
        list.add(ACACIA_WOOD_FACING_NORTHSOUTH);
        variationsArray[8]= ACACIA_WOOD_FACING_NORTHSOUTH;
        list.add(DARK_OAK_WOOD_FACING_NORTHSOUTH);
        variationsArray[9]= DARK_OAK_WOOD_FACING_NORTHSOUTH;
        list.add(ACACIA_WOOD_WITH_ONLY_BARK);
        variationsArray[12]= ACACIA_WOOD_WITH_ONLY_BARK;
        list.add(DARK_OAK_WOOD_WITH_ONLY_BARK);
        variationsArray[13]= DARK_OAK_WOOD_WITH_ONLY_BARK;
        variations = Collections.unmodifiableList(list);
    }
}
