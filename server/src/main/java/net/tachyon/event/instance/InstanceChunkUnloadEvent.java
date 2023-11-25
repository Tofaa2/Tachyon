package net.tachyon.event.instance;

import net.tachyon.event.InstanceEvent;
import net.tachyon.world.Instance;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a chunk in an instance is unloaded.
 */
public class InstanceChunkUnloadEvent extends InstanceEvent {

    private final int chunkX, chunkZ;

    public InstanceChunkUnloadEvent(@NotNull Instance instance, int chunkX, int chunkZ) {
        super(instance);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    /**
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    public int getChunkX() {
        return chunkX;
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    public int getChunkZ() {
        return chunkZ;
    }

}
