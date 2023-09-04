package net.tachyon.utils.position;

import net.tachyon.coordinate.Point;
import net.tachyon.coordinate.Position;
import org.jetbrains.annotations.NotNull;

public final class PositionUtils {

    public static Position lookAlong(@NotNull Point position, double dx, double dy, double dz) {
        final double horizontalAngle = Math.atan2(dz, dx);
        final float yaw = (float) (horizontalAngle * (180.0 / Math.PI)) - 90;
        final float pitch = (float) Math.atan2(dy, Math.max(Math.abs(dx), Math.abs(dz)));

        return new Position(position.x(), position.y(), position.z(), yaw, pitch);
    }

}
