package net.tachyon.command.builder.arguments;

import net.tachyon.command.CommandSender;
import net.tachyon.command.builder.Command;
import net.tachyon.command.builder.exception.ArgumentSyntaxException;
import net.tachyon.command.builder.suggestion.SuggestionCallback;
import net.tachyon.utils.validator.StringArrayValidator;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Same as {@link ArgumentStringArray} with the exception
 * that this argument can trigger {@link Command#onDynamicWrite(CommandSender, String)}.
 *
 * @deprecated Use {@link Argument#setSuggestionCallback(SuggestionCallback)} with any argument type you want
 */
@Deprecated
public class ArgumentDynamicStringArray extends Argument<String[]> {

    public static final int RESTRICTION_ERROR = 1;

    private StringArrayValidator dynamicRestriction;

    public ArgumentDynamicStringArray(String id) {
        super(id, true, true);
    }

    @NotNull
    @Override
    public String[] parse(@NotNull String input) throws ArgumentSyntaxException {
        final String[] value = input.split(StringUtils.SPACE);

        // true if 'value' is valid based on the dynamic restriction
        final boolean restrictionCheck = dynamicRestriction == null || dynamicRestriction.isValid(value);

        if (!restrictionCheck) {
            throw new ArgumentSyntaxException("Argument does not respect the dynamic restriction", input, RESTRICTION_ERROR);
        }

        return value;
    }

    /**
     * Sets the dynamic restriction of this dynamic argument.
     * <p>
     * Will be called once the argument condition is checked.
     *
     * @param dynamicRestriction the dynamic restriction, can be null to disable
     * @return 'this' for chaining
     */
    public ArgumentDynamicStringArray fromRestrictions(@Nullable StringArrayValidator dynamicRestriction) {
        this.dynamicRestriction = dynamicRestriction;
        return this;
    }

}
