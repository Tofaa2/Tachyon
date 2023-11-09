package net.tachyon.stat;

import net.tachyon.entity.Player;

/**
 * Represents a single statistic in the "statistics" game menu.
 * <p>
 * You can retrieve the statistics map with {@link Player#getStatisticValueMap()} and modify it with your own values.
 */
public record PlayerStatistic(String statisticName) {

}
