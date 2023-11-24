package net.tachyon.entity.ai.target;

import net.tachyon.entity.Entity;
import net.tachyon.entity.EntityCreature;
import net.tachyon.entity.ai.TargetSelector;
import net.tachyon.entity.damage.DamageType;
import net.tachyon.entity.damage.EntityDamage;
import org.jetbrains.annotations.NotNull;

/**
 * Targets the last damager of this entity.
 */
public class LastEntityDamagerTarget extends TargetSelector {

    private final float range;

    public LastEntityDamagerTarget(@NotNull EntityCreature entityCreature, float range) {
        super(entityCreature);
        this.range = range;
    }

    @Override
    public Entity findTarget() {
        final DamageType damageType = entityCreature.getLastDamageSource();

        if (!(damageType instanceof EntityDamage entityDamage)) {
            // No damager recorded, return null
            return null;
        }

        final Entity entity = entityDamage.getSource();

        if (entity.isRemoved()) {
            // TachyonEntity not valid
            return null;
        }

        // Check range
        return entityCreature.getDistance(entity) < range ? entity : null;
    }
}
