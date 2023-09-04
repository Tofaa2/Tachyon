package net.tachyon.entity.metadata;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.BatMeta;
import net.tachyon.entity.metadata.TachyonMobMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonBatMeta extends TachyonMobMeta implements BatMeta {


    public TachyonBatMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isHanging() {
        return metadata.getIndex((byte) 16, false);
    }

    public void setHanging(boolean value) {
        metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

}
