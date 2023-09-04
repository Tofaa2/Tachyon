package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonEndCrystalMeta extends TachyonEntityMeta implements EndCrystalMeta {

    public TachyonEndCrystalMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getHealth() {
        return this.metadata.getIndex((byte) 8, (short) 0);
    }

    public void setHealth(int value) {
        this.metadata.setIndex((byte) 8, Metadata.Int(value));
    }

}
