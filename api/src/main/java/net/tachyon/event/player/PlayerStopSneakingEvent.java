package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stops sneaking.
 */
public class PlayerStopSneakingEvent extends PlayerEvent {

    public PlayerStopSneakingEvent(@NotNull Player player) {
        super(player);
    }
}
