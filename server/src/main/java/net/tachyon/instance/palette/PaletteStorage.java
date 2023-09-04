package net.tachyon.instance.palette;

import net.tachyon.Tachyon;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.block.Block;
import net.tachyon.network.packet.server.play.ChunkDataPacket;
import net.tachyon.utils.MathUtils;
import net.tachyon.utils.chunk.ChunkUtils;
import net.tachyon.utils.clone.CloneUtils;
import net.tachyon.utils.clone.PublicCloneable;
import org.jetbrains.annotations.NotNull;

/**
 * Used to efficiently store blocks with an optional palette.
 * <p>
 * The format used is the one described in the {@link ChunkDataPacket},
 * the reason is that it allows us to write the packet much faster.
 */
public class PaletteStorage implements PublicCloneable<PaletteStorage> {

    private Section[] sections = new Section[TachyonChunk.CHUNK_SECTION_COUNT];

    /**
     * Creates a new palette storage.
     */
    public PaletteStorage() {
    }

    public void setBlockAt(int x, int y, int z, short blockId) {
        if (!MathUtils.isBetween(y, 0, TachyonChunk.CHUNK_SIZE_Y - 1)) {
            return;
        }
        final int sectionIndex = ChunkUtils.getSectionAt(y);
        x = toChunkCoordinate(x);
        z = toChunkCoordinate(z);

        Section section = sections[sectionIndex];
        if (section == null) {
            section = new Section();
            sections[sectionIndex] = section;
        }
        section.setBlockAt(x, y, z, blockId);
    }

    public short getBlockAt(int x, int y, int z) {
        if (y < 0 || y >= TachyonChunk.CHUNK_SIZE_Y) {
            return 0;
        }

        final int sectionIndex = ChunkUtils.getSectionAt(y);
        final Section section = sections[sectionIndex];
        if (section == null) {
            return Block.AIR.getBlockId();
        }
        x = toChunkCoordinate(x);
        z = toChunkCoordinate(z);

        return section.getBlockAt(x, y, z);
    }

    public Section[] getSections() {
        return sections;
    }

    /**
     * Loops through all the sections and blocks to find unused array (empty chunk section)
     * <p>
     * Useful after clearing one or multiple sections of a chunk. Can be unnecessarily expensive if the chunk
     * is composed of almost-empty sections since the loop will not stop until a non-air block is discovered.
     */
    public synchronized void clean() {
        for (Section section : sections) {
            section.clean();
        }
    }

    /**
     * Clears all the data in the palette and data array.
     */
    public void clear() {
        for (Section section : sections) {
            section.clear();
        }
    }

    @NotNull
    @Override
    public PaletteStorage clone() {
        try {
            PaletteStorage paletteStorage = (PaletteStorage) super.clone();
            paletteStorage.sections = CloneUtils.cloneArray(sections, Section[]::new);
            return paletteStorage;
        } catch (CloneNotSupportedException e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
            throw new IllegalStateException("Weird thing happened");
        }
    }
    /**
     * Converts a world coordinate to a chunk one.
     *
     * @param xz the world coordinate
     * @return the chunk coordinate of {@code xz}
     */
    private static int toChunkCoordinate(int xz) {
        xz %= 16;
        if (xz < 0) {
            xz += TachyonChunk.CHUNK_SECTION_SIZE;
        }

        return xz;
    }
}
