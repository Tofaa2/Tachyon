package net.tachyon.command.builder.arguments.minecraft;

import com.google.gson.JsonParseException;
import net.kyori.adventure.text.Component;
import net.tachyon.chat.Adventure;
import net.tachyon.command.builder.arguments.Argument;
import net.tachyon.command.builder.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

public class ArgumentComponent extends Argument<Component> {

    public static final int INVALID_JSON_ERROR = 1;

    public ArgumentComponent(@NotNull String id) {
        super(id, true);
    }

    @NotNull
    @Override
    public Component parse(@NotNull String input) throws ArgumentSyntaxException {
        try {
            return Adventure.COMPONENT_SERIALIZER.deserialize(input);
        } catch (JsonParseException e) {
            throw new ArgumentSyntaxException("Invalid JSON", input, INVALID_JSON_ERROR);
        }
    }
}
