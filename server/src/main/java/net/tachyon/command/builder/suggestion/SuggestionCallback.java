package net.tachyon.command.builder.suggestion;

import net.tachyon.command.CommandSender;
import net.tachyon.command.builder.CommandContext;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface SuggestionCallback {
    void apply(@NotNull CommandSender sender, @NotNull CommandContext context, @NotNull Suggestion suggestion);
}
