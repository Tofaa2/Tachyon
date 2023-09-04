package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.metadata.MobMeta;

public interface SpiderMeta extends MobMeta {

    boolean isClimbing();

    void setClimbing(boolean value);

}
