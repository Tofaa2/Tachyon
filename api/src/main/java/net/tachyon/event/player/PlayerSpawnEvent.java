package net.tachyon.event.player;


import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;

import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a new instance is set for a player.
 */
public class PlayerSpawnEvent extends PlayerEvent {

    private final World spawnInstance;
    private final boolean firstSpawn;

    public PlayerSpawnEvent(@NotNull Player player, @NotNull World spawnInstance, boolean firstSpawn) {
        super(player);
        this.spawnInstance = spawnInstance;
        this.firstSpawn = firstSpawn;
    }

    /**
     * Gets the entity new instance.
     *
     * @return the instance
     */
    @NotNull
    public World getSpawnInstance() {
        return spawnInstance;
    }

    /**
     * 'true' if the player is spawning for the first time. 'false' if this spawn event was triggered by a dimension teleport
     *
     * @return true if this is the first spawn, false otherwise
     */
    public boolean isFirstSpawn() {
        return firstSpawn;
    }
}
