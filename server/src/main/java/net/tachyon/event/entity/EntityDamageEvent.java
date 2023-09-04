package net.tachyon.event.entity;

import net.tachyon.entity.TachyonLivingEntity;
import net.tachyon.entity.damage.DamageType;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called with {@link TachyonLivingEntity#damage(DamageType, float)}.
 */
public class EntityDamageEvent extends EntityEvent implements CancellableEvent {

    private final DamageType damageType;
    private float damage;

    private boolean cancelled;

    public EntityDamageEvent(@NotNull TachyonLivingEntity entity, @NotNull DamageType damageType, float damage) {
        super(entity);
        this.damageType = damageType;
        this.damage = damage;
    }

    @NotNull
    @Override
    public TachyonLivingEntity getEntity() {
        return (TachyonLivingEntity) entity;
    }

    /**
     * Gets the damage type.
     *
     * @return the damage type
     */
    @NotNull
    public DamageType getDamageType() {
        return damageType;
    }

    /**
     * Gets the damage amount.
     *
     * @return the damage amount
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Changes the damage amount.
     *
     * @param damage the new damage amount
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
