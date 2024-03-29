package net.tachyon.event.entity;

import net.tachyon.coordinate.Position;
import net.tachyon.entity.Entity;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.event.types.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called with {@link net.tachyon.entity.EntityProjectile#shoot(Position, double, double)}
 */
public class EntityShootEvent extends EntityEvent implements CancellableEvent {

    private final Entity projectile;
    private final Position to;
    private double power;
    private double spread;

    private boolean cancelled;

    public EntityShootEvent(@NotNull Entity entity, @NotNull Entity projectile, @NotNull Position to, double power, double spread) {
        super(entity);
        this.projectile = projectile;
        this.to = to;
        this.power = power;
        this.spread = spread;
    }

    /**
     * Gets the projectile.
     *
     * @return the projectile.
     */
    public Entity getProjectile() {
        return this.projectile;
    }

    /**
     * Gets the position projectile was shot to.
     *
     * @return the position projectile was shot to.
     */
    public Position getTo() {
        return this.to;
    }

    /**
     * Gets shot spread.
     *
     * @return shot spread.
     */
    public double getSpread() {
        return this.spread;
    }

    /**
     * Sets shot spread.
     *
     * @param spread shot spread.
     */
    public void setSpread(double spread) {
        this.spread = spread;
    }

    /**
     * Gets shot power.
     *
     * @return shot power.
     */
    public double getPower() {
        return this.power;
    }

    /**
     * Sets shot power.
     *
     * @param power shot power.
     */
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}
