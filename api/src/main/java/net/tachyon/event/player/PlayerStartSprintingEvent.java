package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player starts sprinting.
 */
public class PlayerStartSprintingEvent extends PlayerEvent {

    public PlayerStartSprintingEvent(@NotNull Player player) {
        super(player);
    }
}
