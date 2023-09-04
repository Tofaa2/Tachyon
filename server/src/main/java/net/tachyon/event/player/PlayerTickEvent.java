package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called at each player tick.
 */
public class PlayerTickEvent extends PlayerEvent {

    public PlayerTickEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
