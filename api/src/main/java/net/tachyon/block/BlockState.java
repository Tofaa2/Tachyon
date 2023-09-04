package net.tachyon.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BlockState(Block block, byte metadata, @Nullable BlockVariation variation) {

    @NotNull
    public Block getBlock() {
        return block;
    }

    public byte getMetadata() {
        return metadata;
    }

    @Nullable
    public BlockVariation getVariation() {
        return variation;
    }

    @NotNull
    public static BlockState fromStateId(short blockStateId) {
        Block block = Block.fromStateId(blockStateId);
        byte metadata = Block.toMetadata(blockStateId);
        BlockVariation variation = block.getVariation(metadata);
        return new BlockState(block, metadata, variation);
    }
}
