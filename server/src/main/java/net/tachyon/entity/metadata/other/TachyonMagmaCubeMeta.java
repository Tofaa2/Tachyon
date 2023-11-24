package net.tachyon.entity.metadata.other;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.monster.MagmaCubeMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonMagmaCubeMeta extends TachyonSlimeMeta implements MagmaCubeMeta {

    public TachyonMagmaCubeMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
