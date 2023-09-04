package net.tachyon.entity.metadata.other;

import net.tachyon.coordinate.Vec;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonLivingEntityMeta;
import org.jetbrains.annotations.NotNull;

public class TachyonArmorStandMeta extends TachyonLivingEntityMeta {

    private final static byte MASK_INDEX = 10;

    private final static byte IS_SMALL_BIT = 0x01;
    private final static byte HAS_GRAVITY_BIT = 0x02;
    private final static byte HAS_ARMS_BIT = 0x04;
    private final static byte HAS_NO_BASE_PLATE_BIT = 0x08;
    private final static byte IS_MARKER_BIT = 0x10;

    public TachyonArmorStandMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isSmall() {
        return getMaskBit(MASK_INDEX, IS_SMALL_BIT);
    }

    public void setSmall(boolean value) {
        setMaskBit(MASK_INDEX, IS_SMALL_BIT, value);
    }

    public boolean isHasGravity() {
        return getMaskBit(MASK_INDEX, HAS_GRAVITY_BIT);
    }

    public void setHasGravity(boolean value) {
        setMaskBit(MASK_INDEX, HAS_GRAVITY_BIT, value);
    }

    public boolean isHasArms() {
        return getMaskBit(MASK_INDEX, HAS_ARMS_BIT);
    }

    public void setHasArms(boolean value) {
        setMaskBit(MASK_INDEX, HAS_ARMS_BIT, value);
    }

    public boolean isHasNoBasePlate() {
        return getMaskBit(MASK_INDEX, HAS_NO_BASE_PLATE_BIT);
    }

    public void setHasNoBasePlate(boolean value) {
        setMaskBit(MASK_INDEX, HAS_NO_BASE_PLATE_BIT, value);
    }

    public boolean isMarker() {
        return getMaskBit(MASK_INDEX, IS_MARKER_BIT);
    }

    public void setMarker(boolean value) {
        setMaskBit(MASK_INDEX, IS_MARKER_BIT, value);
    }

    @NotNull
    public Vec getHeadRotation() {
        return super.metadata.getIndex((byte) 11, new Vec(0D, 0D, 0D));
    }

    public void setHeadRotation(@NotNull Vec value) {
        super.metadata.setIndex((byte) 11, Metadata.Vector(value));
    }

    @NotNull
    public Vec getBodyRotation() {
        return super.metadata.getIndex((byte) 12, new Vec(0D, 0D, 0D));
    }

    public void setBodyRotation(@NotNull Vec value) {
        super.metadata.setIndex((byte) 12, Metadata.Vector(value));
    }

    @NotNull
    public Vec getLeftArmRotation() {
        return super.metadata.getIndex((byte) 13, new Vec(-10D, 0D, -10D));
    }

    public void setLeftArmRotation(@NotNull Vec value) {
        super.metadata.setIndex((byte) 13, Metadata.Vector(value));
    }

    @NotNull
    public Vec getRightArmRotation() {
        return super.metadata.getIndex((byte) 14, new Vec(-15D, 0D, 10D));
    }

    public void setRightArmRotation(@NotNull Vec value) {
        super.metadata.setIndex((byte) 14, Metadata.Vector(value));
    }

    @NotNull
    public Vec getLeftLegRotation() {
        return super.metadata.getIndex((byte) 15, new Vec(-1D, 0D, -1D));
    }

    public void setLeftLegRotation(@NotNull Vec value) {
        super.metadata.setIndex((byte) 15, Metadata.Vector(value));
    }

    @NotNull
    public Vec getRightLegRotation() {
        return super.metadata.getIndex((byte) 16, new Vec(1D, 0D, 1D));
    }

    public void setRightLegRotation(@NotNull Vec value) {
        super.metadata.setIndex((byte) 16, Metadata.Vector(value));
    }

}
