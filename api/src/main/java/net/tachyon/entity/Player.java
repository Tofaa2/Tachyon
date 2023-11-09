package net.tachyon.entity;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.tachyon.command.CommandSender;
import net.tachyon.coordinate.Point;
import net.tachyon.entity.metadata.PlayerMeta;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.scoreboard.BelowNameTag;
import net.tachyon.stat.PlayerStatistic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

public interface Player extends LivingEntity, CommandSender {

    @NotNull String getUsername();

    @Nullable PlayerSkin getSkin();

    @NotNull PlayerConnection getPlayerConnection();

    @NotNull Map<PlayerStatistic, Integer> getStatisticValueMap();

    void sendPluginMessage(@NotNull String channel, @NotNull String message);

    void sendPluginMessage(@NotNull String channel, byte[] data);

    void playSound(@NotNull Sound sound, double x, double y, double z);

    default void playSound(@NotNull Sound sound, Point point) {
        playSound(sound, point.x(), point.y(), point.z());
    }

    void chat(@NotNull String message);

    int getFood();

    void setFood(int food);

    float getFoodSaturation();

    void setFoodSaturation(float saturation);

    void kick(@NotNull Component reason);

    boolean isOnline();

    void sendPacket(@NotNull ServerPacket packet);

    void setBelowNameTag(@Nullable BelowNameTag belowNameTag);
}
