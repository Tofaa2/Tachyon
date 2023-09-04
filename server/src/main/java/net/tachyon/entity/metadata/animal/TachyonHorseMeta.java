package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonHorseMeta extends AnimalMeta implements HorseMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte TAMED_BIT = 0x02;
    private final static byte SADDLED_BIT = 0x04;
    private final static byte HAS_CHEST_BIT = 0x08;
    private final static byte HAS_BRED_BIT = 0x10;
    private final static byte EATING_BIT = 0x20;
    private final static byte REARING_BIT = 0x40;
    private final static byte MOUTH_OPEN_BIT = (byte)0x80;

    public TachyonHorseMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isTamed() {
        return getMaskBit(MASK_INDEX, TAMED_BIT);
    }

    public void setTamed(boolean value) {
        setMaskBit(MASK_INDEX, TAMED_BIT, value);
    }

    public boolean isSaddled() {
        return getMaskBit(MASK_INDEX, SADDLED_BIT);
    }

    public void setSaddled(boolean value) {
        setMaskBit(MASK_INDEX, SADDLED_BIT, value);
    }

    public boolean isHasChest() {
        return getMaskBit(MASK_INDEX, HAS_CHEST_BIT);
    }

    public void setHasChest(boolean value) {
        setMaskBit(MASK_INDEX, HAS_CHEST_BIT, value);
    }

    public boolean isHasBred() {
        return getMaskBit(MASK_INDEX, HAS_BRED_BIT);
    }

    public void setHasBred(boolean value) {
        setMaskBit(MASK_INDEX, HAS_BRED_BIT, value);
    }

    public boolean isEating() {
        return getMaskBit(MASK_INDEX, EATING_BIT);
    }

    public void setEating(boolean value) {
        setMaskBit(MASK_INDEX, EATING_BIT, value);
    }

    public boolean isRearing() {
        return getMaskBit(MASK_INDEX, REARING_BIT);
    }

    public void setRearing(boolean value) {
        setMaskBit(MASK_INDEX, REARING_BIT, value);
    }

    public boolean isMouthOpen() {
        return getMaskBit(MASK_INDEX, MOUTH_OPEN_BIT);
    }

    public void setMouthOpen(boolean value) {
        setMaskBit(MASK_INDEX, MOUTH_OPEN_BIT, value);
    }

    @NotNull
    public Type getType() {
        return Type.VALUES[super.metadata.getIndex((byte) 19, (byte) 0)];
    }

    public void setType(@NotNull Type value) {
        super.metadata.setIndex((byte) 19, Metadata.Byte((byte) value.ordinal()));
    }

    public Variant getVariant() {
        return getVariantFromID(super.metadata.getIndex((byte) 20, 0));
    }

    public void setVariant(Variant variant) {
        super.metadata.setIndex((byte) 20, Metadata.Int(getVariantID(variant.marking, variant.color)));
    }

    public static int getVariantID(@NotNull Marking marking, @NotNull Color color) {
        return (marking.ordinal() << 8) + color.ordinal();
    }

    public static Variant getVariantFromID(int variantID) {
        return new Variant(
                Marking.VALUES[variantID >> 8],
                Color.VALUES[variantID & 0xFF]
        );
    }

    public String getOwner() {
        return super.metadata.getIndex((byte) 21, null);
    }

    public void setOwner(String value) {
        super.metadata.setIndex((byte) 21, Metadata.String(value));
    }

    @NotNull
    public Armor getArmor() {
        return Armor.VALUES[super.metadata.getIndex((byte) 22, 0)];
    }

    public void setArmor(@NotNull Armor value) {
        super.metadata.setIndex((byte) 22, Metadata.Int((byte) value.ordinal()));
    }

}
