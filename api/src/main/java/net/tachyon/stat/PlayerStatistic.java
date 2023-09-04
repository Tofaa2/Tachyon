package net.tachyon.stat;

/**
 * Represents a single statistic in the "statistics" game menu.
 * <p>
 * You can retrieve the statistics map with {@link Player#getStatisticValueMap()} and modify it with your own values.
 */
public class PlayerStatistic {

    private final String statisticName;

    public PlayerStatistic(String statisticName) {
        this.statisticName = statisticName;
    }

    public String getStatisticName() {
        return statisticName;
    }
}
