package net.tachyon.entity.metadata.golem;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.animal.SnowGolemMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonSnowGolemMeta extends AbstractGolemMeta implements SnowGolemMeta {

    public TachyonSnowGolemMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
