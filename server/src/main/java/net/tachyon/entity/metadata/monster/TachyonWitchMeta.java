package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonWitchMeta extends MonsterMeta implements WitchMeta {

    public TachyonWitchMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isAggressive() {
        return super.metadata.getIndex((byte) 21, false);
    }

    public void setAggressive(boolean value) {
        super.metadata.setIndex((byte) 21, Metadata.Boolean(value));
    }

}
