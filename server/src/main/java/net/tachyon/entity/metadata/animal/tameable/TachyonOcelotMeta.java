package net.tachyon.entity.metadata.animal.tameable;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonOcelotMeta extends TachyonTameableAnimalMeta implements OcelotMeta {

    public TachyonOcelotMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Type getType() {
        return Type.VALUES[super.metadata.getIndex((byte) 10, (byte) 0)];
    }

    public void setType(@NotNull Type value) {
        super.metadata.setIndex((byte) 10, Metadata.Byte((byte) value.ordinal()));
    }

}
