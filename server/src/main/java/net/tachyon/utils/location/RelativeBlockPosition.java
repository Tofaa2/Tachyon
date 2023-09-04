package net.tachyon.utils.location;

import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a relative {@link Point}.
 *
 * @see RelativeLocation
 */
public class RelativeBlockPosition extends RelativeLocation<Point> {

    public RelativeBlockPosition(Point location, boolean relativeX, boolean relativeY, boolean relativeZ) {
        super(location, relativeX, relativeY, relativeZ);
    }

    @Override
    public Point from(@Nullable Position position) {
        if (!relativeX && !relativeY && !relativeZ) {
            return location;
        }
        final Position entityPosition = position != null ? position : Position.ZERO;

        final int x = location.blockX() + (relativeX ? (int) entityPosition.blockX() : 0);
        final int y = location.blockY() + (relativeY ? (int) entityPosition.blockY() : 0);
        final int z = location.blockZ() + (relativeZ ? (int) entityPosition.blockZ() : 0);

        return new Vec(x, y, z);
    }
}
