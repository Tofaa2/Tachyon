package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player start flying.
 */
public class PlayerStartFlyingEvent extends PlayerEvent {

    public PlayerStartFlyingEvent(@NotNull Player player) {
        super(player);
    }
}
