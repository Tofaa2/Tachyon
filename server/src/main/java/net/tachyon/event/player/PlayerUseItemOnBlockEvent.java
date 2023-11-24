package net.tachyon.event.player;

import net.tachyon.coordinate.Point;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.item.ItemStack;
import net.tachyon.coordinate.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * Used when a player is clicking on a block with an item (but is not a block in item form).
 */
public class PlayerUseItemOnBlockEvent extends PlayerEvent {

    private final ItemStack itemStack;
    private final Point position;
    private final Direction blockFace;

    public PlayerUseItemOnBlockEvent(@NotNull TachyonPlayer player,
                                     @NotNull ItemStack itemStack,
                                     @NotNull Point position, @NotNull Direction blockFace) {
        super(player);
        this.itemStack = itemStack;
        this.position = position;
        this.blockFace = blockFace;
    }

    /**
     * Gets the position of the interacted block.
     *
     * @return the block position
     */
    @NotNull
    public Point getPosition() {
        return position;
    }

    /**
     * Gets which face the player has interacted with.
     *
     * @return the block face
     */
    @NotNull
    public Direction getBlockFace() {
        return blockFace;
    }

    /**
     * Gets with which item the player has interacted with the block.
     *
     * @return the item
     */
    @NotNull
    public ItemStack getItemStack() {
        return itemStack;
    }
}
