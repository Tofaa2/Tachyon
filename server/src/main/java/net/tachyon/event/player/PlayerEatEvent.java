package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import net.tachyon.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player is finished eating.
 */
public class PlayerEatEvent extends PlayerEvent {

    private final ItemStack foodItem;

    public PlayerEatEvent(@NotNull TachyonPlayer player, @NotNull ItemStack foodItem) {
        super(player);
        this.foodItem = foodItem;
    }

    /**
     * Gets the food item that has been eaten.
     *
     * @return the food item
     */
    @NotNull
    public ItemStack getFoodItem() {
        return foodItem;
    }
}
