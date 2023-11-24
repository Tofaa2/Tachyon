package net.tachyon.entity.ai.goal;

import net.tachyon.coordinate.Position;
import net.tachyon.entity.Entity;
import net.tachyon.entity.EntityCreature;
import net.tachyon.entity.ai.GoalSelector;
import net.tachyon.entity.ai.TargetSelector;
import net.tachyon.entity.pathfinding.Navigator;
import net.tachyon.utils.time.CooldownUtils;
import net.tachyon.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;

/**
 * Attacks the entity's target ({@link EntityCreature#getTarget()}) OR the closest entity
 * which can be targeted with the entity {@link TargetSelector}.
 */
public class MeleeAttackGoal extends GoalSelector {

    private long lastHit;
    private final int delay;
    private final TimeUnit timeUnit;
    private final int range;

    private boolean stop;
    private Entity cachedTarget;

    /**
     * @param entityCreature the entity to add the goal to
     * @param delay          the delay between each attacks
     * @param range          the allowed range the entity can attack others.
     * @param timeUnit       the unit of the delay
     */
    public MeleeAttackGoal(@NotNull EntityCreature entityCreature, int delay, int range, @NotNull TimeUnit timeUnit) {
        super(entityCreature);
        this.delay = delay;
        this.range = range;
        this.timeUnit = timeUnit;
    }

    @Override
    public boolean shouldStart() {
        this.cachedTarget = findTarget();
        return this.cachedTarget != null;
    }

    @Override
    public void start() {
        final Position targetPosition = this.cachedTarget.getPosition();
        entityCreature.getNavigator().setPathTo(targetPosition);
    }

    @Override
    public void tick(long time) {
        Entity target;
        if (this.cachedTarget != null) {
            target = this.cachedTarget;
            this.cachedTarget = null;
        } else {
            target = findTarget();
        }

        this.stop = target == null;

        if (!stop) {

            // Attack the target entity
            if (entityCreature.getDistance(target) <= range) {
                if (!CooldownUtils.hasCooldown(time, lastHit, timeUnit, delay)) {
                    entityCreature.attack(target, true);
                    this.lastHit = time;
                }
                return;
            }

            // Move toward the target entity
            Navigator navigator = entityCreature.getNavigator();
            final Position pathPosition = navigator.getPathPosition();
            final Position targetPosition = target.getPosition();
            if (pathPosition == null || !pathPosition.isSimilar(targetPosition)) {
                navigator.setPathTo(targetPosition);
            }
        }
    }

    @Override
    public boolean shouldEnd() {
        return stop;
    }

    @Override
    public void end() {
        // Stop following the target
        entityCreature.getNavigator().setPathTo(null);
    }
}
