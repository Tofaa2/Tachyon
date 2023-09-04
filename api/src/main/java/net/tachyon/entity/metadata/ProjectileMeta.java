package net.tachyon.entity.metadata;

import net.tachyon.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface ProjectileMeta extends EntityMeta {

    @Nullable Entity getShooter();

    void setShooter(@Nullable Entity shooter);

}
