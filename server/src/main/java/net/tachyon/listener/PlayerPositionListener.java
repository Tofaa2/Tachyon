package net.tachyon.listener;

import net.tachyon.coordinate.Position;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerMoveEvent;
import net.tachyon.world.Instance;
import net.tachyon.network.packet.client.play.ClientPlayerLookPacket;
import net.tachyon.network.packet.client.play.ClientPlayerPacket;
import net.tachyon.network.packet.client.play.ClientPlayerPositionAndLookPacket;
import net.tachyon.network.packet.client.play.ClientPlayerPositionPacket;
import net.tachyon.utils.ChunkUtils;
import org.jetbrains.annotations.NotNull;

public class PlayerPositionListener {

    public static void playerPacketListener(ClientPlayerPacket packet, TachyonPlayer player) {
        player.refreshOnGround(packet.onGround);
    }

    public static void playerLookListener(ClientPlayerLookPacket packet, TachyonPlayer player) {
        final Position playerPosition = player.getPosition();
        final double x = playerPosition.getX();
        final double y = playerPosition.getY();
        final double z = playerPosition.getZ();
        final float yaw = packet.yaw;
        final float pitch = packet.pitch;
        final boolean onGround = packet.onGround;
        processMovement(player, x, y, z, yaw, pitch, onGround);
    }

    public static void playerPositionListener(ClientPlayerPositionPacket packet, TachyonPlayer player) {
        final Position playerPosition = player.getPosition();
        final float yaw = playerPosition.getYaw();
        final float pitch = playerPosition.getPitch();
        final boolean onGround = packet.onGround;
        processMovement(player,
                packet.x, packet.y, packet.z,
                yaw, pitch, onGround);
    }

    public static void playerPositionAndLookListener(ClientPlayerPositionAndLookPacket packet, TachyonPlayer player) {
        final float yaw = packet.yaw;
        final float pitch = packet.pitch;
        final boolean onGround = packet.onGround;
        processMovement(player,
                packet.x, packet.y, packet.z,
                yaw, pitch, onGround);
    }

    private static void processMovement(@NotNull TachyonPlayer player, double x, double y, double z,
                                        float yaw, float pitch, boolean onGround) {
        final Instance instance = player.getInstance();

        // Prevent moving before the player spawned, probably a modified client (or high latency?)
        if (instance == null) {
            return;
        }

        // Try to move in an unloaded chunk, prevent it
        if (!ChunkUtils.isLoaded(instance, x, z)) {
            player.teleport(player.getPosition());
            return;
        }

        final Position currentPosition = player.getPosition();
        Position newPosition = new Position(x, y, z, yaw, pitch);
        final Position cachedPosition = newPosition;

        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(player, newPosition);
        player.callEvent(PlayerMoveEvent.class, playerMoveEvent);

        // True if the event call changed the player position (possibly a teleport)
        final boolean positionChanged = !currentPosition.equals(player.getPosition());

        if (!playerMoveEvent.isCancelled() && !positionChanged) {
            // Move the player
            newPosition = playerMoveEvent.getNewPosition();
            if (!newPosition.equals(cachedPosition)) {
                // New position from the event changed, teleport the player
                player.teleport(newPosition);
            }
            // Change the internal data
            player.refreshPosition(newPosition.getX(), newPosition.getY(), newPosition.getZ());
            player.refreshView(newPosition.getYaw(), newPosition.getPitch());
            player.refreshOnGround(onGround);
        } else {
            player.teleport(player.getPosition());
        }
    }

}
