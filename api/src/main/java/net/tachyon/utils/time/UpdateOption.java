package net.tachyon.utils.time;

import org.jetbrains.annotations.NotNull;

public record UpdateOption(long value, @NotNull TimeUnit timeUnit) {

    public long getValue() {
        return value;
    }

    @NotNull
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
