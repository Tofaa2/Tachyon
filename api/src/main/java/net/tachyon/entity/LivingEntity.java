package net.tachyon.entity;

import net.tachyon.attribute.Attribute;
import net.tachyon.attribute.AttributeInstance;
import net.tachyon.coordinate.Point;
import net.tachyon.entity.damage.DamageType;
import net.tachyon.utils.time.TimeUnit;
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

    void heal();

    float getMaxHealth();

    float getHealth();

    void setHealth(float health);

    boolean damage(@NotNull DamageType type, float amount);

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

    /**
     * Sets fire to this entity for a given duration.
     *
     * @param duration duration of the effect
     * @param unit     unit used to express the duration
     * @see #setOnFire(boolean) if you want it to be permanent without any event callback
     */
    void setFireForDuration(int duration, TimeUnit unit);

    /**
     * Changes the delay between two fire damage applications.
     *
     * @param fireDamagePeriod the delay
     * @param timeUnit         the time unit
     */
    void setFireDamagePeriod(long fireDamagePeriod, @NotNull TimeUnit timeUnit);



    /**
     * Sets fire to this entity for a given duration.
     *
     * @param duration duration in ticks of the effect
     */
    void setFireForDuration(int duration);

    AttributeInstance getAttribute(@NotNull Attribute attribute);

    float getAttributeValue(@NotNull Attribute attribute);

}
