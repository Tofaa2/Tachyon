package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player stop flying.
 */
public class PlayerStopFlyingEvent extends PlayerEvent {

    public PlayerStopFlyingEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
