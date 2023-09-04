package net.tachyon.command.builder.parser;

import net.tachyon.command.builder.CommandContext;
import net.tachyon.command.builder.CommandSyntax;
import net.tachyon.command.builder.arguments.Argument;

public class ArgumentQueryResult {
    public CommandSyntax syntax;
    public Argument<?> argument;
    public CommandContext context;
    public String input;
}
