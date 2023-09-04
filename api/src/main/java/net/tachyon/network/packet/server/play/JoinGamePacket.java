package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.entity.GameMode;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.world.Difficulty;
import net.tachyon.world.DimensionType;
import net.tachyon.world.LevelType;
import org.jetbrains.annotations.NotNull;

public record JoinGamePacket(int entityId, GameMode gameMode, DimensionType dimensionType, Difficulty difficulty, int maxPlayers, LevelType levelType, boolean reducedDebugInfo) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(entityId);
        writer.writeByte((byte) (gameMode.getId() | (gameMode.isHardcore() ? 0x08 : 0x0)));
        writer.writeByte(dimensionType.getId());
        writer.writeByte(difficulty.getId());
        writer.writeByte((byte) maxPlayers);
        writer.writeSizedString(levelType.getId());
        writer.writeBoolean(reducedDebugInfo);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.JOIN_GAME;
    }

}
