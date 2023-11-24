package net.tachyon.entity.metadata.golem;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.PathfinderMobMeta;
import org.jetbrains.annotations.NotNull;

public class AbstractGolemMeta extends PathfinderMobMeta {

    protected AbstractGolemMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
