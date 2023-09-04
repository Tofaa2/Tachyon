package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonSpiderMeta extends MonsterMeta implements SpiderMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte CLIMBING_BIT = 0x01;

    public TachyonSpiderMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isClimbing() {
        return getMaskBit(MASK_INDEX, CLIMBING_BIT);
    }

    public void setClimbing(boolean value) {
        setMaskBit(MASK_INDEX, CLIMBING_BIT, value);
    }

}
