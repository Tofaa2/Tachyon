package net.tachyon.network;

import net.tachyon.entity.Player;
import net.tachyon.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Used when you want to provide your own player object instead of using the default one.
 * <p>
 * Sets with {@link ConnectionManager#setPlayerProvider(PlayerProvider)}.
 */
@FunctionalInterface
public interface PlayerProvider {

    /**
     * Creates a new {@link Player} object based on his connection data.
     * <p>
     * Called once a client want to join the server and need to have an assigned player object.
     *
     * @param uuid       the player {@link UUID}
     * @param username   the player username
     * @param connection the player connection
     * @return a newly create {@link Player} object
     */
    @NotNull
    Player createPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection connection);
}
