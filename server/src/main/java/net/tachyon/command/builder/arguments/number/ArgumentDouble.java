package net.tachyon.command.builder.arguments.number;

import net.tachyon.command.builder.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

public class ArgumentDouble extends ArgumentNumber<Double> {

    public ArgumentDouble(String id) {
        super(id);
    }

    @NotNull
    @Override
    public Double parse(@NotNull String input) throws ArgumentSyntaxException {
        try {
            final double value;
            {
                String parsed = parseValue(input);
                int radix = getRadix(input);
                if (radix != 10) {
                    value = (double) Long.parseLong(parsed, radix);
                } else {
                    value = Double.parseDouble(parsed);
                }
            }

            // Check range
            if (hasMin && value < min) {
                throw new ArgumentSyntaxException("Input is lower than the minimum required value", input, RANGE_ERROR);
            }
            if (hasMax && value > max) {
                throw new ArgumentSyntaxException("Input is higher than the minimum required value", input, RANGE_ERROR);
            }

            return value;
        } catch (NumberFormatException | NullPointerException e) {
            throw new ArgumentSyntaxException("Input is not a number/long", input, NOT_NUMBER_ERROR);
        }
    }

}
