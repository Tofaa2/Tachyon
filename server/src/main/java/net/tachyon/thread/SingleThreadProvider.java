package net.tachyon.thread;

import net.tachyon.instance.TachyonChunk;
import net.tachyon.instance.Instance;
import net.tachyon.utils.chunk.ChunkUtils;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

/**
 * Simple thread provider implementation using a single thread to update all the instances and chunks.
 */
public class SingleThreadProvider extends ThreadProvider {

    {
        setThreadCount(1);
    }

    private final Set<Instance> instances = new CopyOnWriteArraySet<>();

    @Override
    public void onInstanceCreate(@NotNull Instance instance) {
        this.instances.add(instance);
    }

    @Override
    public void onInstanceDelete(@NotNull Instance instance) {
        this.instances.remove(instance);
    }

    @Override
    public void onChunkLoad(@NotNull Instance instance, int chunkX, int chunkZ) {

    }

    @Override
    public void onChunkUnload(@NotNull Instance instance, int chunkX, int chunkZ) {

    }

    @NotNull
    @Override
    public List<Future<?>> update(long time) {
        return Collections.singletonList(pool.submit(() -> {
            for (Instance instance : instances) {
                updateInstance(instance, time);
                for (Chunk chunk : instance.getChunks()) {
                    final long index = ChunkUtils.getChunkIndex(chunk.getChunkX(), chunk.getChunkZ());
                    processChunkTick(instance, index, time);
                }
            }
        }));
    }
}
