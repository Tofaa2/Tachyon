package net.tachyon.command.builder.arguments;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Represents an argument which will take all the remaining of the command.
 * <p>
 * Example: Hey I am a string
 */
public class ArgumentStringArray extends Argument<String[]> {

    public ArgumentStringArray(String id) {
        super(id, true, true);
    }

    @NotNull
    @Override
    public String[] parse(@NotNull String input) {
        return input.split(Pattern.quote(StringUtils.SPACE));
    }

}
