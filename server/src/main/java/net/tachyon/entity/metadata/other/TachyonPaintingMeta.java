package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.coordinate.Direction;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TachyonPaintingMeta extends TachyonEntityMeta implements PaintingMeta {

    private Motive motive = Motive.KEBAB;
    private Direction direction = Direction.SOUTH;

    public TachyonPaintingMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Motive getMotive() {
        return motive;
    }

    /**
     * Sets motive of a painting.
     * This is possible only before spawn packet is sent.
     *
     * @param motive motive of a painting.
     */
    public void setMotive(@NotNull Motive motive) {
        this.motive = motive;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets direction of a painting.
     * This is possible only before spawn packet is sent.
     *
     * @param direction direction of a painting.
     */
    public void setDirection(@NotNull Direction direction) {
        Check.argCondition(direction == Direction.UP || direction == Direction.DOWN, "Painting can't look up or down!");
        this.direction = direction;
    }


}
