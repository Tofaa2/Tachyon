package net.tachyon.entity;

import net.tachyon.coordinate.Position;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link TachyonEntityMeta} that inherits
 * {@link ObjectDataProvider} instead.
 */
@Deprecated
public abstract class ObjectEntity extends TachyonEntity {

    public ObjectEntity(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        super(entityType, spawnPosition);
        setGravity(0.02f, 0.04f, 1.96f);
    }

    /**
     * Gets the data of this object entity.
     *
     * @return an object data
     * @see <a href="https://wiki.vg/Object_Data">here</a>
     */
    public abstract int getObjectData();

    @Override
    public void update(long time) {

    }

    @Override
    public void spawn() {

    }
}
