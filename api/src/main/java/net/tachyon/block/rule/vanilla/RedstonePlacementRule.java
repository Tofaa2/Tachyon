package net.tachyon.block.rule.vanilla;

import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.BlockFace;
import net.tachyon.entity.Player;
import net.tachyon.instance.Instance;
import net.tachyon.block.Block;
import net.tachyon.block.rule.BlockPlacementRule;
import net.tachyon.utils.block.BlockUtils;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;

public class RedstonePlacementRule extends BlockPlacementRule {

    public RedstonePlacementRule() {
        super(Block.REDSTONE_WIRE);
    }

    @Override
    public short blockUpdate(@NotNull World instance, @NotNull Point blockPosition, short currentId) {
        BlockUtils block = new BlockUtils(instance, blockPosition);

        String east = "none";
        String north = "none";
        String power = "0";
        String south = "none";
        String west = "none";

        // TODO Block should have method isRedstone, as redstone connects to more than itself.

        final BlockUtils blockNorth = block.north();
        final BlockUtils blockSouth = block.south();
        final BlockUtils blockEast = block.east();
        final BlockUtils blockWest = block.west();
        int connected = 0;

        if (blockNorth.equals(Block.REDSTONE_WIRE) || blockNorth.below().equals(Block.REDSTONE_WIRE)) {
            connected++;
            north = "side";
        }
        if (blockSouth.equals(Block.REDSTONE_WIRE) || blockSouth.below().equals(Block.REDSTONE_WIRE)) {
            connected++;
            south = "side";
        }
        if (blockEast.equals(Block.REDSTONE_WIRE) || blockEast.below().equals(Block.REDSTONE_WIRE)) {
            connected++;
            east = "side";
        }
        if (blockWest.equals(Block.REDSTONE_WIRE) || blockWest.below().equals(Block.REDSTONE_WIRE)) {
            connected++;
            west = "side";
        }
        if (blockNorth.above().equals(Block.REDSTONE_WIRE)) {
            connected++;
            north = "up";
        }
        if (blockSouth.above().equals(Block.REDSTONE_WIRE)) {
            connected++;
            south = "up";
        }
        if (blockEast.above().equals(Block.REDSTONE_WIRE)) {
            connected++;
            east = "up";
        }
        if (blockWest.above().equals(Block.REDSTONE_WIRE)) {
            connected++;
            west = "up";
        }
        if (connected == 0) {
            north = "side";
            south = "side";
            east = "side";
            west = "side";
        } else if (connected == 1) {
            if (!north.equals("none")) {
                south = "side";
            }
            if (!south.equals("none")) {
                north = "side";
            }
            if (!east.equals("none")) {
                west = "side";
            }
            if (!west.equals("none")) {
                east = "side";
            }
        }

        // TODO power

        final String[] properties = {
                "east=" + east,
                "north=" + north,
                "power=" + power,
                "south=" + south,
                "west=" + west};

        // TODO(koesie10: Fix placement rules
        return 0;
//        return Block.REDSTONE_WIRE.withProperties(properties);
    }

    @Override
    public short blockPlace(@NotNull World instance,
                            @NotNull Block block, @NotNull BlockFace blockFace, @NotNull Point blockPosition,
                            @NotNull Player pl) {
        final short belowBlockId = instance.getBlockStateId(blockPosition.blockX(), blockPosition.blockY() - 1, blockPosition.blockZ());
        if (!Block.fromStateId(belowBlockId).isSolid()) {
            return CANCEL_CODE;
        }

        return getBlockId();
    }

}
