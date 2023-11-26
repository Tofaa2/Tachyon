package net.tachyon.command.parser;

import net.tachyon.command.CommandSyntax;
import net.tachyon.command.exception.ArgumentSyntaxException;

/**
 * Holds the data of an invalidated syntax.
 */
public class CommandSuggestionHolder {
    public CommandSyntax syntax;
    public ArgumentSyntaxException argumentSyntaxException;
    public int argIndex;
}
