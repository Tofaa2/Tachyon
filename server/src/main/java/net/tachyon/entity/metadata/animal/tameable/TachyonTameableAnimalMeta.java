package net.tachyon.entity.metadata.animal.tameable;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.metadata.animal.AnimalMeta;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonTameableAnimalMeta extends AnimalMeta implements TameableMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte SITTING_BIT = 0x01;
    private final static byte TAMED_BIT = 0x04;

    protected TachyonTameableAnimalMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isSitting() {
        return getMaskBit(MASK_INDEX, SITTING_BIT);
    }

    public void setSitting(boolean value) {
        setMaskBit(MASK_INDEX, SITTING_BIT, value);
    }

    public boolean isTamed() {
        return getMaskBit(MASK_INDEX, TAMED_BIT);
    }

    public void setTamed(boolean value) {
        setMaskBit(MASK_INDEX, TAMED_BIT, value);
    }

    @NotNull
    public String getOwner() {
        return super.metadata.getIndex((byte) 17, null);
    }

    public void setOwner(@NotNull String value) {
        super.metadata.setIndex((byte) 17, Metadata.String(value));
    }

}
