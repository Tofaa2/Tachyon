package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.PathfinderMobMeta;
import org.jetbrains.annotations.NotNull;

public class MonsterMeta extends PathfinderMobMeta {

    protected MonsterMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
