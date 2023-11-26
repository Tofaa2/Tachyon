package net.tachyon.command.arguments.minecraft.registry;

import net.tachyon.command.arguments.Argument;
import net.tachyon.command.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

public abstract class ArgumentRegistry<T> extends Argument<T> {

    public static final int INVALID_NAME = -2;

    public ArgumentRegistry(String id) {
        super(id);
    }

    public abstract T getRegistry(@NotNull String value);

    @NotNull
    @Override
    public T parse(@NotNull String input) throws ArgumentSyntaxException {
        final T registryValue = getRegistry(input);
        if (registryValue == null)
            throw new ArgumentSyntaxException("Registry value is invalid", input, INVALID_NAME);

        return registryValue;
    }
}
