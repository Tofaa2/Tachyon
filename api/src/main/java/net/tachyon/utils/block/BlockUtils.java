package net.tachyon.utils.block;

import net.tachyon.coordinate.Point;
import net.tachyon.block.Block;
import net.tachyon.world.World;

public class BlockUtils {

    private final World instance;
    private final Point position;

    public BlockUtils(World instance, Point position) {
        this.instance = instance;
        this.position = position;
    }

    public BlockUtils getRelativeTo(int x, int y, int z) {
        Point position = this.position.add(x, y, z);
        return new BlockUtils(instance, position);
    }

    public BlockUtils above() {
        return getRelativeTo(0, 1, 0);
    }

    public BlockUtils below() {
        return getRelativeTo(0, -1, 0);
    }

    public BlockUtils north() {
        return getRelativeTo(0, 0, -1);
    }

    public BlockUtils east() {
        return getRelativeTo(1, 0, 0);
    }

    public BlockUtils south() {
        return getRelativeTo(0, 0, 1);
    }

    public BlockUtils west() {
        return getRelativeTo(-1, 0, 0);
    }

    public Block getBlock() {
        return Block.fromStateId(instance.getBlockStateId(position));
    }

    public boolean equals(Block block) {
        return getBlock() == block;
    }
}
