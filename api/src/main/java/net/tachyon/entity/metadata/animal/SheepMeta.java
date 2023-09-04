package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.metadata.AgeableMobMeta;

public interface SheepMeta extends AgeableMobMeta {

    int getColor();

    void setColor(byte color);

    boolean isSheared();

    void setSheared(boolean value);

}
