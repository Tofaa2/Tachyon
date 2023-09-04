package net.tachyon.entity.metadata.animal.tameable;

import net.tachyon.entity.metadata.AgeableMobMeta;
import org.jetbrains.annotations.NotNull;

public interface TameableMeta extends AgeableMobMeta {

    boolean isSitting();

    void setSitting(boolean value);

    boolean isTamed();

    void setTamed(boolean value);

    @NotNull String getOwner();

    void setOwner(@NotNull String value);

}
