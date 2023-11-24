package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TachyonWitherMeta extends MonsterMeta implements WitherMeta {

    private Entity centerHead;
    private Entity leftHead;
    private Entity rightHead;

    public TachyonWitherMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @Nullable
    public Entity getCenterHead() {
        return this.centerHead;
    }

    public void setCenterHead(@Nullable Entity value) {
        this.centerHead = value;
        super.metadata.setIndex((byte) 17, Metadata.Int(value == null ? 0 : value.getEntityId()));
    }

    @Nullable
    public Entity getLeftHead() {
        return this.leftHead;
    }

    public void setLeftHead(@Nullable Entity value) {
        this.leftHead = value;
        super.metadata.setIndex((byte) 18, Metadata.Int(value == null ? 0 : value.getEntityId()));
    }

    @Nullable
    public Entity getRightHead() {
        return this.rightHead;
    }

    public void setRightHead(@Nullable Entity value) {
        this.rightHead = value;
        super.metadata.setIndex((byte) 19, Metadata.Int(value == null ? 0 : value.getEntityId()));
    }

    public int getInvulnerableTime() {
        return super.metadata.getIndex((byte) 20, 0);
    }

    public void setInvulnerableTime(int value) {
        super.metadata.setIndex((byte) 20, Metadata.Int(value));
    }

}
