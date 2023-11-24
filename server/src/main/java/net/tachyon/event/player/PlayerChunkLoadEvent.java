package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player receive a new chunk data.
 */
public class PlayerChunkLoadEvent extends PlayerEvent {

    private final int chunkX, chunkZ;

    public PlayerChunkLoadEvent(@NotNull Player player, int chunkX, int chunkZ) {
        super(player);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    /**
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    public int getChunkX() {
        return chunkX;
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    public int getChunkZ() {
        return chunkZ;
    }
}
