package net.tachyon.entity;

import net.tachyon.coordinate.Point;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LivingEntity extends Entity {


    void swingHand();

    boolean canPickupItems();

    void setCanPickupItems(boolean canPickupItems);

    boolean isDead();

    boolean isInvulnerable();

    void setInvulnerable(boolean invulnerable);

    void kill();

    /**
     * Gets the amount of arrows in the entity.
     */
    byte getArrowCount();

    /**
     * Changes the amount of arrow stuck in the entity.
     * @param arrowCount the new amount of arrows
     */
    void setArrowCount(byte arrowCount);

    /**
     * Checks whether the current entity has line of sight to the given one.
     * If so, it doesn't mean that the given entity is IN line of sight of the current,
     * but the current one can rotate so that it will be true.
     *
     * @param entity the entity to be checked.
     * @return if the current entity has line of sight to the given one.
     */
    boolean hasLineOfSight(@NotNull Entity entity);

    /**
     * Gets the target (not-air) {@link Point} of the entity.
     *
     * @param maxDistance The max distance to scan before returning null
     * @return The {@link Point} targeted by this entity, null if non are found
     */
    @Nullable Point getTargetBlockPosition(int maxDistance);

}
