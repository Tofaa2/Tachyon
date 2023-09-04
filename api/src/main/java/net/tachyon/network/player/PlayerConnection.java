package net.tachyon.network.player;

import net.tachyon.entity.Player;
import net.tachyon.network.ConnectionState;
import net.tachyon.network.packet.server.ServerPacket;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A PlayerConnection is an object needed for all created {@link net.tachyon.entity.Player}.
 * It can be extended to create a new kind of player (NPC for instance).
 */
public interface  PlayerConnection {

    void update();

    void disconnect();

    void sendPacket(@NotNull ServerPacket packet);

    @NotNull AtomicInteger getPacketCounter();

    @NotNull String getIdentifier();

    @NotNull SocketAddress getRemoteAddress();

    @Nullable Player getPlayer();

    int getLastPacketCounter();

    boolean isOnline();

    @NotNull ConnectionState getConnectionState();

    @ApiStatus.Internal
    void setConnectionState(@NotNull ConnectionState connectionState);

}
