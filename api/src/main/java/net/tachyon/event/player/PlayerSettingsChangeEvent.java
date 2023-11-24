package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called after the player signals the server that his settings has been modified.
 */
public class PlayerSettingsChangeEvent extends PlayerEvent {

    public PlayerSettingsChangeEvent(@NotNull Player player) {
        super(player);
    }

    /**
     * Gets the player who changed his settings.
     * <p>
     * You can retrieve the new player settings with {@link Player#getSettings()}.
     *
     * @return the player
     */
    @NotNull
    @Override
    public Player getPlayer() {
        return player;
    }

}
