package net.tachyon.event.player;

import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a {@link TachyonPlayer} interacts (right-click) with an {@link TachyonEntity}.
 */
public class PlayerEntityInteractEvent extends PlayerEvent {

    private final TachyonEntity entityTarget;

    public PlayerEntityInteractEvent(@NotNull TachyonPlayer player, @NotNull TachyonEntity entityTarget) {
        super(player);
        this.entityTarget = entityTarget;
    }

    /**
     * Gets the {@link TachyonEntity} with who {@link #getPlayer()} is interacting.
     *
     * @return the {@link TachyonEntity}
     */
    @NotNull
    public TachyonEntity getTarget() {
        return entityTarget;
    }
}