package net.tachyon.event.player;

import net.tachyon.coordinate.Point;
import net.tachyon.entity.Player;
import net.tachyon.event.types.CancellableEvent;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.coordinate.BlockFace;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player interacts with a block (right-click).
 * This is also called when a block is placed.
 */
public class PlayerBlockInteractEvent extends PlayerEvent implements CancellableEvent {

    private final Point blockPosition;
    private final BlockFace blockFace;

    /**
     * Does this interaction block the normal item use?
     * True for containers which open an inventory instead of letting blocks be placed
     */
    private boolean blocksItemUse;

    private boolean cancelled;

    public PlayerBlockInteractEvent(@NotNull Player player,
                                    @NotNull Point blockPosition, @NotNull BlockFace blockFace) {
        super(player);
        this.blockPosition = blockPosition;
        this.blockFace = blockFace;
    }

    /**
     * Gets if the event should block the item use.
     *
     * @return true if the item use is blocked, false otherwise
     */
    public boolean isBlockingItemUse() {
        return blocksItemUse;
    }

    public void setBlockingItemUse(boolean blocks) {
        this.blocksItemUse = blocks;
    }

    /**
     * Gets the position of the interacted block.
     *
     * @return the block position
     */
    @NotNull
    public Point getBlockPosition() {
        return blockPosition;
    }

    /**
     * Gets the block face.
     *
     * @return the block face
     */
    @NotNull
    public BlockFace getBlockFace() {
        return blockFace;
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
