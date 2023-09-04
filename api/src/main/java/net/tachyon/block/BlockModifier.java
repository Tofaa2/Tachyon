package net.tachyon.block;

import net.tachyon.Tachyon;
import net.tachyon.coordinate.Point;
import net.tachyon.data.Data;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element which can place blocks at position.
 * <p>
 * Notably used by {@link net.tachyon.world.World}, {@link Batch}.
 */
public interface BlockModifier {

    BlockManager BLOCK_MANAGER = Tachyon.getServer().getBlockmanager();

    /**
     * Sets a block at a position.
     * <p>
     * You can use {@link #setBlock(int, int, int, Block)} if you want it to be more explicit.
     *
     * @param x            the block X
     * @param y            the block Y
     * @param z            the block Z
     * @param blockStateId the block state id
     * @param data         the block {@link Data}, can be null
     */
    void setBlockStateId(int x, int y, int z, short blockStateId, @Nullable Data data);

    /**
     * Sets a {@link CustomBlock} at a position.
     * <p>
     * The custom block id should be the one returned by {@link CustomBlock#getCustomBlockId()}.
     *
     * @param x             the block X
     * @param y             the block Y
     * @param z             the block Z
     * @param customBlockId the custom block id
     * @param data          the block {@link Data}, can be null
     */
    void setCustomBlock(int x, int y, int z, short customBlockId, @Nullable Data data);

    /**
     * Sets a {@link CustomBlock} at a position with a custom state id.
     * <p>
     * The custom block id should be the one returned by {@link CustomBlock#getCustomBlockId()},
     * and the block state id can be anything you want, state id can be retrieved using {@link Block#toStateId(byte)}.
     *
     * @param x             the block X
     * @param y             the block Y
     * @param z             the block Z
     * @param blockStateId  the block state id
     * @param customBlockId the custom block id
     * @param data          the block {@link Data}, can be null
     */
    void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId, @Nullable Data data);

    default void setBlockStateId(int x, int y, int z, short blockStateId) {
        setBlockStateId(x, y, z, blockStateId, null);
    }

    default void setBlock(int x, int y, int z, @NotNull Block block) {
        setBlockStateId(x, y, z, block.toStateId((byte) 0), null);
    }

    default void setBlock(int x, int y, int z, @NotNull Block block, byte metadata) {
        setBlockStateId(x, y, z, block.toStateId(metadata), null);
    }

    default void setBlock(int x, int y, int z, @NotNull Block block, BlockVariation variation) {
        setBlockStateId(x, y, z, block.toStateId(variation.getMetadata()), null);
    }

    default void setBlock(@NotNull Point blockPosition, @NotNull Block block) {
        setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block);
    }

    default void setBlock(@NotNull Point blockPosition, @NotNull Block block, byte metadata) {
        setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block, metadata);
    }

    default void setBlock(@NotNull Point blockPosition, @NotNull Block block, BlockVariation variation) {
        setBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), block, variation);
    }

    default void setBlockStateId(@NotNull Point blockPosition, short blockStateId) {
        setBlockStateId(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), blockStateId);
    }

    default void setCustomBlock(int x, int y, int z, short customBlockId) {
        setCustomBlock(x, y, z, customBlockId, null);
    }

    default void setCustomBlock(int x, int y, int z, @NotNull String customBlockId, @Nullable Data data) {
        CustomBlock customBlock = BLOCK_MANAGER.getCustomBlock(customBlockId);
        Check.notNull(customBlock, "The CustomBlock " + customBlockId + " is not registered");

        setCustomBlock(x, y, z, customBlock.getCustomBlockId(), data);
    }

    default void setCustomBlock(int x, int y, int z, @NotNull String customBlockId) {
        setCustomBlock(x, y, z, customBlockId, null);
    }

    default void setCustomBlock(@NotNull Point blockPosition, @NotNull String customBlockId) {
        setCustomBlock(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), customBlockId);
    }

    default void setSeparateBlocks(int x, int y, int z, short blockStateId, short customBlockId) {
        setSeparateBlocks(x, y, z, blockStateId, customBlockId, null);
    }

    default void setSeparateBlocks(@NotNull Point blockPosition, short blockStateId, short customBlockId) {
        setSeparateBlocks(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), blockStateId, customBlockId, null);
    }

}
