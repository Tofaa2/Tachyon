package net.tachyon.entity.metadata;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.ArrowMeta;
import net.tachyon.entity.metadata.ProjectileMeta;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TachyonArrowMeta extends TachyonEntityMeta implements ArrowMeta {

    private Entity shooter;

    public TachyonArrowMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
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

    public boolean isCritical() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setCritical(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

    @Override
    public int getObjectData() {
        return this.shooter == null ? 0 : this.shooter.getEntityId() + 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }

}
