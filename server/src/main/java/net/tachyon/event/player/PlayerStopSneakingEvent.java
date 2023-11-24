package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stops sneaking.
 */
public class PlayerStopSneakingEvent extends PlayerEvent {

    public PlayerStopSneakingEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
