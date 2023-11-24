package net.tachyon.entity.metadata;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonMobMeta;
import net.tachyon.entity.metadata.animal.SquidMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonSquidMeta extends TachyonMobMeta implements SquidMeta {

    public TachyonSquidMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
