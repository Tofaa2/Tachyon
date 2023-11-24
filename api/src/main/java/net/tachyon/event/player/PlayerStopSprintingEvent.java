package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stops sprinting.
 */
public class PlayerStopSprintingEvent extends PlayerEvent {

    public PlayerStopSprintingEvent(@NotNull Player player) {
        super(player);
    }
}
