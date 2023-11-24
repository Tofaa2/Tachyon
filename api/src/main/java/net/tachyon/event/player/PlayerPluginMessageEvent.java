package net.tachyon.event.player;

import net.tachyon.entity.Player;
import net.tachyon.event.types.PlayerEvent;
import net.tachyon.network.packet.client.play.ClientPluginMessagePacket;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player send {@link ClientPluginMessagePacket}.
 */
public class PlayerPluginMessageEvent extends PlayerEvent {

    private final String identifier;
    private final byte[] message;

    public PlayerPluginMessageEvent(@NotNull Player player, @NotNull String identifier, @NotNull byte[] message) {
        super(player);
        this.identifier = identifier;
        this.message = message;
    }

    /**
     * Gets the message identifier.
     *
     * @return the identifier
     */
    @NotNull
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets the message data as a byte array.
     *
     * @return the message
     */
    @NotNull
    public byte[] getMessage() {
        return message;
    }

    /**
     * Gets the message data as a String.
     *
     * @return the message
     */
    @NotNull
    public String getMessageString() {
        return new String(message);
    }
}
