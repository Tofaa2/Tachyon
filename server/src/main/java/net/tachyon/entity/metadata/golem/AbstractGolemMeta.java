package net.tachyon.entity.metadata.golem;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.PathfinderMobMeta;
import org.jetbrains.annotations.NotNull;

public class AbstractGolemMeta extends PathfinderMobMeta {

    protected AbstractGolemMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
