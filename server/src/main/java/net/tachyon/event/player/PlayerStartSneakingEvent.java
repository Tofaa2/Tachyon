package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player starts sneaking.
 */
public class PlayerStartSneakingEvent extends PlayerEvent {

    public PlayerStartSneakingEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
