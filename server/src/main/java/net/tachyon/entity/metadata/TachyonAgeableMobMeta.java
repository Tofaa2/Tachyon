package net.tachyon.entity.metadata;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonAgeableMobMeta extends PathfinderMobMeta implements AgeableMobMeta {

    protected TachyonAgeableMobMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public byte getAge() {
        return super.metadata.getIndex((byte) 12, (byte) 0);
    }

    public void setAge(byte age) {
        super.metadata.setIndex((byte) 12, Metadata.Byte(age));
    }

}
