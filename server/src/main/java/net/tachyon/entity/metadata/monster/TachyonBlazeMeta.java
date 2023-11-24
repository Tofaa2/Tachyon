package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonBlazeMeta extends MonsterMeta implements BlazeMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte ON_FIRE_BIT = 0x01;

    public TachyonBlazeMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isOnFire() {
        return getMaskBit(MASK_INDEX, ON_FIRE_BIT);
    }

    public void setOnFire(boolean value) {
        setMaskBit(MASK_INDEX, ON_FIRE_BIT, value);
    }

}
