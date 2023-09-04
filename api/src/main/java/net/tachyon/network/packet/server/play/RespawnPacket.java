package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.entity.GameMode;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.world.Difficulty;
import net.tachyon.world.DimensionType;
import net.tachyon.world.LevelType;
import org.jetbrains.annotations.NotNull;

public class RespawnPacket implements ServerPacket {

    public DimensionType dimensionType;
    public Difficulty difficulty;
    public GameMode gameMode;
    public LevelType levelType;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(dimensionType.getId());
        writer.writeByte(difficulty.getId());
        writer.writeByte(gameMode.getId());
        writer.writeSizedString(levelType.getId());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.RESPAWN;
    }
}
