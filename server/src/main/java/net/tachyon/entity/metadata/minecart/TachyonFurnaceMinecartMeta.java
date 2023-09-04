package net.tachyon.entity.metadata.minecart;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.FurnaceMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonFurnaceMinecartMeta extends AbstractMinecartMeta implements FurnaceMeta {

    public TachyonFurnaceMinecartMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isPowered() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setPowered(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

    @Override
    public int getObjectData() {
        return 2;
    }

}
