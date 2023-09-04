package net.tachyon.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.tachyon.entity.Player;
import net.tachyon.permission.PermissionHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents something which can send commands to the server.
 * <p>
 * Main implementations are {@link Player} and {@link ConsoleSender}.
 */
public interface CommandSender extends PermissionHandler, Audience {
    /**
     * Sends a chat message with a {@link Identity#nil() nil} identity to this {@link Audience}.
     *
     * @param message a message
     * @see Component#text(String)
     * @see #sendMessage(Identified, Component)
     * @see #sendMessage(Identity, ComponentLike)
     */
    default void sendMessage(final @NonNull String message) {
        this.sendMessage(Identity.nil(), Component.text(message));
    }

    default void sendMessage(final @NonNull Component message) {
        this.sendMessage(Identity.nil(), message);
    }


    /**
     * Gets if the sender is a {@link Player}.
     *
     * @return true if 'this' is a player, false otherwise
     */
    default boolean isPlayer() {
        return this instanceof Player;
    }

    /**
     * Gets if the sender is a {@link ConsoleSender}.
     *
     * @return true if 'this' is the console, false otherwise
     */
    default boolean isConsole() {
        return this instanceof ConsoleSender;
    }

    /**
     * Casts this object to a {@link Player}.
     * No checks are performed, {@link ClassCastException} can very much happen.
     *
     * @throws ClassCastException if 'this' is not a player
     * @see #isPlayer()
     */
    default Player asPlayer() {
        return (Player) this;
    }

    /**
     * Casts this object to a {@link ConsoleSender}.
     * No checks are performed, {@link ClassCastException} can very much happen.
     *
     * @throws ClassCastException if 'this' is not a console sender
     * @see #isConsole()
     */
    default ConsoleSender asConsole() {
        return (ConsoleSender) this;
    }
}