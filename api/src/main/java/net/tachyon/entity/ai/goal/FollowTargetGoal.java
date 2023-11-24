package net.tachyon.entity.ai.goal;

import net.tachyon.coordinate.Position;
import net.tachyon.entity.Entity;
import net.tachyon.entity.EntityCreature;
import net.tachyon.entity.ai.GoalSelector;
import net.tachyon.entity.pathfinding.Navigator;
import net.tachyon.utils.MathUtils;
import net.tachyon.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;

public class FollowTargetGoal extends GoalSelector {

    private final UpdateOption pathUpdateOption;
    private long lastUpdateTime = 0;
    private boolean forceEnd = false;
    private Position lastTargetPos;

    /**
     * Creates a follow target goal object.
     *
     * @param entityCreature   the entity
     * @param pathUpdateOption the time between each path update (to check if the target moved)
     */
    public FollowTargetGoal(@NotNull EntityCreature entityCreature, @NotNull UpdateOption pathUpdateOption) {
        super(entityCreature);
        this.pathUpdateOption = pathUpdateOption;
    }

    @Override
    public boolean shouldStart() {
        return entityCreature.getTarget() != null &&
                getDistance(entityCreature.getTarget().getPosition(), entityCreature.getPosition()) >= 2;
    }

    @Override
    public void start() {
        lastUpdateTime = 0;
        forceEnd = false;
        lastTargetPos = null;
        final Entity target = entityCreature.getTarget();

        if (target != null) {
            Navigator navigator = entityCreature.getNavigator();

            lastTargetPos = target.getPosition();
            if (getDistance(lastTargetPos, entityCreature.getPosition()) < 2) {
                forceEnd = true;
                navigator.setPathTo(null);
                return;
            }

            if (navigator.getPathPosition() == null ||
                    (!navigator.getPathPosition().isSimilar(lastTargetPos))) {
                navigator.setPathTo(lastTargetPos);
            } else {
                forceEnd = true;
            }
        } else {
            forceEnd = true;
        }
    }

    @Override
    public void tick(long time) {
        if (forceEnd ||
                pathUpdateOption.getValue() == 0 ||
                pathUpdateOption.getTimeUnit().toMilliseconds(pathUpdateOption.getValue()) + lastUpdateTime > time) {
            return;
        }
        Position targetPos = entityCreature.getTarget() != null ? entityCreature.getTarget().getPosition() : null;
        if (targetPos != null && !targetPos.equals(lastTargetPos)) {
            lastUpdateTime = time;
            lastTargetPos = targetPos;
            entityCreature.getNavigator().setPathTo(targetPos);
        }
    }

    @Override
    public boolean shouldEnd() {
        return forceEnd ||
                entityCreature.getTarget() == null ||
                getDistance(entityCreature.getTarget().getPosition(), entityCreature.getPosition()) < 2;
    }

    @Override
    public void end() {
        entityCreature.getNavigator().setPathTo(null);
    }

    private double getDistance(@NotNull Position a, @NotNull Position b) {
        return MathUtils.square(a.getX() - b.getX()) +
                MathUtils.square(a.getZ() - b.getZ());
    }
}
