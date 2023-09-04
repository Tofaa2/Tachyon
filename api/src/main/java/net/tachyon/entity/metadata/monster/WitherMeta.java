package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.Entity;
import net.tachyon.entity.metadata.MobMeta;
import org.jetbrains.annotations.Nullable;

public interface WitherMeta extends MobMeta {

    @Nullable Entity getLeftHead();

    void setLeftHead(@Nullable Entity value);

    @Nullable Entity getRightHead();

    void setRightHead(@Nullable Entity value);

    @Nullable Entity getCenterHead();

    void setCenterHead(@Nullable Entity value);

    int getInvulnerableTime();

    void setInvulnerableTime(int value);

}
