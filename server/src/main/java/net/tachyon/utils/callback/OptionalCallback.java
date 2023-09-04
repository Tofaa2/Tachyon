package net.tachyon.utils.callback;

import net.tachyon.instance.TachyonChunk;
import net.tachyon.world.chunk.Chunk;
import net.tachyon.world.chunk.ChunkCallback;
import org.jetbrains.annotations.Nullable;

/**
 * Convenient class to execute callbacks which can be null.
 */
public class OptionalCallback {

    /**
     * Executes an optional {@link Runnable}.
     *
     * @param callback the optional runnable, can be null
     */
    public static void execute(@Nullable Runnable callback) {
        if (callback != null) {
            callback.run();
        }
    }

    /**
     * Executes an optional {@link ChunkCallback}.
     *
     * @param callback the optional chunk callback, can be null
     * @param chunk    the chunk to forward to the callback
     */
    public static void execute(@Nullable ChunkCallback callback, @Nullable Chunk chunk) {
        if (callback != null) {
            callback.accept(chunk);
        }
    }

}
