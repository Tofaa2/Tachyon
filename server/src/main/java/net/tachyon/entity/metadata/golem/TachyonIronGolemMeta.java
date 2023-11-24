package net.tachyon.entity.metadata.golem;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.IronGolemMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonIronGolemMeta extends AbstractGolemMeta implements IronGolemMeta {

    public TachyonIronGolemMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isPlayerCreated() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setPlayerCreated(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

}
