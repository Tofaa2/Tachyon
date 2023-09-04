package net.tachyon.utils.player;

import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.player.NettyPlayerConnection;
import net.tachyon.network.player.PlayerConnection;

public final class PlayerUtils {

    private PlayerUtils() {

    }

    public static boolean isNettyClient(PlayerConnection playerConnection) {
        return playerConnection instanceof NettyPlayerConnection;
    }

    public static boolean isNettyClient(Player player) {
        return isNettyClient(((TachyonPlayer)player).getPlayerConnection());
    }

    public static boolean isNettyClient(TachyonEntity entity) {
        return (entity instanceof Player) && isNettyClient((Player) entity);
    }

}
