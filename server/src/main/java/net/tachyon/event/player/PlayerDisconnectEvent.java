package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player disconnect.
 */
public class PlayerDisconnectEvent extends PlayerEvent {

    public PlayerDisconnectEvent(@NotNull TachyonPlayer player) {
        super(player);
    }
}
