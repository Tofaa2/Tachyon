package net.tachyon.entity.metadata.other;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.metadata.TachyonEntityMeta;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TachyonFireworkRocketMeta extends TachyonEntityMeta implements FireworkRocketMeta{

    private TachyonEntity shooter;

    public TachyonFireworkRocketMeta(@NotNull TachyonEntity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public ItemStack getFireworkInfo() {
        return super.metadata.getIndex((byte) 8, ItemStack.AIR);
    }

    public void setFireworkInfo(@NotNull ItemStack value) {
        super.metadata.setIndex((byte) 8, Metadata.Slot(value));
    }

}
