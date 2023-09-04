package net.tachyon.entity.metadata;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.GhastMeta;
import net.tachyon.entity.metadata.TachyonMobMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonGhastMeta extends TachyonMobMeta implements GhastMeta {

    public TachyonGhastMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isAttacking() {
        return super.metadata.getIndex((byte) 15, false);
    }

    public void setAttacking(boolean value) {
        super.metadata.setIndex((byte) 15, Metadata.Boolean(value));
    }

}
