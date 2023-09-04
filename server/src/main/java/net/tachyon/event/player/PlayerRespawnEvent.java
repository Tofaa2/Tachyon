package net.tachyon.event.player;

import net.tachyon.coordinate.Position;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.PlayerEvent;
import net.tachyon.network.packet.client.play.ClientStatusPacket;
import org.jetbrains.annotations.NotNull;

/**
 * Called when {@link TachyonPlayer#respawn()} is executed (for custom respawn or as a result of
 * {@link ClientStatusPacket}
 */
public class PlayerRespawnEvent extends PlayerEvent {

    private Position respawnPosition;

    public PlayerRespawnEvent(@NotNull TachyonPlayer player) {
        super(player);
        this.respawnPosition = player.getRespawnPoint();
    }

    /**
     * Gets the respawn position.
     * <p>
     * Is by default {@link TachyonPlayer#getRespawnPoint()}
     *
     * @return the respawn position
     */
    @NotNull
    public Position getRespawnPosition() {
        return respawnPosition;
    }

    /**
     * Changes the respawn position.
     *
     * @param respawnPosition the new respawn position
     */
    public void setRespawnPosition(@NotNull Position respawnPosition) {
        this.respawnPosition = respawnPosition;
    }
}
