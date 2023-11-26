package net.tachyon.command.arguments;

import net.tachyon.command.CommandSender;
import net.tachyon.command.Command;
import net.tachyon.command.arguments.minecraft.SuggestionType;
import net.tachyon.command.exception.ArgumentSyntaxException;
import net.tachyon.command.suggestion.SuggestionCallback;
import net.tachyon.utils.StringUtils;
import net.tachyon.utils.validator.StringValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Same as {@link ArgumentWord} with the exception
 * that this argument can trigger {@link Command#onDynamicWrite(CommandSender, String)}
 * when the suggestion type is {@link SuggestionType#ASK_SERVER}, or any other suggestions available in the enum.
 *
 * @deprecated Use {@link Argument#setSuggestionCallback(SuggestionCallback)} with any argument type you want
 */
@Deprecated
public class ArgumentDynamicWord extends Argument<String> {

    public static final int SPACE_ERROR = 1;
    public static final int RESTRICTION_ERROR = 2;

    private final SuggestionType suggestionType;

    private StringValidator dynamicRestriction;

    public ArgumentDynamicWord(@NotNull String id, @NotNull SuggestionType suggestionType) {
        super(id);
        this.suggestionType = suggestionType;
    }

    @NotNull
    @Override
    public String parse(@NotNull String input) throws ArgumentSyntaxException {
        if (input.contains(StringUtils.SPACE))
            throw new ArgumentSyntaxException("Word cannot contain space characters", input, SPACE_ERROR);

        // true if 'value' is valid based on the dynamic restriction
        final boolean restrictionCheck = dynamicRestriction == null || dynamicRestriction.isValid(input);

        if (!restrictionCheck) {
            throw new ArgumentSyntaxException("Word does not respect the dynamic restriction", input, RESTRICTION_ERROR);
        }

        return input;
    }

    /**
     * Gets the suggestion type of this argument.
     * <p>
     * Suggestions are only suggestion, this means that the end value could not be the expected one.
     *
     * @return this argument suggestion type
     */
    @NotNull
    public SuggestionType getSuggestionType() {
        return suggestionType;
    }

    /**
     * Sets the dynamic restriction of this dynamic argument.
     * <p>
     * Will be called once the argument condition is checked.
     *
     * @param dynamicRestriction the dynamic restriction, can be null to disable
     * @return 'this' for chaining
     */
    @NotNull
    public ArgumentDynamicWord fromRestrictions(@Nullable StringValidator dynamicRestriction) {
        this.dynamicRestriction = dynamicRestriction;
        return this;
    }
}
