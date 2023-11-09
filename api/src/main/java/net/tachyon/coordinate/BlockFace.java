package net.tachyon.coordinate;

import org.jetbrains.annotations.NotNull;

public enum BlockFace {
    BOTTOM(Direction.DOWN),
    TOP(Direction.UP),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST);

    private final Direction direction;

    BlockFace(Direction direction) {
        this.direction = direction;
    }

    public Direction toDirection() {
        return direction;
    }

    @NotNull
    public BlockFace getOppositeFace() {
        return switch (this) {
            case BOTTOM -> TOP;
            case TOP -> BOTTOM;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case EAST -> WEST;
        };
    }
}
