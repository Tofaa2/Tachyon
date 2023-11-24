package net.tachyon.entity.metadata.item;

import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.entity.metadata.ObjectDataProvider;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemEntityMeta extends EntityMeta, ObjectDataProvider {


    @NotNull ItemStack getItem();

    void setItem(@NotNull ItemStack item);
}


