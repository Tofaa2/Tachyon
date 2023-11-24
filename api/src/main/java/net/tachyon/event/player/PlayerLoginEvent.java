package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;

import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called at player login, used to define his spawn instance.
 * <p>
 * Be aware that the player is not yet in a world when the event
 * is called, meaning that most player methods will not work.
 * You can use {@link PlayerSpawnEvent} and {@link PlayerSpawnEvent#isFirstSpawn()}
 * if needed.
 * <p>
 * WARNING: defining the spawning instance is MANDATORY.
 */
public class PlayerLoginEvent extends PlayerEvent {

    private World spawningInstance;

    public PlayerLoginEvent(@NotNull Player player) {
        super(player);
    }

    /**
     * Gets the spawning instance of the player.
     * <p>
     * WARNING: this must NOT be null, otherwise the player cannot spawn.
     *
     * @return the spawning instance, null if not already defined
     */
    @Nullable
    public World getSpawningInstance() {
        return spawningInstance;
    }

    /**
     * Changes the spawning instance.
     *
     * @param instance the new spawning instance
     */
    public void setSpawningWorld(@NotNull World instance) {
        this.spawningInstance = instance;
    }
}
