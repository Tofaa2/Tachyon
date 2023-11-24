package net.tachyon;

import net.tachyon.block.BlockManager;
import net.tachyon.data.DataManager;
import net.tachyon.data.DataType;
import net.tachyon.data.SerializableData;
import net.tachyon.entity.Entity;
import net.tachyon.entity.Metadata;
import net.tachyon.entity.Player;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.event.GlobalEventHandler;
import net.tachyon.exception.ExceptionManager;
import net.tachyon.network.IConnectionManager;
import net.tachyon.network.listener.IPacketListenerManager;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.ping.ResponseDataConsumer;
import net.tachyon.scheduler.SchedulerManager;
import net.tachyon.scoreboard.TeamManager;
import net.tachyon.utils.MathUtils;
import net.tachyon.utils.validate.Check;
import net.tachyon.utils.validator.PlayerValidator;
import net.tachyon.world.*;
import net.tachyon.world.biome.BiomeManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.UUID;
import java.util.function.BiFunction;

public abstract class Server {

    public final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static final String VERSION_NAME = "1.8.9";
    public static final int PROTOCOL_VERSION = 47;

    // Threads
    public static final String THREAD_NAME_BENCHMARK = "MC-Benchmark";

    public static final String THREAD_NAME_TICK = "MC-Tick";

    public static final String THREAD_NAME_BLOCK_BATCH = "MC-BlockBatchPool";

    public static final String THREAD_NAME_SCHEDULER = "MC-SchedulerPool";

    public static final String THREAD_NAME_PARALLEL_CHUNK_SAVING = "MC-ParallelChunkSaving";

    private final KeyPair secretKeypair;
    private final ExceptionManager exceptionManager;
    private final ServerSettings settings;
    private final GlobalEventHandler globalEventHandler;
    private ResponseDataConsumer responseDataConsumer;

    Server(@NotNull ServerSettings settings) {
        Check.stateCondition(!MathUtils.isBetween(settings.chunkViewDistance(), 2, 32), "Chunk view distance must be between 2 and 32");
        Check.stateCondition(!MathUtils.isBetween(settings.entityViewDistance(), 0, 32), "Entity view distance must be between 0 and 32");
        this.settings = settings;
        this.exceptionManager = new ExceptionManager();
        this.globalEventHandler = new GlobalEventHandler();
        KeyPair kp;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            kp = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            Tachyon.getServer().getExceptionManager().handleException(e);
            LOGGER.error("Key pair generation failed!");
            kp = null;
        }
        this.secretKeypair = kp;
    }

    /**
     * Gets the consumer executed to show server-list data.
     *
     * @return the response data consumer
     */
    @Nullable
    public ResponseDataConsumer getResponseDataConsumer() {
        return responseDataConsumer;
    }

    /**
     * Sets the consumer executed to show server-list data.
     * @param responseDataConsumer the response data consumer
     */
    public void setResponseDataConsumer(@Nullable ResponseDataConsumer responseDataConsumer) {
        this.responseDataConsumer = responseDataConsumer;
    }

    /**
     * Gets the global event handler.
     * <p>
     * Used to register event callback at a global scale.
     *
     * @return the global event handler
     */
    @NotNull
    public GlobalEventHandler getGlobalEventHandler() {
        return globalEventHandler;
    }

    public abstract @NotNull TeamManager getTeamManager();

    public abstract @NotNull IConnectionManager getConnectionManager();

    public abstract <E extends EntityMeta> BiFunction<Entity, Metadata, EntityMeta> createMeta(@NotNull Class<E> clazz);

    /**
     * Sends a {@link ServerPacket} to multiple players.
     * <p>
     * Can drastically improve performance since the packet will not have to be processed as much.
     *
     * @param players         the players to send the packet to
     * @param packet          the packet to send to the players
     * @param validator optional callback to check if a specify player of {@code players} should receive the packet
     */
    public abstract void sendGroupedPacket(@NotNull Collection<Player> players, @NotNull ServerPacket packet, @Nullable PlayerValidator validator);


    /**
     * Same as {@link #sendGroupedPacket(Collection, ServerPacket, PlayerValidator)}
     * but with the player validator sets to null.
     *
     * @see #sendGroupedPacket(Collection, ServerPacket, PlayerValidator)
     */
    public abstract void sendGroupedPacket(@NotNull Collection<Player> players, @NotNull ServerPacket packet);




    public abstract void stopCleanly();

    public abstract boolean isStopping();

    public abstract SchedulerManager getSchedulerManager();
    public abstract BlockManager getBlockmanager();

    public abstract BiomeManager getBiomeManager();
    public abstract IPacketListenerManager getPacketListenerManager();

    /**
     * Gets the manager handling {@link DataType} used by {@link SerializableData}.
     *
     * @return the data manager
     */
    public abstract DataManager getDataManager();

    public @Nullable KeyPair getSecretKeypair() {
        return secretKeypair;
    }

    /**
     * Creates and returns a new world to the server. Worlds by default are not saved or have anything applied to them besides simple
     * time ticking and age/block/entity ticking and tracking.
     * @param uuid the {@link UUID} of the world, this is used to identify the world. These arent stored anywhere and are only used to identify the world during runtime.
     * @param levelType the {@link LevelType} of the world
     * @param dimesionType the {@link DimensionType} of the world.
     * @param shared if the world is shared or not. Shared worlds are worlds that have the same block data but can both accept different players. If this is set to true, the created {@link World} instance can be safely casted to {@link SharedWorld} as it is a {@link SharedWorld} instance.
     * @return return the newly created {@link World} instance.
     */
    public abstract @NotNull World createWorld(@NotNull UUID uuid, @NotNull LevelType levelType, DimensionType dimesionType, boolean shared);

    /**
     * Calls the {@link #createWorld(UUID, LevelType, DimensionType, boolean)} but with a randomized {@link UUID}
     */
    public @NotNull World createWorld(@NotNull LevelType levelType, DimensionType dimesionType, boolean shared) {
        return createWorld(UUID.randomUUID(), levelType, dimesionType, shared);
    }

    public @NotNull String getBrandName() {
        return settings.brand();
    }

    public int getTargetTPS() {
        return settings.targetTps();
    }

    public int getTickMs() {
        return 1000 / getTargetTPS();
    }

    public int getMaxPlayers() {
        return settings.maxPlayers();
    }

    public Difficulty getDifficulty() {
        return settings.difficulty();
    }

    public int getChunkViewDistance() {
        return settings.chunkViewDistance();
    }

    public int getEntityViewDistance() {
        return settings.entityViewDistance();
    }

    public int getSchedulerThreadCount() {
        return settings.thread().schedulerThreadCount();
    }

    public int getBlockBatchThreadCount() {
        return settings.thread().blockBatchThreadCount();
    }

    public int getChunkSavingThreadCount() {
        return settings.thread().chunkSavingThreadCount();
    }

    public int getCompressionThreshold() {
        return settings.packet().compressionThreshold();
    }

    public int getPacketRateLimit() {
        return settings.packet().rateLimitLimit();
    }

    public int getPacketSizeLimit() {
        return settings.packet().sizeLimit();
    }

    public boolean isPacketCachingEnabled() {
        return settings.packet().caching();
    }

    public boolean isPacketGroupingEnabled() {
        return settings.packet().grouping();
    }

    public int getNettyThreadCount() {
        return settings.thread().nettyThreadCount() == -1 ? Math.max(1, Runtime.getRuntime().availableProcessors() - 1) : settings.thread().nettyThreadCount();
    }

    public ExceptionManager getExceptionManager() {
        return exceptionManager;
    }
}
