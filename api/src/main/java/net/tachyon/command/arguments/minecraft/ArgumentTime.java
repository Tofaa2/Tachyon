package net.tachyon.command.arguments.minecraft;

import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import net.tachyon.command.arguments.Argument;
import net.tachyon.command.exception.ArgumentSyntaxException;
import net.tachyon.utils.time.TimeUnit;
import net.tachyon.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an argument giving a time (day/second/tick).
 * <p>
 * Example: 50d, 25s, 75t
 */
public class ArgumentTime extends Argument<UpdateOption> {

    public static final int INVALID_TIME_FORMAT = -2;
    public static final int NO_NUMBER = -3;

    private static final CharList SUFFIXES = new CharArrayList(new char[]{'d', 's', 't'});

    public ArgumentTime(String id) {
        super(id);
    }

    @NotNull
    @Override
    public UpdateOption parse(@NotNull String input) throws ArgumentSyntaxException {
        final char lastChar = input.charAt(input.length() - 1);

        TimeUnit timeUnit;
        if (Character.isDigit(lastChar))
            timeUnit = TimeUnit.TICK;
        else if (SUFFIXES.contains(lastChar)) {
            input = input.substring(0, input.length() - 1);

            if (lastChar == 'd') {
                timeUnit = TimeUnit.DAY;
            } else if (lastChar == 's') {
                timeUnit = TimeUnit.SECOND;
            } else if (lastChar == 't') {
                timeUnit = TimeUnit.TICK;
            } else {
                throw new ArgumentSyntaxException("Time needs to have the unit d, s, t, or none", input, NO_NUMBER);
            }
        } else
            throw new ArgumentSyntaxException("Time needs to have a unit", input, NO_NUMBER);

        try {
            // Check if value is a number
            final int time = Integer.parseInt(input);
            return new UpdateOption(time, timeUnit);
        } catch (NumberFormatException e) {
            throw new ArgumentSyntaxException("Time needs to be a number", input, NO_NUMBER);
        }

    }

}
