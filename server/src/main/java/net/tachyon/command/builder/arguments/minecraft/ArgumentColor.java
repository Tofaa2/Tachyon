package net.tachyon.command.builder.arguments.minecraft;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.tachyon.command.builder.arguments.Argument;
import net.tachyon.command.builder.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an argument which will give you a {@link TextColor}.
 * <p>
 * Example: red, white, reset
 */
public class ArgumentColor extends Argument<TextColor> {

    public static final int UNDEFINED_COLOR = -2;

    public ArgumentColor(String id) {
        super(id);
    }

    @NotNull
    @Override
    public TextColor parse(@NotNull String input) throws ArgumentSyntaxException {
        final TextColor color = NamedTextColor.NAMES.value(input);
        if (color == null)
            throw new ArgumentSyntaxException("Undefined color", input, UNDEFINED_COLOR);

        return color;
    }

}
