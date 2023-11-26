package net.tachyon.command;

import net.tachyon.command.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

/**
 * Callback executed when an error is found within the {@link Argument}.
 */
@FunctionalInterface
public interface ArgumentCallback {

    /**
     * Executed when an error is found.
     *
     * @param sender    the sender which executed the command
     * @param exception the exception containing the message, input and error code related to the issue
     */
    void apply(@NotNull CommandSender sender, @NotNull ArgumentSyntaxException exception);
}
