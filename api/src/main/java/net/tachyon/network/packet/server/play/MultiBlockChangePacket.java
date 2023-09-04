package net.tachyon.network.packet.server.play;

import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;

public class MultiBlockChangePacket implements ServerPacket {

    public int chunkX;
    public int chunkZ;
    public BlockChange[] blockChanges;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(chunkX);
        writer.writeInt(chunkZ);
        if (blockChanges != null) {
            final int length = blockChanges.length;
            writer.writeVarInt(length);
            for (final BlockChange blockChange : blockChanges) {
                writer.writeByte((byte) ((blockChange.positionX % Chunk.CHUNK_SIZE_X) << 4 | (blockChange.positionZ % Chunk.CHUNK_SIZE_Z)));
                writer.writeByte((byte) blockChange.positionY);
                writer.writeVarInt(blockChange.blockStateId);
            }
        } else {
            writer.writeVarInt(0);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.MULTI_BLOCK_CHANGE;
    }

    public static class BlockChange {
        public int positionX;
        public int positionY;
        public int positionZ;
        public short blockStateId;
    }
}
