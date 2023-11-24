package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called at each player tick.
 */
public class PlayerTickEvent extends PlayerEvent {

    public PlayerTickEvent(@NotNull Player player) {
        super(player);
    }
}
