package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Anvil {
    public static BlockVariation ANVIL = new BlockVariation((byte) 0, "Anvil");

    public static BlockVariation SLIGHTLY_DAMAGED_ANVIL = new BlockVariation((byte) 1, "Slightly Damaged Anvil");

    public static BlockVariation VERY_DAMAGED_ANVIL = new BlockVariation((byte) 2, "Very Damaged Anvil");

    public static BlockVariation ANVIL_NORTHSOUTH = new BlockVariation((byte) 3, "Anvil (North/South)");

    public static BlockVariation ANVIL_EASTWEST = new BlockVariation((byte) 4, "Anvil (East/West)");

    public static BlockVariation ANVIL_SOUTHNORTH = new BlockVariation((byte) 5, "Anvil (South/North)");

    public static BlockVariation ANVIL_WESTEAST = new BlockVariation((byte) 6, "Anvil (West/East)");

    public static BlockVariation SLIGHTLY_DAMAGED_ANVIL_NORTHSOUTH = new BlockVariation((byte) 7, "Slightly Damaged Anvil (North/South)");

    public static BlockVariation SLIGHTLY_DAMAGED_ANVIL_EASTWEST = new BlockVariation((byte) 8, "Slightly Damaged Anvil (East/West)");

    public static BlockVariation SLIGHTLY_DAMAGED_ANVIL_WESTEAST = new BlockVariation((byte) 9, "Slightly Damaged Anvil (West/East)");

    public static BlockVariation SLIGHTLY_DAMAGED_ANVIL_SOUTHNORTH = new BlockVariation((byte) 10, "Slightly Damaged Anvil (South/North)");

    public static BlockVariation VERY_DAMAGED_ANVIL_NORTHSOUTH = new BlockVariation((byte) 11, "Very Damaged Anvil (North/South)");

    public static BlockVariation VERY_DAMAGED_ANVIL_EASTWEST = new BlockVariation((byte) 12, "Very Damaged Anvil (East/West)");

    public static BlockVariation VERY_DAMAGED_ANVIL_WESTEAST = new BlockVariation((byte) 13, "Very Damaged Anvil (West/East)");

    public static BlockVariation VERY_DAMAGED_ANVIL_SOUTHNORTH = new BlockVariation((byte) 14, "Very Damaged Anvil (South/North)");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(15);
        list.add(ANVIL);
        variationsArray[0]= ANVIL;
        list.add(SLIGHTLY_DAMAGED_ANVIL);
        variationsArray[1]= SLIGHTLY_DAMAGED_ANVIL;
        list.add(VERY_DAMAGED_ANVIL);
        variationsArray[2]= VERY_DAMAGED_ANVIL;
        list.add(ANVIL_NORTHSOUTH);
        variationsArray[3]= ANVIL_NORTHSOUTH;
        list.add(ANVIL_EASTWEST);
        variationsArray[4]= ANVIL_EASTWEST;
        list.add(ANVIL_SOUTHNORTH);
        variationsArray[5]= ANVIL_SOUTHNORTH;
        list.add(ANVIL_WESTEAST);
        variationsArray[6]= ANVIL_WESTEAST;
        list.add(SLIGHTLY_DAMAGED_ANVIL_NORTHSOUTH);
        variationsArray[7]= SLIGHTLY_DAMAGED_ANVIL_NORTHSOUTH;
        list.add(SLIGHTLY_DAMAGED_ANVIL_EASTWEST);
        variationsArray[8]= SLIGHTLY_DAMAGED_ANVIL_EASTWEST;
        list.add(SLIGHTLY_DAMAGED_ANVIL_WESTEAST);
        variationsArray[9]= SLIGHTLY_DAMAGED_ANVIL_WESTEAST;
        list.add(SLIGHTLY_DAMAGED_ANVIL_SOUTHNORTH);
        variationsArray[10]= SLIGHTLY_DAMAGED_ANVIL_SOUTHNORTH;
        list.add(VERY_DAMAGED_ANVIL_NORTHSOUTH);
        variationsArray[11]= VERY_DAMAGED_ANVIL_NORTHSOUTH;
        list.add(VERY_DAMAGED_ANVIL_EASTWEST);
        variationsArray[12]= VERY_DAMAGED_ANVIL_EASTWEST;
        list.add(VERY_DAMAGED_ANVIL_WESTEAST);
        variationsArray[13]= VERY_DAMAGED_ANVIL_WESTEAST;
        list.add(VERY_DAMAGED_ANVIL_SOUTHNORTH);
        variationsArray[14]= VERY_DAMAGED_ANVIL_SOUTHNORTH;
        variations = Collections.unmodifiableList(list);
    }
}
