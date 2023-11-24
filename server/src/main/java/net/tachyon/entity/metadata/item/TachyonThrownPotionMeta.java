package net.tachyon.entity.metadata.item;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonThrownPotionMeta extends TachyonEntityMeta implements ThrownPotionMeta {

    public TachyonThrownPotionMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
