package net.tachyon.entity.metadata.monster;

import net.tachyon.entity.metadata.MobMeta;

public interface EndermanMeta extends MobMeta {

    short getCarriedBlockID();

    void setCarriedBlockID(short value);

    byte getCarriedBlockData();

    void setCarriedBlockData(byte value);

    boolean isScreaming();

    void setScreaming(boolean value);

}
