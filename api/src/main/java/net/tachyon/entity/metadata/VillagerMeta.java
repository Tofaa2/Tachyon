package net.tachyon.entity.metadata;

import org.jetbrains.annotations.NotNull;

public interface VillagerMeta extends AgeableMobMeta {


    @NotNull Profession getProfession();

    void setProfession(@NotNull Profession value);

    enum Profession {
        FARMER,
        LIBRARIAN,
        PRIEST,
        BLACKSMITH,
        BUTCHER;

        public final static Profession[] VALUES = values();
    }

}
