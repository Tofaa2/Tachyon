package net.tachyon.entity.metadata.minecart;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMinecartContainerMeta extends AbstractMinecartMeta {

    protected AbstractMinecartContainerMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
