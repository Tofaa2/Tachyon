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

    byte getHeldSlot();

    void sendPacket(@NotNull ServerPacket packet);

    void setBelowNameTag(@Nullable BelowNameTag belowNameTag);

    void refreshVisibleChunks();

    void setMetadataIndex(byte index, @NotNull Metadata.Value<?> data);

    @NotNull String getClientBrand();

    enum FacePoint {
        FEET,
        EYE
    }

    // Settings enum

    enum ChatMode {
        ENABLED,
        COMMANDS_ONLY,
        HIDDEN
    }

    class PlayerSettings {

        private final Player player;
        private String locale;
        private byte viewDistance;
        private ChatMode chatMode;
        private boolean chatColors;
        private byte displayedSkinParts;
        private boolean firstRefresh = true;

        PlayerSettings(Player player) {
            this.player = player;
        }

        /**
         * The player game language.
         *
         * @return the player locale
         */
        public String getLocale() {
            return locale;
        }

        /**
         * Gets the player view distance.
         *
         * @return the player view distance
         */
        public byte getViewDistance() {
            return viewDistance;
        }

        /**
         * Gets the player chat mode.
         *
         * @return the player chat mode
         */
        public ChatMode getChatMode() {
            return chatMode;
        }

        /**
         * Gets if the player has chat colors enabled.
         *
         * @return true if chat colors are enabled, false otherwise
         */
        public boolean hasChatColors() {
            return chatColors;
        }

        public byte getDisplayedSkinParts() {
            return displayedSkinParts;
        }

        /**
         * Changes the player settings internally.
         * <p>
         * WARNING: the player will not be noticed by this change, probably unsafe.
         *
         * @param locale             the player locale
         * @param viewDistance       the player view distance
         * @param chatMode           the player chat mode
         * @param chatColors         the player chat colors
         * @param displayedSkinParts the player displayed skin parts
         */
        public void refresh(String locale, byte viewDistance, ChatMode chatMode, boolean chatColors,
                            byte displayedSkinParts) {

            final boolean viewDistanceChanged = this.viewDistance != viewDistance;

            this.locale = locale;
            this.viewDistance = viewDistance;
            this.chatMode = chatMode;
            this.chatColors = chatColors;
            this.displayedSkinParts = displayedSkinParts;

            player.setMetadataIndex((byte) 0, Metadata.Byte(displayedSkinParts));

            this.firstRefresh = false;

            // Client changed his view distance in the settings
            if (viewDistanceChanged) {
                player.refreshVisibleChunks();
            }
        }
    }

}
