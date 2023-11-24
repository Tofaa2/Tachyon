package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stop flying.
 */
public class PlayerStopFlyingEvent extends PlayerEvent {

    public PlayerStopFlyingEvent(@NotNull Player player) {
        super(player);
    }
}
