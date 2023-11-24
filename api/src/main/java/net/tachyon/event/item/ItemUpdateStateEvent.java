package net.tachyon.event.item;

import net.tachyon.entity.Player;
import net.tachyon.event.types.Event;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemUpdateStateEvent extends Event {

    private final Player player;
    private final ItemStack itemStack;
    private boolean handAnimation;

    public ItemUpdateStateEvent(@NotNull Player player, @NotNull ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
    }

    @NotNull
    public Player getPlayer() {
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
