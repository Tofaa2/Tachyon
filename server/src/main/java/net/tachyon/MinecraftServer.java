package net.tachyon;

import net.tachyon.block.BlockManager;
import net.tachyon.data.DataManager;
import net.tachyon.data.TachyonDataManager;
import net.tachyon.entity.*;
import net.tachyon.entity.metadata.EntityMeta;
import net.tachyon.network.IConnectionManager;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.plugin.TachyonPluginManager;
import net.tachyon.scoreboard.TeamManager;
import net.tachyon.utils.PacketUtils;
import net.tachyon.utils.benchmark.BenchmarkManager;
import net.tachyon.command.TachyonCommandManager;
import net.tachyon.fluids.Fluid;
import net.tachyon.world.InstanceContainer;
import net.tachyon.world.InstanceManager;
import net.tachyon.block.Block;
import net.tachyon.world.SharedInstance;
import net.tachyon.world.block.TachyonBlockManager;
import net.tachyon.block.CustomBlock;
import net.tachyon.block.rule.BlockPlacementRule;
import net.tachyon.item.Enchantment;
import net.tachyon.item.Material;
import net.tachyon.listener.manager.PacketListenerManager;
import net.tachyon.namespace.NamespaceID;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.PacketProcessor;
import net.tachyon.network.netty.NettyServer;
import net.tachyon.particle.Particle;
import net.tachyon.potion.PotionEffect;
import net.tachyon.potion.PotionType;
import net.tachyon.registry.Registries;
import net.tachyon.scheduler.SchedulerManager;
import net.tachyon.scheduler.SchedulerManagerImpl;
import net.tachyon.scoreboard.TachyonTeamManager;
import net.tachyon.sound.SoundEvent;
import net.tachyon.stat.StatisticType;
import net.tachyon.utils.thread.ServerThread;
import net.tachyon.utils.validate.Check;
import net.tachyon.utils.validator.PlayerValidator;
import net.tachyon.world.DimensionType;
import net.tachyon.world.LevelType;
import net.tachyon.world.World;
import net.tachyon.biome.TachyonBiomeManager;
import net.tachyon.world.WorldManager;
import net.tachyon.world.biome.BiomeManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * The main server class used to start the server and retrieve all the managers.
 * <p>
 * The server needs to be started with {@link #start(String, int)}.
 * You should register all of your dimensions, biomes, commands, events, etc... in-between.
 */
public final class MinecraftServer extends Server {

    private static MinecraftServer instance;
    // Network
    private PacketListenerManager packetListenerManager;
    private PacketProcessor packetProcessor;
    private NettyServer nettyServer;
    // In-Game Manager
    private ConnectionManager connectionManager;
    private InstanceManager instanceManager;
    private TachyonBlockManager blockManager;
    private TachyonCommandManager commandManager;
    private TachyonDataManager dataManager;
    private TachyonTeamManager teamManager;
    private SchedulerManager schedulerManager;
    private BenchmarkManager benchmarkManager;
    private TachyonBiomeManager biomeManager;
    private UpdateManager updateManager;
    private TachyonPluginManager pluginManager;

    // Data
    private boolean initialized;
    private boolean started;
    private boolean stopping;

    public MinecraftServer(@NotNull ServerSettings settings, @NotNull File dataDir) {
        super(settings, dataDir);
        Check.stateCondition(instance != null, "The server is already initialized");
        instance = this;
    }

    public void init() {
        Check.stateCondition(initialized, "The server is already initialized");
        Block[] blocks = Block.values();
        for (Block block : blocks) {
            Registries.blocks.put(NamespaceID.from(block.getName()), block);
        }

        Material[] materials = Material.values();
        for (Material material : materials) {
            Registries.materials.put(NamespaceID.from(material.getName()), material);
        }

        Enchantment[] enchantments = Enchantment.values();
        for (Enchantment enchantment : enchantments) {
            Registries.enchantments.put(NamespaceID.from(enchantment.getNamespaceID()), enchantment);
        }

        Particle[] particles = Particle.values();
        for (Particle particle : particles) {
            Registries.particles.put(NamespaceID.from(particle.getNamespaceID()), particle);
        }

        EntityType[] entityTypes = EntityType.values();
        for (EntityType entityType : entityTypes) {
            Registries.entityTypes.put(NamespaceID.from(entityType.getNamespaceID()), entityType);
        }

        PotionType[] potionTypes = PotionType.values();
        for (PotionType potionType : potionTypes) {
            Registries.potionTypes.put(NamespaceID.from(potionType.getNamespaceID()), potionType);
        }

        PotionEffect[] potionEffects = PotionEffect.values();
        for (PotionEffect potionEffect : potionEffects) {
            Registries.potionEffects.put(NamespaceID.from(potionEffect.getNamespaceID()), potionEffect);
        }

        SoundEvent[] sounds = SoundEvent.values();
        for (SoundEvent sound : sounds) {
            Registries.sounds.put(NamespaceID.from("minecraft", sound.getId()), sound);
        }

        StatisticType[] statistics = StatisticType.values();
        for (StatisticType statisticType : statistics) {
            Registries.statisticTypes.put(NamespaceID.from(statisticType.getNamespaceID()), statisticType);
        }

        Fluid[] fluids = Fluid.values();
        for (Fluid fluid : fluids) {
            Registries.fluids.put(NamespaceID.from(fluid.getNamespaceID()), fluid);
        }

        connectionManager = new ConnectionManager();
        // Networking
        packetProcessor = new PacketProcessor();
        packetListenerManager = new PacketListenerManager(connectionManager);

        instanceManager = new InstanceManager();
        blockManager = new TachyonBlockManager();
        commandManager = new TachyonCommandManager();
        dataManager = new TachyonDataManager();
        teamManager = new TachyonTeamManager(connectionManager);
        schedulerManager = new SchedulerManagerImpl();
        benchmarkManager = new BenchmarkManager();
        biomeManager = new TachyonBiomeManager();
        pluginManager = new TachyonPluginManager();

        updateManager = new UpdateManager(this, connectionManager);

        nettyServer = new NettyServer(packetProcessor);

        initialized = true;
    }


    @NotNull @Override
    public TachyonPluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public BiomeManager getBiomeManager() {
        return biomeManager;
    }

    @Override
    public void sendGroupedPacket(@NotNull Collection<Player> players, @NotNull ServerPacket packet) {
        PacketUtils.sendGroupedPacket(players, packet, null);
    }

    @Override
    public void sendGroupedPacket(@NotNull Collection<Player> players, @NotNull ServerPacket packet, @Nullable PlayerValidator validator) {
        PacketUtils.sendGroupedPacket(players, packet, validator);
    }

    @Override
    public @NotNull World createWorld(@NotNull UUID uuid, @NotNull LevelType levelType, DimensionType dimesionType, boolean shared) {
        InstanceContainer instance = instanceManager.createInstanceContainer(dimesionType, levelType);
        if (shared) {
            SharedInstance i = instanceManager.createSharedInstance(instance);
            return i;
        }
        return instance;
    }


    @Override
    public <E extends EntityMeta> BiFunction<Entity, Metadata, EntityMeta> createMeta(@NotNull Class<E> clazz) {
        return Registries.getEntityMetaSupplier(clazz);
    }

    @Override
    public DataManager getDataManager() {
        return this.dataManager;
    }


    /**
     * Gets the manager handling all incoming packets
     *
     * @return the packet listener manager
     */
    @Override
    public PacketListenerManager getPacketListenerManager() {
        return instance.packetListenerManager;
    }

    /**
     * Gets the netty server.
     *
     * @return the netty server
     */
    public NettyServer getNettyServer() {
        return instance.nettyServer;
    }


    @Override
    public @NotNull WorldManager getWorldManager() {
        return instanceManager;
    }

    /**
     * Gets the manager handling commands.
     *
     * @return the command manager
     */
    @NotNull
    public TachyonCommandManager getCommandManager() {
        return commandManager;
    }


    /**
     * Gets the manager handling teams.
     *
     * @return the team manager
     */
    @NotNull
    public TeamManager getTeamManager() {
        return teamManager;
    }

    /**
     * Gets the manager handling scheduled tasks.
     *
     * @return the scheduler manager
     */
    public SchedulerManager getSchedulerManager() {
        return schedulerManager;
    }

    /**
     * Gets the manager handling {@link CustomBlock} and {@link BlockPlacementRule}.
     *
     * @return the block manager
     */
    @Override
    public BlockManager getBlockmanager() {
        return blockManager;
    }


    @Override
    public @NotNull IConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public static BenchmarkManager getBenchmarkManager() {
        return instance.benchmarkManager;
    }

    /**
     * Gets the object handling the client packets processing.
     * <p>
     * Can be used if you want to convert a buffer to a client packet object.
     *
     * @return the packet processor
     */
    public static PacketProcessor getPacketProcessor() {
        return instance.packetProcessor;
    }

    /**
     * Gets if the server is up and running.
     *
     * @return true if the server is started
     */
    public static boolean isStarted() {
        return instance.started;
    }


    /**
     * Gets if the server is currently being shutdown using {@link #stopCleanly()}.
     *
     * @return true if the server is being stopped
     */
    public boolean isStopping() {
        return stopping;
    }

    @Override
    public @Nullable Entity getEntity(int id) {
        return TachyonEntity.getEntity(id);
    }

    @Override
    public @Nullable Entity getEntity(@NotNull UUID uuid) {
        return TachyonEntity.getEntity(uuid);
    }

    /**
     * Gets the manager handling the server ticks.
     *
     * @return the update manager
     */
    public static UpdateManager getUpdateManager() {
        return instance.updateManager;
    }

    /**
     * Starts the server.
     * <p>
     *
     * @param address              the server address
     * @param port                 the server port
     */
    public void start(@NotNull String address, int port) {
        Check.stateCondition(!initialized, "#start can only be called after #init");
        Check.stateCondition(started, "The server is already started");
        started = true;
        LOGGER.info("Starting " + getBrandName() + " server.");
        updateManager.start();
        // Init & start the TCP server
        nettyServer.init();
        nettyServer.start(address, port);
        LOGGER.info(getBrandName() + " server started successfully.");
        commandManager.startConsoleThread();
        pluginManager.start();
    }


    /**
     * Stops this server properly (saves if needed, kicking players, etc.)
     */
    public void stopCleanly() {
        stopping = true;
        LOGGER.info("Stopping " + getBrandName() + " server.");
        updateManager.stop();
        schedulerManager.shutdown();
        connectionManager.shutdown();
        nettyServer.stop();
        pluginManager.shutdown();
        LOGGER.info("Unloading all extensions.");
        LOGGER.info("Shutting down all thread pools.");
        benchmarkManager.disable();
        commandManager.stopConsoleThread();
        ServerThread.shutdownAll();
        LOGGER.info(getBrandName() + " server stopped successfully.");
    }

}
