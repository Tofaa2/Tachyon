package net.tachyon;

import io.netty.buffer.ByteBuf;
import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.Data;
import net.tachyon.data.SerializableData;
import net.tachyon.entity.Player;
import net.tachyon.network.packet.server.play.EntityEquipmentPacket;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Unsafe {

    /**
     * Pretends the player did close the inventory they had open.
     * @param player
     * @param didCloseInventory
     */
    void changeDidCloseInventory(Player player, boolean didCloseInventory);

    void setBlock(Chunk chunk, int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data, boolean updatable);

    void addViewableChunk(Player player, Chunk chunk);

    void removeViewableChunk(Player player, Chunk chunk);

    @NotNull SerializableData serializableDataImpl();

    @NotNull BinaryReader newBinaryReader(byte[] bytes);

    @NotNull BinaryWriter newBinaryWriter();

    @NotNull BinaryWriter newBinaryWriter(ByteBuf... buffers);

    void refreshLastBlockChangeTime(World world);
}
