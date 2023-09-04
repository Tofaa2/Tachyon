package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonBoatMeta extends TachyonEntityMeta implements BoatMeta {

    public TachyonBoatMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getTimeSinceLastHit() {
        return super.metadata.getIndex((byte) 17, 0);
    }

    public void setTimeSinceLastHit(int value) {
        super.metadata.setIndex((byte) 17, Metadata.Int(value));
    }

    public int getForwardDirection() {
        return super.metadata.getIndex((byte) 18, 1);
    }

    public void setForwardDirection(int value) {
        super.metadata.setIndex((byte) 18, Metadata.Int(value));
    }

    public float getDamageTaken() {
        return super.metadata.getIndex((byte) 19, 0);
    }

    public void setDamageTaken(float value) {
        super.metadata.setIndex((byte) 19, Metadata.Float(value));
    }

}
