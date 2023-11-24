package net.tachyon.entity.ai.goal;

import it.unimi.dsi.fastutil.shorts.Short2ShortArrayMap;
import net.tachyon.coordinate.Vec;
import net.tachyon.entity.EntityCreature;
import net.tachyon.entity.ai.GoalSelector;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EatBlockGoal extends GoalSelector {
    private static final Random RANDOM = new Random();
    private final Short2ShortArrayMap eatBelowMap;
    private final Short2ShortArrayMap eatInMap;
    private final int chancePerTick;
    private int eatAnimationTick;

    /**
     * @param entityCreature Creature that should eat a block.
     * @param eatInMap       Map containing the block IDs that the entity can eat (when inside the block) and the block ID of the replacement block.
     * @param eatBelowMap    Map containing block IDs that the entity can eat (when above the block) and the block ID of the replacement block.
     * @param chancePerTick  The chance (per tick) that the entity eats. Settings this to N would mean there is a 1 in N chance.
     */
    public EatBlockGoal(
            @NotNull EntityCreature entityCreature,
            @NotNull Short2ShortArrayMap eatInMap,
            @NotNull Short2ShortArrayMap eatBelowMap,
            int chancePerTick) {
        super(entityCreature);
        this.eatInMap = eatInMap;
        this.eatBelowMap = eatBelowMap;
        this.chancePerTick = chancePerTick;
    }

    @Override
    public boolean shouldStart() {
        // TODO: is Baby
        if (RANDOM.nextInt(chancePerTick) != 0) {
            return false;
        }

        final World instance = entityCreature.getWorld();

        // An entity shouldn't be eating blocks on null instances.
        if (instance == null) {
            return false;
        }

        final Vec blockPosition = entityCreature.getPosition().toVector();
        final short blockStateIdIn = instance.getBlockStateId(blockPosition.subtract(0, 1, 0));
        final short blockStateIdBelow = instance.getBlockStateId(blockPosition.subtract(0, 2, 0));

        return eatInMap.containsKey(blockStateIdIn) || eatBelowMap.containsKey(blockStateIdBelow);
    }

    @Override
    public void start() {
        this.eatAnimationTick = 40;
        // TODO: EatBlockEvent call here.
        // Stop moving
        entityCreature.getNavigator().setPathTo(null);
    }

    @Override
    public void tick(long time) {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick != 4) {
            return;
        }
        World instance = entityCreature.getWorld();
        final Vec currentPosition = entityCreature.getPosition().toVector().subtract(0, 1, 0);
        final Vec belowPosition = currentPosition.subtract(0, 1, 0);

        final short blockStateIdIn = instance.getBlockStateId(currentPosition);
        final short blockStateIdBelow = instance.getBlockStateId(belowPosition);
        if (eatInMap.containsKey(blockStateIdIn)) {
            instance.setBlockStateId(currentPosition, eatInMap.get(blockStateIdIn));
        } else if (eatBelowMap.containsKey(blockStateIdBelow)) {
            instance.setBlockStateId(belowPosition, eatBelowMap.get(blockStateIdBelow));
        }
        // TODO: Call TachyonEntity Eat Animation
    }

    @Override
    public boolean shouldEnd() {
        return eatAnimationTick <= 0;
    }

    @Override
    public void end() {
        this.eatAnimationTick = 0;
    }
}
