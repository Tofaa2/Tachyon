package net.tachyon.world.chunk;

import net.tachyon.Server;
import net.tachyon.Tachyon;
import net.tachyon.utils.OptionalCallback;
import net.tachyon.utils.thread.ServerThread;
import net.tachyon.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Interface implemented to change the way chunks are loaded/saved.
 * <p>
 * See {@link BasicChunkLoader} for the default implementation used in {@link InstanceContainer}.
 */
public interface IChunkLoader {

    /**
     * Loads a {@link TachyonChunk}, all blocks should be set since the {@link ChunkGenerator} is not applied.
     *
     * @param instance the {@link Instance} where the {@link TachyonChunk} belong
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     * @param callback the callback executed when the {@link TachyonChunk} is done loading,
     *                 never called if the method returns false. Can be null.
     * @return true if the chunk loaded successfully, false otherwise
     */
    boolean loadChunk(@NotNull World instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback);

    /**
     * Saves a {@link TachyonChunk} with an optional callback for when it is done.
     *
     * @param chunk    the {@link TachyonChunk} to save
     * @param callback the callback executed when the {@link TachyonChunk} is done saving,
     *                 should be called even if the saving failed (you can throw an exception).
     *                 Can be null.
     */
    void saveChunk(@NotNull TachyonChunk chunk, @Nullable Runnable callback);

    /**
     * Saves multiple chunks with an optional callback for when it is done.
     * <p>
     * Implementations need to check {@link #supportsParallelSaving()} to support the feature if possible.
     *
     * @param chunks   the chunks to save
     * @param callback the callback executed when the {@link TachyonChunk} is done saving,
     *                 should be called even if the saving failed (you can throw an exception).
     *                 Can be null.
     */
    default void saveChunks(@NotNull Collection<TachyonChunk> chunks, @Nullable Runnable callback) {
        if (supportsParallelSaving()) {
            ExecutorService parallelSavingThreadPool = new ServerThread(Tachyon.getServer().getChunkSavingThreadCount(), Server.THREAD_NAME_PARALLEL_CHUNK_SAVING, true);
            chunks.forEach(c -> parallelSavingThreadPool.execute(() -> saveChunk(c, null)));
            try {
                parallelSavingThreadPool.shutdown();
                parallelSavingThreadPool.awaitTermination(1L, java.util.concurrent.TimeUnit.DAYS);
                OptionalCallback.execute(callback);
            } catch (InterruptedException e) {
                Tachyon.getServer().getExceptionManager().handleException(e);
            }
        } else {
            AtomicInteger counter = new AtomicInteger();
            for (TachyonChunk chunk : chunks) {
                saveChunk(chunk, () -> {
                    final boolean isLast = counter.incrementAndGet() == chunks.size();
                    if (isLast) {
                        OptionalCallback.execute(callback);
                    }
                });
            }
        }
    }

    /**
     * Does this {@link IChunkLoader} allow for multi-threaded saving of {@link TachyonChunk}?
     *
     * @return true if the chunk loader supports parallel saving
     */
    default boolean supportsParallelSaving() {
        return false;
    }

    /**
     * Does this {@link IChunkLoader} allow for multi-threaded loading of {@link TachyonChunk}?
     *
     * @return true if the chunk loader supports parallel loading
     */
    default boolean supportsParallelLoading() {
        return false;
    }
}
