package net.tachyon.listener;

import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.packet.client.play.ClientStatusPacket;
import net.tachyon.network.packet.server.play.StatisticsPacket;
import net.tachyon.stat.PlayerStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatusListener {

    public static void listener(ClientStatusPacket packet, TachyonPlayer player) {
        switch (packet.action) {
            case PERFORM_RESPAWN -> player.respawn();
            case REQUEST_STATS -> {
                List<StatisticsPacket.Statistic> statisticList = new ArrayList<>();
                StatisticsPacket statisticsPacket = new StatisticsPacket();
                final Map<PlayerStatistic, Integer> playerStatisticValueMap = player.getStatisticValueMap();
                for (Map.Entry<PlayerStatistic, Integer> entry : playerStatisticValueMap.entrySet()) {
                    PlayerStatistic playerStatistic = entry.getKey();
                    int value = entry.getValue();

                    StatisticsPacket.Statistic statistic = new StatisticsPacket.Statistic();
                    statistic.name = playerStatistic.getStatisticName();
                    statistic.value = value;

                    statisticList.add(statistic);
                }
                statisticsPacket.statistics = statisticList.toArray(new StatisticsPacket.Statistic[0]);
                player.getPlayerConnection().sendPacket(statisticsPacket);
            }
        }
    }

}
