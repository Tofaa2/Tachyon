package net.tachyon.command.parser;

import net.tachyon.command.CommandSyntax;
import net.tachyon.command.arguments.Argument;

import java.util.Map;

/**
 * Holds the data of a validated syntax.
 */
public class ValidSyntaxHolder {
    public String commandString;
    public CommandSyntax syntax;
    public Map<Argument<?>, CommandParser.ArgumentResult> argumentResults;

}
