package net.tachyon.chat;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.tachyon.Tachyon;
import net.tachyon.coordinate.Position;
import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.play.ChatMessagePacket;
import net.tachyon.network.packet.server.play.PlayerListHeaderAndFooterPacket;
import net.tachyon.network.packet.server.play.SoundEffectPacket;
import net.tachyon.network.packet.server.play.TitlePacket;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

@FunctionalInterface
public interface ForwardingPlayerAudience extends ForwardingAudience {
    @Override
    default void sendMessage(final @NonNull Identified source, final @NonNull Component message, final @NonNull MessageType type) {
        sendMessage(source.identity(), message, type);
    }

    @Override
    default void sendMessage(final @NonNull Identity source, final @NonNull Component message, final @NonNull MessageType type) {
        ChatMessagePacket chatMessagePacket = new ChatMessagePacket(message, type == MessageType.CHAT ? ChatMessagePacket.Position.CHAT : ChatMessagePacket.Position.SYSTEM_MESSAGE);
        Tachyon.getServer().sendGroupedPacket(players(), chatMessagePacket);
    }

    @Override
    default void sendActionBar(@NonNull Component message) {
        ChatMessagePacket chatMessagePacket = new ChatMessagePacket(message, ChatMessagePacket.Position.GAME_INFO);
        Tachyon.getServer().sendGroupedPacket(players(), chatMessagePacket);
    }

    @Override
    default void sendPlayerListHeaderAndFooter(@NonNull Component header, @NonNull Component footer) {
        PlayerListHeaderAndFooterPacket playerListHeaderAndFooterPacket = new PlayerListHeaderAndFooterPacket(header, footer);
        Tachyon.getServer().sendGroupedPacket(players(), playerListHeaderAndFooterPacket);
    }

    @Override
    default void playSound(net.kyori.adventure.sound.@NonNull Sound sound, double x, double y, double z) {
        SoundEffectPacket soundEffectPacket = new SoundEffectPacket();
        soundEffectPacket.soundName = sound.name().value();
        soundEffectPacket.position = new Position(x, y, z);
        soundEffectPacket.volume = sound.volume();
        soundEffectPacket.pitch = sound.pitch();

        Tachyon.getServer().sendGroupedPacket(players(), soundEffectPacket);
    }

    @Override
    default void showTitle(@NonNull Title title) {
        TitlePacket titlePacket = new TitlePacket();
        titlePacket.action = TitlePacket.Action.SET_TITLE;
        titlePacket.titleText = title.title();
        Tachyon.getServer().sendGroupedPacket(players(), titlePacket);

        TitlePacket subtitlePacket = new TitlePacket();
        subtitlePacket.action = TitlePacket.Action.SET_SUBTITLE;
        subtitlePacket.titleText = title.subtitle();
        Tachyon.getServer().sendGroupedPacket(players(), subtitlePacket);

        Title.Times times = title.times();
        if (times != null) {
            TitlePacket timePacket = new TitlePacket();
            timePacket.action = TitlePacket.Action.SET_TIMES_AND_DISPLAY;
            timePacket.fadeIn = (int) (times.fadeIn().toMillis() / Tachyon.getServer().getTickMs());
            timePacket.stay =  (int) (times.stay().toMillis() / Tachyon.getServer().getTickMs());
            timePacket.fadeOut =  (int) (times.fadeOut().toMillis() / Tachyon.getServer().getTickMs());
            Tachyon.getServer().sendGroupedPacket(players(), timePacket);
        }
    }

    @Override
    default void clearTitle() {
        TitlePacket titlePacket = new TitlePacket();
        titlePacket.action = TitlePacket.Action.HIDE;
        Tachyon.getServer().sendGroupedPacket(players(), titlePacket);
    }

    @Override
    default void resetTitle() {
        TitlePacket titlePacket = new TitlePacket();
        titlePacket.action = TitlePacket.Action.RESET;
        Tachyon.getServer().sendGroupedPacket(players(), titlePacket);
    }

    @Override
    @NonNull default Iterable<? extends Audience> audiences() {
        return players();
    }

    @ApiStatus.OverrideOnly
    @NonNull Collection<Player> players();
}
