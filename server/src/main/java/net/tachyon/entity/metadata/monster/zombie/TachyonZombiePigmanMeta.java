package net.tachyon.entity.metadata.monster.zombie;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.monster.ZombiePigmanMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonZombiePigmanMeta extends TachyonZombieMeta implements ZombiePigmanMeta {

    public TachyonZombiePigmanMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

}
