package net.tachyon.entity.metadata.villager;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonAgeableMobMeta;
import net.tachyon.entity.metadata.VillagerMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonVillagerMeta extends TachyonAgeableMobMeta implements VillagerMeta {

    public TachyonVillagerMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Profession getProfession() {
        return Profession.VALUES[super.metadata.getIndex((byte) 16, (byte) 0)];
    }

    public void setProfession(@NotNull Profession value) {
        super.metadata.setIndex((byte) 16, Metadata.Byte((byte) value.ordinal()));
    }

}
