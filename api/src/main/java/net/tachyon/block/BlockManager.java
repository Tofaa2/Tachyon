package net.tachyon.block;

import net.tachyon.block.rule.BlockPlacementRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface BlockManager {

    /**
     * Registers a {@link CustomBlock}.
     *
     * @param customBlock the custom block to register
     * @throws IllegalArgumentException if <code>customBlock</code> block id is not greater than 0
     * @throws IllegalStateException    if the id of <code>customBlock</code> is already registered
     */
    void registerCustomBlock(@NotNull CustomBlock customBlock);

    /**
     * Registers a {@link BlockPlacementRule}.
     *
     * @param rule the block placement rule to register
     * @throws IllegalArgumentException if <code>blockPlacementRule</code> block id is negative
     */
    void registerBlockPlacementRule(@NotNull BlockPlacementRule rule);

    /**
     * Gets the {@link BlockPlacementRule} of the specific block.
     *
     * @param block the block to check
     * @return the block placement rule associated with the block, null if not any
     */
    @Nullable BlockPlacementRule getBlockPlacementRule(@NotNull Block block);

    /**
     * Gets the {@link BlockPlacementRule} of the specific block.
     *
     * @param blockStateId the block id to check
     * @return the block placement rule associated with the id, null if not any
     */
    @Nullable BlockPlacementRule getBlockPlacementRule(short blockStateId);


    /**
     * Gets the {@link CustomBlock} with the specific identifier {@link CustomBlock#getIdentifier()}.
     *
     * @param identifier the custom block identifier
     * @return the {@link CustomBlock} associated with the identifier, null if not any
     */
    @Nullable CustomBlock getCustomBlock(String identifier);


    /**
     * Gets the {@link CustomBlock} with the specific custom block id {@link CustomBlock#getCustomBlockId()}.
     *
     * @param id the custom block id
     * @return the {@link CustomBlock} associated with the id, null if not any
     */
    @Nullable CustomBlock getCustomBlock(short id);

    /**
     * Gets all the registered custom blocks.
     *
     * @return a {@link Collection} containing the registered custom blocks
     */
    @NotNull Collection<CustomBlock> getCustomBlocks();


}
