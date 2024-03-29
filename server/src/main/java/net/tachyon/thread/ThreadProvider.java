package net.tachyon.thread;

import io.netty.util.NettyRuntime;
import net.tachyon.MinecraftServer;
import net.tachyon.entity.*;
import net.tachyon.world.chunk.TachyonChunk;
import net.tachyon.world.Instance;
import net.tachyon.world.InstanceContainer;
import net.tachyon.world.SharedInstance;
import net.tachyon.utils.validator.EntityValidator;
import net.tachyon.utils.ChunkUtils;
import net.tachyon.utils.thread.ServerThread;
import net.tachyon.UpdateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * Used to link chunks into multiple groups.
 * Then executed into a thread pool.
 * <p>
 * You can change the current thread provider by calling {@link UpdateManager#setThreadProvider(ThreadProvider)}.
 */
public abstract class ThreadProvider {

    /**
     * The thread pool of this thread provider.
     */
    protected ExecutorService pool;
    /**
     * The amount of threads in the thread pool
     */
    private int threadCount;

    {
        // Default thread count in the pool (cores * 2)
        setThreadCount(NettyRuntime.availableProcessors() * 2);
    }

    /**
     * Called when an {@link Instance} is registered.
     *
     * @param instance the newly create {@link Instance}
     */
    public abstract void onInstanceCreate(@NotNull Instance instance);

    /**
     * Called when an {@link Instance} is unregistered.
     *
     * @param instance the deleted {@link Instance}
     */
    public abstract void onInstanceDelete(@NotNull Instance instance);

    /**
     * Called when a chunk is loaded.
     * <p>
     * Be aware that this is possible for an instance to load chunks before being registered.
     *
     * @param instance the instance of the chunk
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     */
    public abstract void onChunkLoad(@NotNull Instance instance, int chunkX, int chunkZ);

    /**
     * Called when a chunk is unloaded.
     *
     * @param instance the instance of the chunk
     * @param chunkX   the chunk X
     * @param chunkZ   the chunk Z
     */
    public abstract void onChunkUnload(@NotNull Instance instance, int chunkX, int chunkZ);

    /**
     * Performs a server tick for all chunks based on their linked thread.
     *
     * @param time the update time in milliseconds
     * @return the futures to execute to complete the tick
     */
    @NotNull
    public abstract List<Future<?>> update(long time);

    /**
     * Gets the current size of the thread pool.
     *
     * @return the thread pool's size
     */
    public int getThreadCount() {
        return threadCount;
    }

    /**
     * Changes the amount of threads in the thread pool.
     *
     * @param threadCount the new amount of threads
     */
    public synchronized void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        refreshPool();
    }

    private void refreshPool() {
        if (pool != null) {
            this.pool.shutdown();
        }
        this.pool = new ServerThread(threadCount, MinecraftServer.THREAD_NAME_TICK);
    }

    // INSTANCE UPDATE

    /**
     * Processes a whole tick for a chunk.
     *
     * @param instance   the instance of the chunk
     * @param chunkIndex the index of the chunk {@link ChunkUtils#getChunkIndex(int, int)}
     * @param time       the time of the update in milliseconds
     */
    protected void processChunkTick(@NotNull Instance instance, long chunkIndex, long time) {
        final int chunkX = ChunkUtils.getChunkCoordX(chunkIndex);
        final int chunkZ = ChunkUtils.getChunkCoordZ(chunkIndex);

        final TachyonChunk chunk = (TachyonChunk) instance.getChunk(chunkX, chunkZ);
        if (!ChunkUtils.isLoaded(chunk))
            return;

        updateChunk(instance, chunk, time);

        updateEntities(instance, chunk, time);
    }

    /**
     * Executes an instance tick.
     *
     * @param instance the instance
     * @param time     the current time in ms
     */
    protected void updateInstance(@NotNull Instance instance, long time) {
        // The instance
        instance.tick(time);
    }

    /**
     * Executes a chunk tick (blocks update).
     *
     * @param instance the chunk's instance
     * @param chunk    the chunk
     * @param time     the current time in ms
     */
    protected void updateChunk(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time) {
        chunk.tick(time, instance);
    }

    // ENTITY UPDATE

    /**
     * Executes an entity tick (all entities type creatures/objects/players) in an instance's chunk.
     *
     * @param instance the chunk's instance
     * @param chunk    the chunk
     * @param time     the current time in ms
     */
    protected void updateEntities(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time) {
        conditionalEntityUpdate(instance, chunk, time, null);
    }

    /**
     * Executes an entity tick for object entities in an instance's chunk.
     *
     * @param instance the chunk's instance
     * @param chunk    the chunk
     * @param time     the current time in ms
     */
    protected void updateObjectEntities(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time) {
        conditionalEntityUpdate(instance, chunk, time, entity -> entity instanceof ObjectEntity);
    }

    /**
     * Executes an entity tick for living entities in an instance's chunk.
     *
     * @param instance the chunk's instance
     * @param chunk    the chunk
     * @param time     the current time in ms
     */
    protected void updateLivingEntities(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time) {
        conditionalEntityUpdate(instance, chunk, time, entity -> entity instanceof TachyonLivingEntity);
    }

    /**
     * Executes an entity tick for creatures entities in an instance's chunk.
     *
     * @param instance the chunk's instance
     * @param chunk    the chunk
     * @param time     the current time in ms
     */
    protected void updateCreatures(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time) {
        conditionalEntityUpdate(instance, chunk, time, entity -> entity instanceof TachyonEntityCreature);
    }

    /**
     * Executes an entity tick for players in an instance's chunk.
     *
     * @param instance the chunk's instance
     * @param chunk    the chunk
     * @param time     the current time in ms
     */
    protected void updatePlayers(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time) {
        conditionalEntityUpdate(instance, chunk, time, entity -> entity instanceof TachyonPlayer);
    }

    /**
     * Executes an entity tick in an instance's chunk if condition is verified.
     *
     * @param instance  the chunk's instance
     * @param chunk     the chunk
     * @param time      the current time in ms
     * @param condition the condition which confirm if the update happens or not
     */
    protected void conditionalEntityUpdate(@NotNull Instance instance, @NotNull TachyonChunk chunk, long time,
                                           @Nullable EntityValidator condition) {
        final Set<Entity> entities = instance.getChunkEntities(chunk);
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                if (condition != null && !condition.isValid(entity))
                    continue;
                ((TachyonEntity) entity).tick(time);
            }
        }

        updateSharedInstances(instance, sharedInstance -> conditionalEntityUpdate(sharedInstance, chunk, time, condition));
    }

    /**
     * If {@code instance} is an {@link InstanceContainer}, run a callback for all of its
     * {@link SharedInstance}.
     *
     * @param instance the instance
     * @param callback the callback to run for all the {@link SharedInstance}
     */
    private void updateSharedInstances(@NotNull Instance instance, @NotNull Consumer<SharedInstance> callback) {
        if (instance instanceof InstanceContainer) {
            final InstanceContainer instanceContainer = (InstanceContainer) instance;

            if (!instanceContainer.hasSharedInstances())
                return;

            for (SharedInstance sharedInstance : instanceContainer.getSharedInstances()) {
                callback.accept(sharedInstance);
            }
        }
    }

}
