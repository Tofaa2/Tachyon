package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.metadata.MobMeta;

public interface CreeperMeta extends MobMeta {

    boolean isPowered();

    void setPowered(boolean value);

    enum State {
        IDLE,
        FUSE
    }

}
