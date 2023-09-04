package net.tachyon.command.builder.parser;

import net.tachyon.command.builder.CommandSyntax;
import net.tachyon.command.builder.exception.ArgumentSyntaxException;

/**
 * Holds the data of an invalidated syntax.
 */
public class CommandSuggestionHolder {
    public CommandSyntax syntax;
    public ArgumentSyntaxException argumentSyntaxException;
    public int argIndex;
}
