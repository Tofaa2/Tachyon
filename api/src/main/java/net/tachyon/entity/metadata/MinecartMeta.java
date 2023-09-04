package net.tachyon.entity.metadata;

public interface MinecartMeta extends ObjectDataProvider {

    int getShakingPower();

    void setShakingPower(int value);

    int getShakingDirection();

    void setShakingDirection(int value);

    float getShakingMultiplier();

    void setShakingMultiplier(float value);

    int getCustomBlockIdAndDamage();

    void setCustomBlockIdAndDamage(int value);

    // in 16th of a block
    int getCustomBlockYPosition();

    void setCustomBlockYPosition(int value);

    boolean getShowBlock();

    void setShowBlock(boolean value);



}
