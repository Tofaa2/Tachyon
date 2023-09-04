package net.tachyon.entity.metadata.animal.tameable;

public interface WolfMeta extends TameableMeta {

    boolean isAngry();

    void setAngry(boolean value);

    float getHealth();

    void setHealth(float value);

    boolean isBegging();

    void setBegging(boolean value);

    byte getCollarColor();

    void setCollarColor(byte value);

}
