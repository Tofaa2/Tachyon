package net.tachyon.listener.manager;

import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.listener.*;
import net.tachyon.network.packet.client.play.*;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.packet.client.ClientPlayPacket;
import net.tachyon.network.packet.server.ServerPacket;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PacketListenerManager {

    public final static Logger LOGGER = LoggerFactory.getLogger(PacketListenerManager.class);
    private final ConnectionManager connectionManager;

    private final Map<Class<? extends ClientPlayPacket>, PacketListenerConsumer> listeners = new ConcurrentHashMap<>();

    public PacketListenerManager(@NotNull ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        setListener(ClientKeepAlivePacket.class, KeepAliveListener::listener);
        setListener(ClientChatMessagePacket.class, ChatMessageListener::listener);
        setListener(ClientClickWindowPacket.class, WindowListener::clickWindowListener);
        setListener(ClientCloseWindow.class, WindowListener::closeWindowListener);
        setListener(ClientWindowConfirmationPacket.class, WindowListener::windowConfirmationListener);
        setListener(ClientEntityActionPacket.class, EntityActionListener::listener);
        setListener(ClientHeldItemChangePacket.class, PlayerHeldListener::heldListener);
        setListener(ClientPlayerBlockPlacementPacket.class, BlockPlacementListener::listener);
        setListener(ClientSteerVehiclePacket.class, PlayerVehicleListener::steerVehicleListener);
        setListener(ClientPlayerPacket.class, PlayerPositionListener::playerPacketListener);
        setListener(ClientPlayerLookPacket.class, PlayerPositionListener::playerLookListener);
        setListener(ClientPlayerPositionPacket.class, PlayerPositionListener::playerPositionListener);
        setListener(ClientPlayerPositionAndLookPacket.class, PlayerPositionListener::playerPositionAndLookListener);
        setListener(ClientPlayerDiggingPacket.class, PlayerDiggingListener::playerDiggingListener);
        setListener(ClientAnimationPacket.class, AnimationListener::animationListener);
        setListener(ClientInteractEntityPacket.class, UseEntityListener::useEntityListener);
        setListener(ClientStatusPacket.class, StatusListener::listener);
        setListener(ClientSettingsPacket.class, SettingsListener::listener);
        setListener(ClientCreativeInventoryActionPacket.class, CreativeInventoryActionListener::listener);
        setListener(ClientTabCompletePacket.class, TabCompleteListener::listener);
        setListener(ClientPluginMessagePacket.class, PluginMessageListener::listener);
        setListener(ClientPlayerAbilitiesPacket.class, AbilitiesListener::listener);
        setListener(ClientResourcePackStatusPacket.class, ResourcePackListener::listener);
        setListener(ClientSpectatePacket.class, SpectateListener::listener);
    }

    /**
     * Processes a packet by getting its {@link PacketListenerConsumer} and calling all the packet listeners.
     *
     * @param packet the received packet
     * @param player the player who sent the packet
     * @param <T>    the packet type
     */
    public <T extends ClientPlayPacket> void processClientPacket(@NotNull T packet, @NotNull TachyonPlayer player) {

        final Class clazz = packet.getClass();

        PacketListenerConsumer<T> packetListenerConsumer = listeners.get(clazz);

        // Listener can be null if none has been set before, call PacketConsumer anyway
        if (packetListenerConsumer == null) {
            LOGGER.warn("Packet " + clazz + " does not have any default listener! (The issue comes from Tachyon)");
        }

        final PacketController packetController = new PacketController();
        for (ClientPacketConsumer clientPacketConsumer : connectionManager.getReceivePacketConsumers()) {
            clientPacketConsumer.accept(player, packetController, packet);
        }

        if (packetController.isCancel())
            return;

        // Finally execute the listener
        if (packetListenerConsumer != null) {
            packetListenerConsumer.accept(packet, player);
        }
    }

    /**
     * Executes the consumers from {@link ConnectionManager#onPacketSend(ServerPacketConsumer)}.
     *
     * @param packet  the packet to process
     * @param players the players which should receive the packet
     * @return true if the packet is not cancelled, false otherwise
     */
    public boolean processServerPacket(@NotNull ServerPacket packet, @NotNull Collection<Player> players) {
        final List<ServerPacketConsumer> consumers = connectionManager.getSendPacketConsumers();
        if (consumers.isEmpty()) {
            return true;
        }

        final PacketController packetController = new PacketController();
        for (ServerPacketConsumer serverPacketConsumer : consumers) {
            serverPacketConsumer.accept(players, packetController, packet);
        }

        return !packetController.isCancel();
    }

    /**
     * Sets the listener of a packet.
     * <p>
     * WARNING: this will overwrite the default minestom listener, this is not reversible.
     *
     * @param packetClass the class of the packet
     * @param consumer    the new packet's listener
     * @param <T>         the type of the packet
     */
    public <T extends ClientPlayPacket> void setListener(@NotNull Class<T> packetClass, @NotNull PacketListenerConsumer<T> consumer) {
        this.listeners.put(packetClass, consumer);
    }

}
