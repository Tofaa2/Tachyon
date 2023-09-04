package net.tachyon.command.builder.arguments;

import net.tachyon.command.builder.CommandContext;
import net.tachyon.command.builder.exception.ArgumentSyntaxException;
import net.tachyon.command.builder.parser.CommandParser;
import net.tachyon.command.builder.parser.ValidSyntaxHolder;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArgumentGroup extends Argument<CommandContext> {

    public static final int INVALID_ARGUMENTS_ERROR = 1;

    private final Argument<?>[] group;

    public ArgumentGroup(@NotNull String id, @NotNull Argument<?>... group) {
        super(id, true, false);
        this.group = group;
    }

    @NotNull
    @Override
    public CommandContext parse(@NotNull String input) throws ArgumentSyntaxException {
        List<ValidSyntaxHolder> validSyntaxes = new ArrayList<>();
        CommandParser.parse(null, group, input.split(StringUtils.SPACE), input, validSyntaxes, null);

        CommandContext context = new CommandContext(input);
        CommandParser.findMostCorrectSyntax(validSyntaxes, context);
        if (validSyntaxes.isEmpty()) {
            throw new ArgumentSyntaxException("Invalid arguments", input, INVALID_ARGUMENTS_ERROR);
        }

        return context;
    }

}
