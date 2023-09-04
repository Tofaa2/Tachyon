package net.tachyon.chat;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Class used to convert JSON string to proper chat message representation.
 */
public final class ChatParser {

    public static final char COLOR_CHAR = (char) 0xA7; // Represent the character '§'

    /**
     * Converts a simple colored message json (text/color) to a {@link Component}.
     *
     * @param message the json containing the text and color
     * @return a {@link Component} representing the text
     */
    @NotNull
    public static Component toComponent(@NotNull String message) {
        return LegacyComponentSerializer.legacySection().deserialize(message);
    }
}
