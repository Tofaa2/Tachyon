package net.tachyon.entity.metadata;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class TachyonLivingEntityMeta extends TachyonEntityMeta implements LivingEntityMeta {
    protected TachyonLivingEntityMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public float getHealth() {
        return super.metadata.getIndex((byte) 6, 1F);
    }

    public void setHealth(float value) {
        super.metadata.setIndex((byte) 6, Metadata.Float(value));
    }

    public int getPotionEffectColor() {
        return super.metadata.getIndex((byte) 7, 0);
    }

    public void setPotionEffectColor(int value) {
        super.metadata.setIndex((byte) 7, Metadata.Int(value));
    }

    public boolean isPotionEffectAmbient() {
        return super.metadata.getIndex((byte) 8, false);
    }

    public void setPotionEffectAmbient(boolean value) {
        super.metadata.setIndex((byte) 8, Metadata.Boolean(value));
    }

    public byte getArrowCount() {
        return super.metadata.getIndex((byte) 9, (byte) 0);
    }

    public void setArrowCount(byte value) {
        super.metadata.getIndex((byte) 9, Metadata.Byte(value));
    }

}
