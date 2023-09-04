package net.tachyon.entity.metadata;

public interface PlayerMeta extends LivingEntityMeta {


    float getAdditionalHearts();

    void setAdditionalHearts(float value);

    int getScore();

    void setScore(int value);

    boolean isCapeEnabled();

    void setCapeEnabled(boolean value);

    boolean isJacketEnabled();

    void setJacketEnabled(boolean value);

    boolean isLeftSleeveEnabled();

    void setLeftSleeveEnabled(boolean value);

    boolean isRightSleeveEnabled();

    void setRightSleeveEnabled(boolean value);

    boolean isLeftLegEnabled();

    void setLeftLegEnabled(boolean value);

    boolean isRightLegEnabled();

    void setRightLegEnabled(boolean value);

    boolean isHatEnabled();

    void setHatEnabled(boolean value);


}
