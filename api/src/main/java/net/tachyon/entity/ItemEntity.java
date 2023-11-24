package net.tachyon.entity;

import io.netty.util.internal.UnstableApi;
import net.tachyon.item.ItemStack;
import net.tachyon.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;

@UnstableApi
public interface ItemEntity extends Entity {

    int getObjectData();

    @NotNull ItemStack getItemStack();

    void setItemStack(@NotNull ItemStack itemStack);

    boolean isPickable();

    void setPickable(boolean pickable);

    boolean isMergeable();

    void setMergeable(boolean mergeable);

    float getMergeRange();

    void setMergeRange(float mergeRange);

    long getPickupDelay();

    void setPickupDelay(long delay, @NotNull TimeUnit timeUnit);

    long getSpawnTime();

}
