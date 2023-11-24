package net.tachyon.entity;

import net.tachyon.coordinate.Position;
import org.jetbrains.annotations.Nullable;

public interface EntityProjectile extends Entity {

    void setNoGravity(boolean noGravity);

    @Nullable Entity getShooter();

    void shoot(Position to, double power, double spread);
}
