package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Called before the player initialization, it can be used to kick the player before any connection
 * or to change his final username/uuid.
 */
public class AsyncPlayerPreLoginEvent extends PlayerEvent {

    private String username;
    private UUID playerUuid;

    public AsyncPlayerPreLoginEvent(@NotNull TachyonPlayer player, @NotNull String username, @NotNull UUID playerUuid) {
        super(player);
        this.username = username;
        this.playerUuid = playerUuid;
    }

    /**
     * Gets the player username.
     *
     * @return the player username
     */
    @NotNull
    public String getUsername() {
        return username;
    }

    /**
     * Changes the player username.
     *
     * @param username the new player username
     */
    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    /**
     * Gets the player uuid.
     *
     * @return the player uuid
     */
    @NotNull
    public UUID getPlayerUuid() {
        return playerUuid;
    }

    /**
     * Changes the player uuid.
     *
     * @param playerUuid the new player uuid
     */
    public void setPlayerUuid(@NotNull UUID playerUuid) {
        this.playerUuid = playerUuid;
    }
}
