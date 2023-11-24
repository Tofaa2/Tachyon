package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.instance.Instance;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a new instance is set for a player.
 */
public class PlayerSpawnEvent extends PlayerEvent {

    private final Instance spawnInstance;
    private final boolean firstSpawn;

    public PlayerSpawnEvent(@NotNull TachyonPlayer player, @NotNull Instance spawnInstance, boolean firstSpawn) {
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
    public Instance getSpawnInstance() {
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
