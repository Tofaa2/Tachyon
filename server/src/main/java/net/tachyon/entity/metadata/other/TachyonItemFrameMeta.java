package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.coordinate.Rotation;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TachyonItemFrameMeta extends TachyonEntityMeta implements ItemFrameMeta {

    private Orientation orientation;

    public TachyonItemFrameMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
        this.orientation = Orientation.NORTH;
    }

    @NotNull
    public ItemStack getItem() {
        return super.metadata.getIndex((byte) 8, ItemStack.AIR);
    }

    public void setItem(@NotNull ItemStack value) {
        super.metadata.setIndex((byte) 8, Metadata.Slot(value));
    }

    @NotNull
    public Rotation getRotation() {
        return Rotation.values()[super.metadata.getIndex((byte) 9, (byte)0)];
    }

    public void setRotation(@NotNull Rotation value) {
        super.metadata.setIndex((byte) 9, Metadata.Byte((byte)value.ordinal()));
    }

    @NotNull
    public Orientation getOrientation() {
        return this.orientation;
    }

    /**
     * Sets orientation of the item frame.
     * This is possible only before spawn packet is sent.
     *
     * @param orientation the orientation of the item frame.
     */
    public void setOrientation(@NotNull Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public int getObjectData() {
        return this.orientation.ordinal();
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return false;
    }


}
