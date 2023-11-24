package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.event.types.CancellableEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called every time a player send a message starting by '/'.
 */
public class PlayerCommandEvent extends PlayerEvent implements CancellableEvent {

    private String command;

    private boolean cancelled;

    public PlayerCommandEvent(@NotNull Player player, @NotNull String command) {
        super(player);
        this.command = command;
    }

    /**
     * Gets the command used (command name + arguments).
     *
     * @return the command that the player wants to execute
     */
    @NotNull
    public String getCommand() {
        return command;
    }

    /**
     * Changes the command to execute.
     *
     * @param command the new command
     */
    public void setCommand(@NotNull String command) {
        this.command = command;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
