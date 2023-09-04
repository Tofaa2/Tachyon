package net.tachyon.entity.metadata;

public interface LivingEntityMeta extends EntityMeta {

    float getHealth();

    void setHealth(float value);

    int getPotionEffectColor();

    void setPotionEffectColor(int value);

    boolean isPotionEffectAmbient();

    void setPotionEffectAmbient(boolean value);

    byte getArrowCount();

    void setArrowCount(byte value);

}
