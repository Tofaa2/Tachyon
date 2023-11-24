package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonAgeableMobMeta;
import org.jetbrains.annotations.NotNull;

public class AnimalMeta extends TachyonAgeableMobMeta {

    protected AnimalMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
