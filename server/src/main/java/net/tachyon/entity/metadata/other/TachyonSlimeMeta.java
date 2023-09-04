package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonMobMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonSlimeMeta extends TachyonMobMeta implements SlimeMeta {

    public TachyonSlimeMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getSize() {
        return super.metadata.getIndex((byte) 16, (byte) 0);
    }

    public void setSize(byte value) {
        float boxSize = 0.51000005f * value;
        setBoundingBox(boxSize, boxSize);
        super.metadata.setIndex((byte) 16, Metadata.Byte(value));
    }

}
