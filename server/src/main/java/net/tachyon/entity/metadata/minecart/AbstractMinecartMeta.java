package net.tachyon.entity.metadata.minecart;

import net.tachyon.entity.Entity;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.MinecartMeta;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMinecartMeta extends TachyonEntityMeta implements MinecartMeta {

    protected AbstractMinecartMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getShakingPower() {
        return super.metadata.getIndex((byte) 17, 0);
    }

    public void setShakingPower(int value) {
        super.metadata.setIndex((byte) 17, Metadata.Int(value));
    }

    public int getShakingDirection() {
        return super.metadata.getIndex((byte) 18, 1);
    }

    public void setShakingDirection(int value) {
        super.metadata.setIndex((byte) 18, Metadata.Int(value));
    }

    public float getShakingMultiplier() {
        return super.metadata.getIndex((byte) 19, 0F);
    }

    public void setShakingMultiplier(float value) {
        super.metadata.setIndex((byte) 19, Metadata.Float(value));
    }

    public int getCustomBlockIdAndDamage() {
        return super.metadata.getIndex((byte) 20, 0);
    }

    public void setCustomBlockIdAndDamage(int value) {
        super.metadata.setIndex((byte) 20, Metadata.Int(value));
    }

    // in 16th of a block
    public int getCustomBlockYPosition() {
        return super.metadata.getIndex((byte) 21, 6);
    }

    public void setCustomBlockYPosition(int value) {
        super.metadata.setIndex((byte) 21, Metadata.Int(value));
    }

    public boolean getShowBlock() {
        return super.metadata.getIndex((byte) 22, false);
    }

    public void setShowBlock(boolean value) {
        super.metadata.setIndex((byte) 22, Metadata.Boolean(value));
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }

}
