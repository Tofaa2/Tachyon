package net.tachyon.utils.time;

import net.tachyon.Tachyon;

public enum TimeUnit {

    TICK, DAY, HOUR, MINUTE, SECOND, MILLISECOND;

    /**
     * Converts a value and its unit to milliseconds.
     *
     * @param value the time value
     * @return the converted milliseconds based on the time value and the unit
     */
    public long toMilliseconds(long value) {
        return switch (this) {
            case TICK -> Tachyon.getServer().getTickMs() * value;
            case DAY -> value * 86_400_000;
            case HOUR -> value * 3_600_000;
            case MINUTE -> value * 60_000;
            case SECOND -> value * 1000;
            case MILLISECOND -> value;
            default -> -1; // Unexpected
        };
    }

}
