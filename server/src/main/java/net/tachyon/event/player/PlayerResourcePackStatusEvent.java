package net.tachyon.event.player;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.resourcepack.ResourcePackStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player warns the server of a resource pack status.
 */
public class PlayerResourcePackStatusEvent extends PlayerEvent {

    private final ResourcePackStatus status;

    public PlayerResourcePackStatusEvent(@NotNull TachyonPlayer player, @NotNull ResourcePackStatus status) {
        super(player);
        this.status = status;
    }

    /**
     * Gets the resource pack status.
     *
     * @return the resource pack status
     */
    @NotNull
    public ResourcePackStatus getStatus() {
        return status;
    }
}
