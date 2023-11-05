package net.tachyon.network;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.tachyon.chat.ForwardingPlayerAudience;
import net.tachyon.entity.Player;
import net.tachyon.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface IConnectionManager extends Audience, ForwardingPlayerAudience {


    @Nullable Player getPlayer(@NotNull final UUID uuid);

    /**
     * Gets the {@link Player} linked to a {@link PlayerConnection}.
     *
     * @param connection the player connection
     * @return the player linked to the connection
     */
    @Nullable Player getPlayer(@NotNull final PlayerConnection connection);

    /**
     * Finds the closest player matching a given username.
     * <p>
     *
     * @param name the player username (can be partial)
     * @return the closest match, null if no players are online
     */
    @Nullable Player findPlayer(@NotNull final String name);

    @Nullable Player getPlayer(@NotNull final String name);

    @NotNull UUID getPlayerConnectionUuid(@NotNull final PlayerConnection connection, @NotNull final String username);

    @NotNull Component getShutdownText();

    void setShutdownText(@NotNull final Component text);

    /**
     * Gets all online players.
     *
     * @return an unmodifiable collection containing all the online players
     */
    @NotNull Collection<Player> getOnlinePlayers();

}
