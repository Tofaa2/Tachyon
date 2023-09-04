package net.tachyon.event.item;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.Event;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemUpdateStateEvent extends Event {

    private final TachyonPlayer player;
    private final ItemStack itemStack;
    private boolean handAnimation;

    public ItemUpdateStateEvent(@NotNull TachyonPlayer player, @NotNull ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
    }

    @NotNull
    public TachyonPlayer getPlayer() {
        return player;
    }

    @NotNull
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setHandAnimation(boolean handAnimation) {
        this.handAnimation = handAnimation;
    }

    public boolean hasHandAnimation() {
        return handAnimation;
    }
}
