package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class Log {
    public static BlockVariation OAK_WOOD_FACING_UPDOWN = new BlockVariation((byte) 0, "Oak wood facing up/down");

    public static BlockVariation SPRUCE_WOOD_FACING_UPDOWN = new BlockVariation((byte) 1, "Spruce wood facing up/down");

    public static BlockVariation BIRCH_WOOD_FACING_UPDOWN = new BlockVariation((byte) 2, "Birch wood facing up/down");

    public static BlockVariation JUNGLE_WOOD_FACING_UPDOWN = new BlockVariation((byte) 3, "Jungle wood facing up/down");

    public static BlockVariation OAK_WOOD_FACING_EASTWEST = new BlockVariation((byte) 4, "Oak wood facing East/West");

    public static BlockVariation SPRUCE_WOOD_FACING_EASTWEST = new BlockVariation((byte) 5, "Spruce wood facing East/West");

    public static BlockVariation BIRCH_WOOD_FACING_EASTWEST = new BlockVariation((byte) 6, "Birch wood facing East/West");

    public static BlockVariation JUNGLE_WOOD_FACING_EASTWEST = new BlockVariation((byte) 7, "Jungle wood facing East/West");

    public static BlockVariation OAK_WOOD_FACING_NORTHSOUTH = new BlockVariation((byte) 8, "Oak wood facing North/South");

    public static BlockVariation SPRUCE_WOOD_FACING_NORTHSOUTH = new BlockVariation((byte) 9, "Spruce wood facing North/South");

    public static BlockVariation BIRCH_WOOD_FACING_NORTHSOUTH = new BlockVariation((byte) 10, "Birch wood facing North/South");

    public static BlockVariation JUNGLE_WOOD_FACING_NORTHSOUTH = new BlockVariation((byte) 11, "Jungle wood facing North/South");

    public static BlockVariation OAK_WOOD_WITH_ONLY_BARK = new BlockVariation((byte) 12, "Oak wood with only bark");

    public static BlockVariation SPRUCE_WOOD_WITH_ONLY_BARK = new BlockVariation((byte) 13, "Spruce wood with only bark");

    public static BlockVariation BIRCH_WOOD_WITH_ONLY_BARK = new BlockVariation((byte) 14, "Birch wood with only bark");

    public static BlockVariation JUNGLE_WOOD_WITH_ONLY_BARK = new BlockVariation((byte) 15, "Jungle wood with only bark");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(16);
        list.add(OAK_WOOD_FACING_UPDOWN);
        variationsArray[0]= OAK_WOOD_FACING_UPDOWN;
        list.add(SPRUCE_WOOD_FACING_UPDOWN);
        variationsArray[1]= SPRUCE_WOOD_FACING_UPDOWN;
        list.add(BIRCH_WOOD_FACING_UPDOWN);
        variationsArray[2]= BIRCH_WOOD_FACING_UPDOWN;
        list.add(JUNGLE_WOOD_FACING_UPDOWN);
        variationsArray[3]= JUNGLE_WOOD_FACING_UPDOWN;
        list.add(OAK_WOOD_FACING_EASTWEST);
        variationsArray[4]= OAK_WOOD_FACING_EASTWEST;
        list.add(SPRUCE_WOOD_FACING_EASTWEST);
        variationsArray[5]= SPRUCE_WOOD_FACING_EASTWEST;
        list.add(BIRCH_WOOD_FACING_EASTWEST);
        variationsArray[6]= BIRCH_WOOD_FACING_EASTWEST;
        list.add(JUNGLE_WOOD_FACING_EASTWEST);
        variationsArray[7]= JUNGLE_WOOD_FACING_EASTWEST;
        list.add(OAK_WOOD_FACING_NORTHSOUTH);
        variationsArray[8]= OAK_WOOD_FACING_NORTHSOUTH;
        list.add(SPRUCE_WOOD_FACING_NORTHSOUTH);
        variationsArray[9]= SPRUCE_WOOD_FACING_NORTHSOUTH;
        list.add(BIRCH_WOOD_FACING_NORTHSOUTH);
        variationsArray[10]= BIRCH_WOOD_FACING_NORTHSOUTH;
        list.add(JUNGLE_WOOD_FACING_NORTHSOUTH);
        variationsArray[11]= JUNGLE_WOOD_FACING_NORTHSOUTH;
        list.add(OAK_WOOD_WITH_ONLY_BARK);
        variationsArray[12]= OAK_WOOD_WITH_ONLY_BARK;
        list.add(SPRUCE_WOOD_WITH_ONLY_BARK);
        variationsArray[13]= SPRUCE_WOOD_WITH_ONLY_BARK;
        list.add(BIRCH_WOOD_WITH_ONLY_BARK);
        variationsArray[14]= BIRCH_WOOD_WITH_ONLY_BARK;
        list.add(JUNGLE_WOOD_WITH_ONLY_BARK);
        variationsArray[15]= JUNGLE_WOOD_WITH_ONLY_BARK;
        variations = Collections.unmodifiableList(list);
    }
}
