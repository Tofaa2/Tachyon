package net.tachyon.event.player;

import net.kyori.adventure.text.Component;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player die in {@link Player#kill()}.
 */
public class PlayerDeathEvent extends PlayerEvent {

    private String deathText;
    private Component chatMessage;

    public PlayerDeathEvent(@NotNull TachyonPlayer player, String deathText, Component chatMessage) {
        super(player);
        this.deathText = deathText;
        this.chatMessage = chatMessage;
    }

    /**
     * Gets the text displayed in the death screen.
     *
     * @return the death text, can be null
     */
    @Nullable
    public String getDeathText() {
        return deathText;
    }

    /**
     * Changes the text displayed in the death screen.
     *
     * @param deathText the death text to display, null to remove
     */
    public void setDeathText(@Nullable String deathText) {
        this.deathText = deathText;
    }

    /**
     * Gets the message sent to chat.
     *
     * @return the death chat message
     */
    @Nullable
    public Component getChatMessage() {
        return chatMessage;
    }

    /**
     * Changes the text sent in chat
     *
     * @param chatMessage the death message to send, null to remove
     */
    public void setChatMessage(@Nullable Component chatMessage) {
        this.chatMessage = chatMessage;
    }
}
