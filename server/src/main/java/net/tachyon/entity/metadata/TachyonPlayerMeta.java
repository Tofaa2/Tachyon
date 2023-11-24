package net.tachyon.entity.metadata;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonPlayerMeta extends TachyonLivingEntityMeta implements PlayerMeta {

    private final static byte MASK_INDEX = 10;

    private final static byte CAPE_BIT = 0x01;
    private final static byte JACKET_BIT = 0x02;
    private final static byte LEFT_SLEEVE_BIT = 0x04;
    private final static byte RIGHT_SLEEVE_BIT = 0x08;
    private final static byte LEFT_LEG_BIT = 0x10;
    private final static byte RIGHT_LEG_BIT = 0x20;
    private final static byte HAT_BIT = 0x40;

    public TachyonPlayerMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public float getAdditionalHearts() {
        return super.metadata.getIndex((byte) 17, 0F);
    }

    public void setAdditionalHearts(float value) {
        super.metadata.setIndex((byte) 17, Metadata.Float(value));
    }

    public int getScore() {
        return super.metadata.getIndex((byte) 18, 0);
    }

    public void setScore(int value) {
        super.metadata.setIndex((byte) 18, Metadata.Int(value));
    }

    public boolean isCapeEnabled() {
        return getMaskBit(MASK_INDEX, CAPE_BIT);
    }

    public void setCapeEnabled(boolean value) {
        setMaskBit(MASK_INDEX, CAPE_BIT, value);
    }

    public boolean isJacketEnabled() {
        return getMaskBit(MASK_INDEX, JACKET_BIT);
    }

    public void setJacketEnabled(boolean value) {
        setMaskBit(MASK_INDEX, JACKET_BIT, value);
    }

    public boolean isLeftSleeveEnabled() {
        return getMaskBit(MASK_INDEX, LEFT_SLEEVE_BIT);
    }

    public void setLeftSleeveEnabled(boolean value) {
        setMaskBit(MASK_INDEX, LEFT_SLEEVE_BIT, value);
    }

    public boolean isRightSleeveEnabled() {
        return getMaskBit(MASK_INDEX, RIGHT_SLEEVE_BIT);
    }

    public void setRightSleeveEnabled(boolean value) {
        setMaskBit(MASK_INDEX, RIGHT_SLEEVE_BIT, value);
    }

    public boolean isLeftLegEnabled() {
        return getMaskBit(MASK_INDEX, LEFT_LEG_BIT);
    }

    public void setLeftLegEnabled(boolean value) {
        setMaskBit(MASK_INDEX, LEFT_LEG_BIT, value);
    }

    public boolean isRightLegEnabled() {
        return getMaskBit(MASK_INDEX, RIGHT_LEG_BIT);
    }

    public void setRightLegEnabled(boolean value) {
        setMaskBit(MASK_INDEX, RIGHT_LEG_BIT, value);
    }

    public boolean isHatEnabled() {
        return getMaskBit(MASK_INDEX, HAT_BIT);
    }

    public void setHatEnabled(boolean value) {
        setMaskBit(MASK_INDEX, HAT_BIT, value);
    }

}
