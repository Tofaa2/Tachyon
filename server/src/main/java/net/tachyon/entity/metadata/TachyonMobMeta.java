package net.tachyon.entity.metadata;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonMobMeta extends TachyonLivingEntityMeta implements MobMeta {

    protected TachyonMobMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isNoAi() {
        return super.metadata.getIndex((byte) 15, false);
    }

    public void setNoAi(boolean value) {
        super.metadata.setIndex((byte) 15, Metadata.Boolean(value));
    }

}
