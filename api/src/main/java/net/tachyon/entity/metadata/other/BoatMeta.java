package net.tachyon.entity.metadata.other;

import net.tachyon.entity.metadata.EntityMeta;

public interface BoatMeta extends EntityMeta {

    int getTimeSinceLastHit();

    void setTimeSinceLastHit(int value);

    int getForwardDirection();

    void setForwardDirection(int value);

    float getDamageTaken();

    void setDamageTaken(float value);



}
