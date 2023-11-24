package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonPigMeta extends AnimalMeta implements PigMeta {

    public TachyonPigMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isHasSaddle() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setHasSaddle(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

}
