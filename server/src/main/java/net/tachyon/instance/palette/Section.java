package net.tachyon.instance.palette;

import net.tachyon.Tachyon;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.block.Block;
import net.tachyon.utils.clone.PublicCloneable;
import org.jetbrains.annotations.NotNull;

public class Section implements PublicCloneable<Section> {

    /**
     * The number of blocks that should be in one chunk section.
     */
    public final static int BLOCK_COUNT = TachyonChunk.CHUNK_SECTION_SIZE * TachyonChunk.CHUNK_SECTION_SIZE * TachyonChunk.CHUNK_SECTION_SIZE;

    private short[] blocks;

    protected Section() {
        clear();
    }

    public void setBlockAt(int x, int y, int z, short blockId) {
        if (blocks.length == 0) {
            if (blockId == 0) {
                // Section is empty and method is trying to place an air block, stop unnecessary computation
                return;
            }

            // Initialize the section
            blocks = new short[BLOCK_COUNT];
        }

        final int sectionIndex = getSectionIndex(x, y, z);

        blocks[sectionIndex] = blockId;
    }

    public short getBlockAt(int x, int y, int z) {
        if (blocks.length == 0) {
            // Section is not loaded, can only be air
            return Block.AIR.toStateId((byte)0);
        }

        final int sectionIndex = getSectionIndex(x, y, z);

        return blocks[sectionIndex];
    }

    /**
     * Loops through all the sections and blocks to find unused array (empty chunk section)
     * <p>
     * Useful after clearing one or multiple sections of a chunk. Can be unnecessarily expensive if the chunk
     * is composed of almost-empty sections since the loop will not stop until a non-air block is discovered.
     */
    public synchronized void clean() {
        if (blocks.length != 0) {
            boolean canClear = true;
            for (short blockGroup : blocks) {
                if (blockGroup != 0) {
                    canClear = false;
                    break;
                }
            }
            if (canClear) {
                this.blocks = new short[0];
            }
        }
    }

    public void clear() {
        this.blocks = new short[0];
    }

    public short[] getBlocks() {
        return blocks;
    }

    /**
     * Gets the index of the block on the section array based on the block position.
     *
     * @param x the chunk X
     * @param y the chunk Y
     * @param z the chunk Z
     * @return the section index of the position
     */
    public static int getSectionIndex(int x, int y, int z) {
        y %= TachyonChunk.CHUNK_SECTION_SIZE;
        return y << 8 | z << 4 | x;
    }

    @NotNull
    @Override
    public Section clone() {
        try {
            Section section = (Section) super.clone();
            section.blocks = blocks.clone();
            return section;
        } catch (CloneNotSupportedException e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
            throw new IllegalStateException("Weird thing happened");
        }
    }
}
