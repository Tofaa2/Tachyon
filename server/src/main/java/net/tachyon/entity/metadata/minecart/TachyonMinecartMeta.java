package net.tachyon.entity.metadata.minecart;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonMinecartMeta extends AbstractMinecartMeta {

    public TachyonMinecartMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @Override
    public int getObjectData() {
        return 0;
    }

}
