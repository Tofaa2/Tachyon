package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class Torch {
    public static BlockVariation TORCH_FACING_EAST_ATTACHED_TO_A_BLOCK_TO_ITS_WEST = new BlockVariation((byte) 0, "Torch facing east (attached to a block to its west)");

    public static BlockVariation TORCH_FACING_WEST_ATTACHED_TO_A_BLOCK_TO_ITS_EAST = new BlockVariation((byte) 1, "Torch facing west (attached to a block to its east)");

    public static BlockVariation TORCH_FACING_SOUTH_ATTACHED_TO_A_BLOCK_TO_ITS_NORTH = new BlockVariation((byte) 2, "Torch facing south (attached to a block to its north)");

    public static BlockVariation TORCH_FACING_NORTH_ATTACHED_TO_A_BLOCK_TO_ITS_SOUTH = new BlockVariation((byte) 3, "Torch facing north (attached to a block to its south)");

    public static BlockVariation TORCH_FACING_UP_ATTACHED_TO_A_BLOCK_BENEATH_IT = new BlockVariation((byte) 4, "Torch facing up (attached to a block beneath it)");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(5);
        list.add(TORCH_FACING_EAST_ATTACHED_TO_A_BLOCK_TO_ITS_WEST);
        variationsArray[0]= TORCH_FACING_EAST_ATTACHED_TO_A_BLOCK_TO_ITS_WEST;
        list.add(TORCH_FACING_WEST_ATTACHED_TO_A_BLOCK_TO_ITS_EAST);
        variationsArray[1]= TORCH_FACING_WEST_ATTACHED_TO_A_BLOCK_TO_ITS_EAST;
        list.add(TORCH_FACING_SOUTH_ATTACHED_TO_A_BLOCK_TO_ITS_NORTH);
        variationsArray[2]= TORCH_FACING_SOUTH_ATTACHED_TO_A_BLOCK_TO_ITS_NORTH;
        list.add(TORCH_FACING_NORTH_ATTACHED_TO_A_BLOCK_TO_ITS_SOUTH);
        variationsArray[3]= TORCH_FACING_NORTH_ATTACHED_TO_A_BLOCK_TO_ITS_SOUTH;
        list.add(TORCH_FACING_UP_ATTACHED_TO_A_BLOCK_BENEATH_IT);
        variationsArray[4]= TORCH_FACING_UP_ATTACHED_TO_A_BLOCK_BENEATH_IT;
        variations = Collections.unmodifiableList(list);
    }
}
