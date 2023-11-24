package net.tachyon.event;

import net.tachyon.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerEvent extends Event {

    protected final Player player;

    public PlayerEvent(@NotNull Player player) {
        this.player = player;
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }
}
