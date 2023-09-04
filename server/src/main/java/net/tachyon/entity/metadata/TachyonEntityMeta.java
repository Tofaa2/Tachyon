package net.tachyon.entity.metadata;

import net.kyori.adventure.text.Component;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonEntityMeta implements EntityMeta {

    private final static byte MASK_INDEX = 0;

    private final static byte ON_FIRE_BIT = 0x01;
    private final static byte CROUNCHING_BIT = 0x02;
    private final static byte SPRINTING_BIT = 0x08;
    private final static byte EATING_BIT = 0x10;
    private final static byte INVISIBLE_BIT = 0x20;

    protected final TachyonEntity entity;
    protected final Metadata metadata;

    protected TachyonEntityMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        this.entity = entity;
        this.metadata = metadata;
    }

    /**
     * Sets whether any changes to this meta must result in a metadata packet being sent to entity viewers.
     * By default it's set to true.
     * <p>
     * It's usable if you want to change multiple values of this meta at the same time and want just a
     * single packet being sent: if so, disable notification before your first change and enable it
     * right after the last one: once notification is set to false, we collect all the updates
     * that are being performed, and when it's returned to true we send them all together.
     *
     * @param notifyAboutChanges if to notify entity viewers about this meta changes.
     */
    public void setNotifyAboutChanges(boolean notifyAboutChanges) {
        this.metadata.setNotifyAboutChanges(notifyAboutChanges);
    }

    public boolean isOnFire() {
        return getMaskBit(MASK_INDEX, ON_FIRE_BIT);
    }

    public void setOnFire(boolean value) {
        setMaskBit(MASK_INDEX, ON_FIRE_BIT, value);
    }

    public boolean isSneaking() {
        return getMaskBit(MASK_INDEX, CROUNCHING_BIT);
    }

    public void setSneaking(boolean value) {
        setMaskBit(MASK_INDEX, CROUNCHING_BIT, value);
    }

    public boolean isSprinting() {
        return getMaskBit(MASK_INDEX, SPRINTING_BIT);
    }

    public void setSprinting(boolean value) {
        setMaskBit(MASK_INDEX, SPRINTING_BIT, value);
    }

    public boolean isEating() {
        return getMaskBit(MASK_INDEX, EATING_BIT);
    }

    public void setEating(boolean value) {
        setMaskBit(MASK_INDEX, EATING_BIT, value);
    }

    public boolean isInvisible() {
        return getMaskBit(MASK_INDEX, INVISIBLE_BIT);
    }

    public void setInvisible(boolean value) {
        setMaskBit(MASK_INDEX, INVISIBLE_BIT, value);
    }

    public short getAirTicks() {
        return this.metadata.getIndex((byte) 1, (short) 300);
    }

    public void setAirTicks(short value) {
        this.metadata.setIndex((byte) 1, Metadata.Short(value));
    }

    public Component getCustomName() {
        return this.metadata.getIndex((byte) 2, null);
    }

    public void setCustomName(Component value) {
        this.metadata.setIndex((byte) 2, Metadata.Chat(value));
    }

    public boolean isCustomNameVisible() {
        return this.metadata.getIndex((byte) 3, false);
    }

    public void setCustomNameVisible(boolean value) {
        this.metadata.setIndex((byte) 3, Metadata.Boolean(value));
    }

    public boolean isSilent() {
        return this.metadata.getIndex((byte) 4, false);
    }

    public void setSilent(boolean value) {
        this.metadata.setIndex((byte) 4, Metadata.Boolean(value));
    }

    protected byte getMask(byte index) {
        return this.metadata.getIndex(index, (byte) 0);
    }

    protected void setMask(byte index, byte mask) {
        this.metadata.setIndex(index, Metadata.Byte(mask));
    }

    protected boolean getMaskBit(byte index, byte bit) {
        return (getMask(index) & bit) == bit;
    }

    protected void setMaskBit(byte index, byte bit, boolean value) {
        byte mask = getMask(index);
        boolean currentValue = (mask & bit) == bit;
        if (currentValue == value) {
            return;
        }
        if (value) {
            mask |= bit;
        } else {
            mask &= ~bit;
        }
        setMask(index, mask);
    }

    protected void setBoundingBox(double x, double y, double z) {
        this.entity.setBoundingBox(x, y, z);
    }

    protected void setBoundingBox(double width, double height) {
        setBoundingBox(width, height, width);
    }

}
