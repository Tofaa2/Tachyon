package net.tachyon.entity;

import net.tachyon.entity.ai.EntityAI;
import net.tachyon.entity.damage.DamageType;
import net.tachyon.entity.pathfinding.NavigableEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityCreature extends LivingEntity, NavigableEntity, EntityAI {

    int getRemovalAnimationDelay();

    void setRemovalAnimationDelay(int removalAnimationDelay);

    @Nullable DamageType getLastDamageSource();

    @Nullable Entity getTarget();

    void setTarget(@Nullable Entity target);

    void attack(@NotNull Entity target, boolean swingHand);

    void attack(@NotNull Entity target);

}
