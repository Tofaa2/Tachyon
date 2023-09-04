package net.tachyon.command.builder.parser;

import net.tachyon.command.builder.CommandSyntax;
import net.tachyon.command.builder.arguments.Argument;

import java.util.Map;

/**
 * Holds the data of a validated syntax.
 */
public class ValidSyntaxHolder {
    public String commandString;
    public CommandSyntax syntax;
    public Map<Argument<?>, CommandParser.ArgumentResult> argumentResults;

}
