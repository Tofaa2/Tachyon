package net.tachyon.entity.metadata.monster.skeleton;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.monster.SkeletonMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonSkeletonMeta extends AbstractSkeletonMeta implements SkeletonMeta {

    public TachyonSkeletonMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
