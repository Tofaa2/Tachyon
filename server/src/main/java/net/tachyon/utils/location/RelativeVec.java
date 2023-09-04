package net.tachyon.utils.location;

import net.tachyon.coordinate.Position;
import net.tachyon.coordinate.Vec;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a relative {@link Vec}.
 *
 * @see RelativeLocation
 */
public class RelativeVec extends RelativeLocation<Vec> {

    public RelativeVec(Vec location, boolean relativeX, boolean relativeY, boolean relativeZ) {
        super(location, relativeX, relativeY, relativeZ);
    }

    @Override
    public Vec from(@Nullable Position position) {
        if (!relativeX && !relativeY && !relativeZ) {
            return location;
        }
        final Position entityPosition = position != null ? position : Position.ZERO;

        final double x = location.getX() + (relativeX ? entityPosition.getX() : 0);
        final double y = location.getY() + (relativeY ? entityPosition.getY() : 0);
        final double z = location.getZ() + (relativeZ ? entityPosition.getZ() : 0);

        return new Vec(x, y, z);
    }
}
