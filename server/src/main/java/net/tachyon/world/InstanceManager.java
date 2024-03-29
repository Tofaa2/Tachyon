package net.tachyon.world;

import net.tachyon.MinecraftServer;
import net.tachyon.utils.validate.Check;
import net.tachyon.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * Used to register {@link Instance}.
 */
public final class InstanceManager implements WorldManager {

    private final Set<Instance> instances = new CopyOnWriteArraySet<>();

    /**
     * Registers an {@link Instance} internally.
     * <p>
     * Note: not necessary if you created your instance using {@link #createInstanceContainer()} or {@link #createSharedInstance(InstanceContainer)}
     * but only if you instantiated your instance object manually
     *
     * @param instance the {@link Instance} to register
     */
    public void registerInstance(@NotNull Instance instance) {
        Check.stateCondition(instance instanceof SharedInstance,
                "Please use InstanceManager#registerSharedInstance to register a shared instance");
        UNSAFE_registerInstance(instance);
    }

    /**
     * Creates and register an {@link InstanceContainer} with a specified {@link DimensionType}
     * @param dimensionType   the {@link DimensionType} of the instance
     * @param levelType       the {@link LevelType} of the instance
     * @return the created {@link InstanceContainer}
     */
    @NotNull
    public InstanceContainer createInstanceContainer(@NotNull DimensionType dimensionType, @NotNull LevelType levelType) {
        final InstanceContainer instanceContainer = new InstanceContainer(UUID.randomUUID(), dimensionType, levelType);
        registerInstance(instanceContainer);
        return instanceContainer;
    }


    /**
     * Creates and register an {@link InstanceContainer} with the specified {@link DimensionType}.
     *
     * @param dimensionType the {@link DimensionType} of the instance
     * @return the created {@link InstanceContainer}
     */
    @NotNull
    public InstanceContainer createInstanceContainer(@NotNull DimensionType dimensionType) {
        return createInstanceContainer(dimensionType, LevelType.FLAT);
    }

    /**
     * Creates and register an {@link InstanceContainer}.
     *
     * @return the created {@link InstanceContainer}
     */
    @NotNull
    public InstanceContainer createInstanceContainer() {
        return createInstanceContainer(DimensionType.OVERWORLD);
    }

    /**
     * Registers a {@link SharedInstance}.
     * <p>
     * WARNING: the {@link SharedInstance} needs to have an {@link InstanceContainer} assigned to it.
     *
     * @param sharedInstance the {@link SharedInstance} to register
     * @return the registered {@link SharedInstance}
     * @throws NullPointerException if {@code sharedInstance} doesn't have an {@link InstanceContainer} assigned to it
     */
    @NotNull
    public SharedInstance registerSharedInstance(@NotNull SharedInstance sharedInstance) {
        final InstanceContainer instanceContainer = sharedInstance.getInstanceContainer();
        Check.notNull(instanceContainer, "SharedInstance needs to have an InstanceContainer to be created!");

        instanceContainer.addSharedInstance(sharedInstance);
        UNSAFE_registerInstance(sharedInstance);
        return sharedInstance;
    }

    /**
     * Creates and register a {@link SharedInstance}.
     *
     * @param instanceContainer the container assigned to the shared instance
     * @return the created {@link SharedInstance}
     * @throws IllegalStateException if {@code instanceContainer} is not registered
     */
    @NotNull
    public SharedInstance createSharedInstance(@NotNull InstanceContainer instanceContainer) {
        Check.notNull(instanceContainer, "Instance container cannot be null when creating a SharedInstance!");
        Check.stateCondition(!instanceContainer.isRegistered(), "The container needs to be register in the InstanceManager");

        final SharedInstance sharedInstance = new SharedInstance(UUID.randomUUID(), instanceContainer);
        return registerSharedInstance(sharedInstance);
    }

    /**
     * Unregisters the {@link Instance} internally.
     * <p>
     * If {@code instance} is an {@link InstanceContainer} all chunks are unloaded.
     *
     * @param instance the {@link Instance} to unregister
     */
    public void unregisterInstance(@NotNull Instance instance) {
        Check.stateCondition(!instance.getPlayers().isEmpty(), "You cannot unregister an instance with players inside.");

        synchronized (instance) {
            // Unload all chunks
            if (instance instanceof InstanceContainer) {
                InstanceContainer instanceContainer = (InstanceContainer) instance;

                Set<Chunk> scheduledChunksToRemove = instanceContainer.scheduledChunksToRemove;
                synchronized (scheduledChunksToRemove) {
                    scheduledChunksToRemove.addAll(instanceContainer.getChunks());
                    instanceContainer.UNSAFE_unloadChunks();
                }
            }

            instance.setRegistered(false);
            this.instances.remove(instance);
            MinecraftServer.getUpdateManager().signalInstanceDelete(instance);
        }
    }

    /**
     * Gets all the registered instances.
     *
     * @return an unmodifiable {@link Set} containing all the registered instances
     */
    @NotNull
    public Set<Instance> getInstances() {
        return Collections.unmodifiableSet(instances);
    }

    /**
     * Gets an instance by the given UUID.
     *
     * @param uuid UUID of the instance
     * @return the instance with the given UUID, null if not found
     */
    @Nullable
    public Instance getInstance(@NotNull UUID uuid) {
        Optional<Instance> instance = getInstances()
                .stream()
                .filter(someInstance -> someInstance.getUuid().equals(uuid))
                .findFirst();
        return instance.orElse(null);
    }

    /**
     * Registers an {@link Instance} internally.
     * <p>
     * Unsafe because it does not check if {@code instance} is a {@link SharedInstance} to verify its container.
     *
     * @param instance the {@link Instance} to register
     */
    private void UNSAFE_registerInstance(@NotNull Instance instance) {
        instance.setRegistered(true);
        this.instances.add(instance);
        MinecraftServer.getUpdateManager().signalInstanceCreate(instance);
    }

    @Override
    public @NotNull World createWorld(@NotNull UUID uuid, @NotNull DimensionType dimension, @NotNull LevelType levelType) {
        InstanceContainer instance = new InstanceContainer(uuid, dimension, levelType);
        registerInstance(instance);
        return instance;
    }

    @Override
    public @NotNull World createWorld(@NotNull DimensionType dimension, @NotNull LevelType levelType) {
        return createInstanceContainer(dimension, levelType);
    }

    @Override
    public @NotNull World createWorld(@NotNull UUID uuid, @NotNull DimensionType dimension) {
        InstanceContainer instance = new InstanceContainer(uuid, dimension, LevelType.FLAT);
        registerInstance(instance);
        return instance;
    }

    @Override
    public @NotNull World createWorld(@NotNull DimensionType dimension) {
        return createInstanceContainer(dimension);
    }

    @Override
    public @NotNull World createWorld() {
        return createInstanceContainer();
    }

    @Override
    public @NotNull SharedWorld createSharedWorld(@NotNull World world) {
        return createSharedInstance((InstanceContainer) world) ;
    }

    @Override
    public @NotNull Set<World> getWorlds() {
        return Set.copyOf(this.instances);
    }

    @Override
    public @NotNull Set<SharedWorld> getSharedWorlds() {
        return this.instances.stream()
                .filter(instance -> instance instanceof SharedInstance)
                .map(instance -> (SharedInstance) instance)
                .collect(Collectors.toUnmodifiableSet());
    }
}
