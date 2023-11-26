package net.tachyon.command.arguments.relative;

import net.tachyon.command.exception.ArgumentSyntaxException;
import net.tachyon.coordinate.Vec;
import net.tachyon.utils.location.RelativeVec;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Vector} with 2 floating numbers (x;z) which can take relative coordinates.
 * <p>
 * Example: -1.2 ~
 */
public class ArgumentRelativeVec2 extends ArgumentRelative<RelativeVec> {

    public ArgumentRelativeVec2(@NotNull String id) {
        super(id, 2);
    }

    @NotNull
    @Override
    public RelativeVec parse(@NotNull String input) throws ArgumentSyntaxException {
        final String[] split = input.split(StringUtils.SPACE);

        // Check if the value has enough element to be correct
        if (split.length != getNumberCount()) {
            throw new ArgumentSyntaxException("Invalid number of values", input, INVALID_NUMBER_COUNT_ERROR);
        }

        Vec vector = Vec.ZERO;
        boolean relativeX = false;
        boolean relativeZ = false;

        for (int i = 0; i < split.length; i++) {
            final String element = split[i];
            try {
                if (element.startsWith(RELATIVE_CHAR)) {
                    if (i == 0) {
                        relativeX = true;
                    } else if (i == 1) {
                        relativeZ = true;
                    }

                    if (element.length() != RELATIVE_CHAR.length()) {
                        final String potentialNumber = element.substring(1);
                        final float number = Float.parseFloat(potentialNumber);
                        if (i == 0) {
                            vector = vector.withX(number);
                        } else if (i == 1) {
                            vector = vector.withZ(number);
                        }
                    }

                } else {
                    final float number = Float.parseFloat(element);
                    if (i == 0) {
                        vector = vector.withX(number);
                    } else if (i == 1) {
                        vector = vector.withZ(number);
                    }
                }
            } catch (NumberFormatException e) {
                throw new ArgumentSyntaxException("Invalid number", input, INVALID_NUMBER_ERROR);
            }
        }

        return new RelativeVec(vector, relativeX, false, relativeZ);
    }

}