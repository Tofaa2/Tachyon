package net.tachyon.block.rule.vanilla;

import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.BlockFace;
import net.tachyon.entity.Player;
import net.tachyon.block.Block;
import net.tachyon.block.rule.BlockPlacementRule;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;

public class AxisPlacementRule extends BlockPlacementRule {

    protected final Block block;

    public AxisPlacementRule(Block block) {
        super(block);
        this.block = block;
    }

    @Override
    public short blockUpdate(@NotNull World instance, @NotNull Point blockPosition, short currentId) {
        return currentId;
    }

    @Override
    public short blockPlace(@NotNull World instance,
                            @NotNull Block block, @NotNull BlockFace blockFace, @NotNull Point blockPosition,
                            @NotNull Player pl) {
        String axis = "y";
        if (blockFace == BlockFace.WEST || blockFace == BlockFace.EAST) {
            axis = "x";
        } else if (blockFace == BlockFace.SOUTH || blockFace == BlockFace.NORTH) {
            axis = "z";
        }
        // TODO: Fix placement rules
        return 0;
    }

}
