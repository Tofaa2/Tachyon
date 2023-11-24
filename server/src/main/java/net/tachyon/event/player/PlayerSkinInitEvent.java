package net.tachyon.event.player;

import net.tachyon.entity.PlayerSkin;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.types.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called at the player connection to initialize his skin.
 */
public class PlayerSkinInitEvent extends PlayerEvent {

    private PlayerSkin skin;

    public PlayerSkinInitEvent(@NotNull TachyonPlayer player, @Nullable PlayerSkin currentSkin) {
        super(player);
        this.skin = currentSkin;
    }

    /**
     * Gets the spawning skin of the player.
     *
     * @return the player skin, or null if not any
     */
    @Nullable
    public PlayerSkin getSkin() {
        return skin;
    }

    /**
     * Sets the spawning skin of the player.
     *
     * @param skin the new player skin
     */
    public void setSkin(@Nullable PlayerSkin skin) {
        this.skin = skin;
    }
}
