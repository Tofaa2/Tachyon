package net.tachyon.entity.metadata.other;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonLeashKnotMeta extends TachyonEntityMeta implements LeashKnotMeta {

    public TachyonLeashKnotMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
