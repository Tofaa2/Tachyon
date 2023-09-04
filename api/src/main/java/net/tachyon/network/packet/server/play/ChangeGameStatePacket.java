package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record ChangeGameStatePacket(@NotNull Reason reason, float value) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte((byte) reason.ordinal());
        writer.writeFloat(value);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CHANGE_GAME_STATE;
    }

    public enum Reason {
        NO_RESPAWN_BLOCK,
        END_RAINING,
        BEGIN_RAINING,
        CHANGE_GAMEMODE,
        WIN_GAME,
        DEMO_EVENT,
        ARROW_HIT_PLAYER,
        FADE_VALUE,
        FADE_TIME,
        PLAY_MOB_APPEARANCE
    }

}
