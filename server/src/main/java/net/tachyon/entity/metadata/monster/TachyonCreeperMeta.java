package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonCreeperMeta extends MonsterMeta implements CreeperMeta {

    public TachyonCreeperMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public State getState() {
        int id = super.metadata.getIndex((byte) 16, (byte)-1);
        return id == -1 ? State.IDLE : State.FUSE;
    }

    public void setState(@NotNull State value) {
        super.metadata.setIndex((byte) 16, Metadata.Byte((byte) (value == State.IDLE ? -1 : 1)));
    }

    public boolean isPowered() {
        return super.metadata.getIndex((byte) 17, false);
    }

    public void setPowered(boolean value) {
        super.metadata.setIndex((byte) 17, Metadata.Boolean(value));
    }


}
