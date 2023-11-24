package net.tachyon.event.player;

import net.tachyon.entity.Entity;
import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a {@link Player} interacts (right-click) with an {@link net.tachyon.entity.Entity}.
 */
public class PlayerEntityInteractEvent extends PlayerEvent {

    private final Entity entityTarget;

    public PlayerEntityInteractEvent(@NotNull Player player, @NotNull Entity entityTarget) {
        super(player);
        this.entityTarget = entityTarget;
    }

    /**
     * Gets the {@link Entity} with who {@link #getPlayer()} is interacting.
     *
     * @return the {@link Entity}
     */
    @NotNull
    public Entity getTarget() {
        return entityTarget;
    }
}