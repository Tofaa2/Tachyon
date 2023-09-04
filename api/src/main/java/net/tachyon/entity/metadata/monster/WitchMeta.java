package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.metadata.MobMeta;

public interface WitchMeta extends MobMeta {

    boolean isAggressive();

    void setAggressive(boolean value);

}
