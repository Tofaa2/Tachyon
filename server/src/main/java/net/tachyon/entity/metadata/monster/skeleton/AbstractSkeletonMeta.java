package net.tachyon.entity.metadata.monster.skeleton;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.metadata.monster.MonsterMeta;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AbstractSkeletonMeta extends MonsterMeta {

    protected AbstractSkeletonMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
