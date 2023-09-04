package net.tachyon.entity.metadata.item;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemEntityMeta extends TachyonEntityMeta implements ObjectDataProvider {

    public ItemEntityMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public ItemStack getItem() {
        return super.metadata.getIndex((byte) 10, ItemStack.AIR);
    }

    public void setItem(@NotNull ItemStack item) {
        super.metadata.setIndex((byte) 10, Metadata.Slot(item));
    }

    @Override
    public int getObjectData() {
        return 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }

}