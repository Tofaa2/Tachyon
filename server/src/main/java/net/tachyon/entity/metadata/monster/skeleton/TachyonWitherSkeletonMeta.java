package net.tachyon.entity.metadata.monster.skeleton;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.monster.WitherSkeletonMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonWitherSkeletonMeta extends AbstractSkeletonMeta implements WitherSkeletonMeta {

    public TachyonWitherSkeletonMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
