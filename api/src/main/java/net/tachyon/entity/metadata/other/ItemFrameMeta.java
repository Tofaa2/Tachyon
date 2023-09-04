package net.tachyon.entity.metadata.other;

import net.tachyon.coordinate.Rotation;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;

public interface ItemFrameMeta extends EntityMeta, ObjectDataProvider {

    // TODO: Implement Items

    @NotNull Rotation getRotation();

    void setRotation(@NotNull Rotation rotation);

    @NotNull Orientation getOrientation();

    void setOrientation(@NotNull Orientation orientation);

    enum Orientation {
        SOUTH, WEST, NORTH, EAST
    }

}
