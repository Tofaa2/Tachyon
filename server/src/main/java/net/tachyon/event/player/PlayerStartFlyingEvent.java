package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player start flying.
 */
public class PlayerStartFlyingEvent extends PlayerEvent {

    public PlayerStartFlyingEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
