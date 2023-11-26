package net.tachyon.command.parser;

import net.tachyon.command.CommandContext;
import net.tachyon.command.CommandSyntax;
import net.tachyon.command.arguments.Argument;

public class ArgumentQueryResult {
    public CommandSyntax syntax;
    public Argument<?> argument;
    public CommandContext context;
    public String input;
}
