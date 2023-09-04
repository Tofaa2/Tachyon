package net.tachyon.entity.metadata.other;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TachyonFishingHookMeta extends TachyonEntityMeta implements FishingHookMeta {

    private Entity thrower;

    public TachyonFishingHookMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @Nullable
    public Entity getThrower() {
        return this.thrower;
    }

    public void setThrower(@Nullable Entity value) {
        this.thrower = value;
    }

    @Override
    public int getObjectData() {
        return this.thrower == null ? 0 : this.thrower.getEntityId() + 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}
