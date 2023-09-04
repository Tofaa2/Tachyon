package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class QuartzBlock {
    public static BlockVariation BLOCK_OF_QUARTZ = new BlockVariation((byte) 0, "Block of Quartz");

    public static BlockVariation CHISELED_QUARTZ_BLOCK = new BlockVariation((byte) 1, "Chiseled Quartz Block");

    public static BlockVariation PILLAR_QUARTZ_BLOCK_VERTICAL = new BlockVariation((byte) 2, "Pillar Quartz Block (vertical)");

    public static BlockVariation PILLAR_QUARTZ_BLOCK_NORTHSOUTH = new BlockVariation((byte) 3, "Pillar Quartz Block (north-south)");

    public static BlockVariation PILLAR_QUARTZ_BLOCK_EASTWEST = new BlockVariation((byte) 4, "Pillar Quartz Block (east-west)");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(5);
        list.add(BLOCK_OF_QUARTZ);
        variationsArray[0]= BLOCK_OF_QUARTZ;
        list.add(CHISELED_QUARTZ_BLOCK);
        variationsArray[1]= CHISELED_QUARTZ_BLOCK;
        list.add(PILLAR_QUARTZ_BLOCK_VERTICAL);
        variationsArray[2]= PILLAR_QUARTZ_BLOCK_VERTICAL;
        list.add(PILLAR_QUARTZ_BLOCK_NORTHSOUTH);
        variationsArray[3]= PILLAR_QUARTZ_BLOCK_NORTHSOUTH;
        list.add(PILLAR_QUARTZ_BLOCK_EASTWEST);
        variationsArray[4]= PILLAR_QUARTZ_BLOCK_EASTWEST;
        variations = Collections.unmodifiableList(list);
    }
}
