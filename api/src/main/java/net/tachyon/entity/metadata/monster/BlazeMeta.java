package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.metadata.MobMeta;

public interface BlazeMeta extends MobMeta {

    boolean isOnFire();

    void setOnFire(boolean value);

}
