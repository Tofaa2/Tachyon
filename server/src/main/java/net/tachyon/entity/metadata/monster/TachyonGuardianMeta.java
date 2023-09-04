package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonGuardianMeta extends MonsterMeta implements GuardianMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte IS_ELDERLY = 0x01;
    private final static byte IS_RETRACTING_SPIKES = 0x02;

    private TachyonEntity target;

    public TachyonGuardianMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isElderly() {
        return getMaskBit(MASK_INDEX, IS_ELDERLY);
    }

    public void setElderly(boolean value) {
        setMaskBit(MASK_INDEX, IS_ELDERLY, value);
    }

    public boolean isRetractingSpikes() {
        return getMaskBit(MASK_INDEX, IS_RETRACTING_SPIKES);
    }

    public void setRetractingSpikes(boolean value) {
        setMaskBit(MASK_INDEX, IS_RETRACTING_SPIKES, value);
    }

    public TachyonEntity getTarget() {
        return this.target;
    }

    public void setTarget(@NotNull TachyonEntity target) {
        this.target = target;
        super.metadata.setIndex((byte) 17, Metadata.Int(target.getEntityId()));
    }

}
