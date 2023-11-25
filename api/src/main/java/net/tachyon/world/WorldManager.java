package net.tachyon.world;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public interface WorldManager {


    /**
     * Creates a world with the specified UUID, dimension and level type.
     * @param uuid
     * @param dimension
     * @param levelType
     * @return
     */
    @NotNull World createWorld(@NotNull UUID uuid, @NotNull DimensionType dimension, @NotNull LevelType levelType);

    /**
     * Creates a world with the specified dimension type and level type
     * @param dimension
     * @param levelType
     * @return
     */
    @NotNull World createWorld(@NotNull DimensionType dimension, @NotNull LevelType levelType);

    /**
     * Creates a world with a specified UUID, dimension and the level type {@link LevelType#FLAT}.
     * @param uuid
     * @param dimension
     * @return
     */
    @NotNull World createWorld(@NotNull UUID uuid, @NotNull DimensionType dimension);

    /**
     * Creates a world with the specified dimension and the level type {@link LevelType#FLAT}.
     * @param dimension
     * @return
     */
    @NotNull World createWorld(@NotNull DimensionType dimension);

    /**
     * Creates a world with the dimension {@link DimensionType#OVERWORLD} and the level type {@link LevelType#FLAT}.
     * @return
     */
    @NotNull World createWorld();

    /**
     * Wraps a world into a {@link SharedWorld} object.
     * @param world
     * @return
     */
    @NotNull SharedWorld createSharedWorld(@NotNull World world);

    /**
     * @return an unmodifiable set of all worlds, including shared worlds
     */
    @NotNull Set<World> getWorlds();

    /**
     * @return an unmodifiable set of all shared worlds.
     */
    @NotNull Set<SharedWorld> getSharedWorlds();

}
