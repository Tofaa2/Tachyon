package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.metadata.AgeableMobMeta;
import org.jetbrains.annotations.NotNull;

public interface RabbitMeta extends AgeableMobMeta {


    @NotNull Type getType();

    void setType(@NotNull Type value);

    enum Type {
        BROWN,
        WHITE,
        BLACK,
        BLACK_AND_WHITE,
        GOLD,
        SALT_AND_PEPPER,
        KILLER_BUNNY;

        protected final static Type[] VALUES = values();
    }

}
