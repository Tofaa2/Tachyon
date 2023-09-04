package net.tachyon.command.builder.arguments;

import io.netty.util.internal.StringUtil;
import net.tachyon.command.builder.exception.ArgumentSyntaxException;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Argument which will take a quoted string.
 * <p>
 * Example: "Hey I am a string"
 */
public class ArgumentString extends Argument<String> {

    private static final char BACKSLASH = '\\';
    private static final char DOUBLE_QUOTE = '"';
    private static final char QUOTE = '\'';

    public static final int QUOTE_ERROR = 1;

    public ArgumentString(String id) {
        super(id, true);
    }

    @NotNull
    @Override
    public String parse(@NotNull String input) throws ArgumentSyntaxException {
        return staticParse(input);
    }

    @NotNull
    public static String staticParse(@NotNull String input) throws ArgumentSyntaxException {

        // Return if not quoted
        if (!input.contains(String.valueOf(DOUBLE_QUOTE)) &&
                !input.contains(String.valueOf(QUOTE)) &&
                !input.contains(String.valueOf(StringUtil.SPACE))) {
            return input;
        }

        // Check if value start and end with quote
        final char first = input.charAt(0);
        final char last = input.charAt(input.length() - 1);
        final boolean quote = input.length() >= 2 &&
                first == last && (first == DOUBLE_QUOTE || first == QUOTE);
        if (!quote)
            throw new ArgumentSyntaxException("String argument needs to start and end with quotes", input, QUOTE_ERROR);

        // Remove first and last characters (quotes)
        input = input.substring(1, input.length() - 1);

        // Verify backslashes
        for (int i = 1; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c == first) {
                final char lastChar = input.charAt(i - 1);
                if (lastChar != BACKSLASH) {
                    throw new ArgumentSyntaxException("Non-escaped quote", input, QUOTE_ERROR);
                }
            }
        }

        return StringEscapeUtils.unescapeJava(input);
    }
}
