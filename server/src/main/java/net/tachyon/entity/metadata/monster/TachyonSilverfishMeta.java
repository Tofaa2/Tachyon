package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonSilverfishMeta extends MonsterMeta implements SilverfishMeta {

    public TachyonSilverfishMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
