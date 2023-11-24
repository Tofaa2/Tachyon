package net.tachyon.event.item;

import net.tachyon.entity.Player;
import net.tachyon.event.CancellableEvent;
import net.tachyon.event.Event;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemDropEvent extends Event implements CancellableEvent {

    private final Player player;
    private final ItemStack itemStack;

    private boolean cancelled;

    public ItemDropEvent(@NotNull Player player, @NotNull ItemStack itemStack) {
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
