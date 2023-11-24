package net.tachyon.world;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a world that is shared between multiple worlds essentially,
 * Blocks cannot be modified in this world, but can be read from.
 */
public interface SharedWorld extends World {

    @NotNull World getSharedInstance();

}
