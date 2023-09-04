package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonRabbitMeta extends AnimalMeta implements RabbitMeta {

    public TachyonRabbitMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Type getType() {
        int id = super.metadata.getIndex((byte) 18, (byte)0);
        if (id == 99) {
            return Type.KILLER_BUNNY;
        }
        return Type.VALUES[id];
    }

    public void setType(@NotNull Type value) {
        byte id = (byte) (value == Type.KILLER_BUNNY ? 99 : value.ordinal());
        super.metadata.setIndex((byte) 18, Metadata.Byte(id));
    }

}
