package net.tachyon.entity.metadata.animal.tameable;

import org.jetbrains.annotations.NotNull;

public interface OcelotMeta extends TameableMeta {

    @NotNull Type getType();

    void setType(@NotNull Type value);


    enum Type {
        NORMAL,
        BLACK,
        RED,
        SIAMESE;

        protected final static Type[] VALUES = values();
    }

}
