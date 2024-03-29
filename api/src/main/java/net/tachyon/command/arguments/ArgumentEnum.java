package net.tachyon.command.arguments;

import net.tachyon.command.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class ArgumentEnum<E extends Enum> extends Argument<E> {

    public final static int NOT_ENUM_VALUE_ERROR = 1;

    private final Class<E> enumClass;
    private final E[] values;
    private Format format = Format.DEFAULT;

    public ArgumentEnum(@NotNull String id, Class<E> enumClass) {
        super(id);
        this.enumClass = enumClass;
        this.values = enumClass.getEnumConstants();
    }

    public ArgumentEnum<E> setFormat(@NotNull Format format) {
        this.format = format;
        return this;
    }

    @NotNull
    @Override
    public E parse(@NotNull String input) throws ArgumentSyntaxException {
        for (E value : this.values) {
            if (this.format.formatter.apply(value.name()).equals(input)) {
                return value;
            }
        }
        throw new ArgumentSyntaxException("Not a " + this.enumClass.getSimpleName() + " value", input, NOT_ENUM_VALUE_ERROR);
    }

    public enum Format {
        DEFAULT(name -> name),
        LOWER_CASED(name -> name.toLowerCase(Locale.ROOT)),
        UPPER_CASED(name -> name.toUpperCase(Locale.ROOT));

        private final Function<String, String> formatter;

        Format(Function<String, String> formatter) {
            this.formatter = formatter;
        }
    }

}
