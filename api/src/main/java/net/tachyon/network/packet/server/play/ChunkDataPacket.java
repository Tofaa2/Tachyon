package net.tachyon.network.packet.server.play;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.world.palette.PaletteStorage;
import net.tachyon.world.palette.Section;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.utils.cache.CacheablePacket;
import net.tachyon.utils.cache.TemporaryCache;
import net.tachyon.utils.cache.TimedBuffer;
import net.tachyon.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.UUID;

public class ChunkDataPacket implements ServerPacket, CacheablePacket {
    private static final TemporaryCache<TimedBuffer> CACHE = new TemporaryCache<>(30000L);

    public boolean fullChunk;
    public boolean unloadChunk;
    public Biome[] biomes;
    public int chunkX, chunkZ;

    public PaletteStorage paletteStorage;
    public boolean skylight;

    public int[] sections;

    private static final byte CHUNK_SECTION_COUNT = 16;
    private static final int MAX_BITS_PER_ENTRY = 16;
    private static final int MAX_BUFFER_SIZE = (Short.BYTES + Byte.BYTES + 5 * Byte.BYTES + (4096 * MAX_BITS_PER_ENTRY / Long.SIZE * Long.BYTES)) * CHUNK_SECTION_COUNT + 256 * Integer.BYTES;

    // Cacheable data
    private final UUID identifier;
    private final long timestamp;

    public ChunkDataPacket(@Nullable UUID identifier, long timestamp) {
        this.identifier = identifier;
        this.timestamp = timestamp;
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(chunkX);
        writer.writeInt(chunkZ);
        writer.writeBoolean(fullChunk);

        short mask = 0;

        if (unloadChunk) {
            writer.writeShort(mask);
            writer.writeVarInt(0);
            return;
        }

        short[][] includedSections = new short[CHUNK_SECTION_COUNT][0];
        int includedCount = 0;

        for (byte i = 0; i < CHUNK_SECTION_COUNT; i++) {
            if (fullChunk || sections == null || (sections.length == CHUNK_SECTION_COUNT && sections[i] != 0)) {
                final Section section = paletteStorage.getSections()[i];
                if (section != null && section.getBlocks().length > 0) { // section contains at least one block
                    mask |= (short) (1 << i);
                    includedSections[includedCount++] = section.getBlocks();
                }
            }
        }

        writer.writeShort(mask);

        ByteBuf data = Unpooled.buffer(MAX_BUFFER_SIZE);

        for (int i = 0; i < includedCount; i++) {
            short[] section = includedSections[i];

            for (short datum : section) {
                data.writeShortLE(datum);
            }
        }

        byte[] lightBytes = new byte[2048];
        Arrays.fill(lightBytes, (byte) 0xFF);

        for (int i = 0; i < includedCount; i++) {
            data.writeBytes(lightBytes);
        }

        if (skylight) {
            for (int i = 0; i < includedCount; i++) {
                data.writeBytes(lightBytes);
            }
        }

        // Biome data
        if (fullChunk || sections == null) {
            for (int i = 0; i < 256; i++) {
                data.writeByte(0);
            }
        }

        // Data
        writer.writeVarInt(data.writerIndex());
        writer.getBuffer().writeBytes(data);
        data.release();
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CHUNK_DATA;
    }

    @NotNull
    @Override
    public TemporaryCache<TimedBuffer> getCache() {
        return CACHE;
    }

    @Override
    public UUID getIdentifier() {
        return identifier;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}