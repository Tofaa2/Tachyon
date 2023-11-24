package net.tachyon.entity.metadata.other;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonMobMeta;
import net.tachyon.entity.metadata.monster.EnderDragonMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonEnderDragonMeta extends TachyonMobMeta implements EnderDragonMeta {

    public TachyonEnderDragonMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }
}
