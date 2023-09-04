package net.tachyon.entity.metadata.item;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.ProjectileMeta;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireballMeta extends TachyonEntityMeta implements ObjectDataProvider, ProjectileMeta {

    private Entity shooter;

    public FireballMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @Override
    @Nullable
    public Entity getShooter() {
        return shooter;
    }

    @Override
    public void setShooter(@Nullable Entity shooter) {
        this.shooter = shooter;
    }

    @Override
    public int getObjectData() {
        return this.shooter == null ? 0 : this.shooter.getEntityId();
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }

}
