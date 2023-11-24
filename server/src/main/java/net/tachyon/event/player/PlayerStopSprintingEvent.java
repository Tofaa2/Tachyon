package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stops sprinting.
 */
public class PlayerStopSprintingEvent extends PlayerEvent {

    public PlayerStopSprintingEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
