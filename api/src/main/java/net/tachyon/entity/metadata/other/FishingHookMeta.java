package net.tachyon.entity.metadata.other;

import net.tachyon.entity.Entity;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.Nullable;

public interface FishingHookMeta extends ObjectDataProvider, EntityMeta {

    @Nullable Entity getThrower();

    void setThrower(@Nullable Entity thrower);

}
