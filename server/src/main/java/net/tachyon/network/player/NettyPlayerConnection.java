package net.tachyon.network.player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.entity.Player;
import net.tachyon.entity.PlayerSkin;
import net.tachyon.extras.mojangAuth.Decrypter;
import net.tachyon.extras.mojangAuth.Encrypter;
import net.tachyon.extras.mojangAuth.MojangCrypt;
import net.tachyon.listener.manager.PacketListenerManager;
import net.tachyon.network.packet.client.handshake.HandshakePacket;
import net.tachyon.network.packet.server.login.SetCompressionPacket;
import net.tachyon.network.ConnectionState;
import net.tachyon.network.netty.NettyServer;
import net.tachyon.network.netty.codec.PacketCompressor;
import net.tachyon.network.netty.packet.FramedPacket;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.utils.BufUtils;
import net.tachyon.utils.PacketUtils;
import net.tachyon.utils.cache.CacheablePacket;
import net.tachyon.utils.cache.TemporaryCache;
import net.tachyon.utils.cache.TimedBuffer;
import net.tachyon.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKey;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a networking connection with Netty.
 * <p>
 * It is the implementation used for all network client.
 */
public class NettyPlayerConnection implements PlayerConnection {

    protected static final PacketListenerManager PACKET_LISTENER_MANAGER = MinecraftServer.getPacketListenerManager();

    private final SocketChannel channel;

    private Player player;
    private volatile ConnectionState connectionState;
    private boolean online;

    // Text used to kick a client sending too many packets
    private static final Component rateLimitKickMessage = Component.text("Too Many Packets", NamedTextColor.RED);

    //Connection Stats
    private final AtomicInteger packetCounter = new AtomicInteger(0);
    private final AtomicInteger lastPacketCounter = new AtomicInteger(0);
    private short tickCounter = 0;
    private SocketAddress remoteAddress;

    private boolean encrypted = false;
    private boolean compressed = false;

    //Could be null. Only used for Mojang Auth
    private byte[] nonce = new byte[4];

    // Data from client packets
    private String loginUsername;
    private String serverAddress;
    private int serverPort;

    // Used for the login plugin request packet, to retrieve the channel from a message id,
    // cleared once the player enters the play state
    private final Map<Integer, String> pluginRequestMap = new ConcurrentHashMap<>();

    // Bungee
    private UUID bungeeUuid;
    private PlayerSkin bungeeSkin;

    private static final int FLUSH_SIZE = 20000;
    private final ByteBuf tickBuffer = BufUtils.getBuffer(true);

    public NettyPlayerConnection(@NotNull SocketChannel channel) {
        this.online = true;
        this.connectionState = ConnectionState.UNKNOWN;
        this.channel = channel;
        this.remoteAddress = channel.remoteAddress();
    }

    @Override
    public void update() {
        // Flush
        if (channel.isActive()) {
            writeWaitingPackets();
            this.channel.flush();
        }
        // Check rate limit
        if (Tachyon.getServer().getPacketRateLimit() > 0) {
            tickCounter++;
            if (tickCounter % Tachyon.getServer().getTargetTPS() == 0 && tickCounter > 0) {
                tickCounter = 0;
                // Retrieve the packet count
                final int count = packetCounter.get();
                this.lastPacketCounter.set(count);
                this.packetCounter.set(0);
                if (count > Tachyon.getServer().getPacketRateLimit()) {
                    // Sent too many packets
                    player.kick(rateLimitKickMessage);
                    disconnect();
                }
            }
        }
    }

    /**
     * Sets the encryption key and add the codecs to the pipeline.
     *
     * @param secretKey the secret key to use in the encryption
     * @throws IllegalStateException if encryption is already enabled for this connection
     */
    public void setEncryptionKey(@NotNull SecretKey secretKey) {
        Check.stateCondition(encrypted, "Encryption is already enabled!");
        this.encrypted = true;
        channel.pipeline().addBefore(NettyServer.GROUPED_PACKET_HANDLER_NAME, NettyServer.DECRYPT_HANDLER_NAME,
                new Decrypter(MojangCrypt.getCipher(2, secretKey)));
        channel.pipeline().addBefore(NettyServer.GROUPED_PACKET_HANDLER_NAME, NettyServer.ENCRYPT_HANDLER_NAME,
                new Encrypter(MojangCrypt.getCipher(1, secretKey)));
    }

    /**
     * Enables compression and add a new codec to the pipeline.
     *
     * @throws IllegalStateException if encryption is already enabled for this connection
     */
    public void startCompression() {
        Check.stateCondition(compressed, "Compression is already enabled!");
        final int threshold = Tachyon.getServer().getCompressionThreshold();
        Check.stateCondition(threshold == 0, "Compression cannot be enabled because the threshold is equal to 0");

        this.compressed = true;
        writeAndFlush(new SetCompressionPacket(threshold));
        channel.pipeline().addAfter(NettyServer.FRAMER_HANDLER_NAME, NettyServer.COMPRESSOR_HANDLER_NAME,
                new PacketCompressor(threshold));
    }

    /**
     * Writes a packet to the connection channel.
     * <p>
     * All packets are flushed during {@link net.tachyon.entity.TachyonPlayer#update(long)}.
     *
     * @param serverPacket the packet to write
     */
    @Override
    public void sendPacket(@NotNull ServerPacket serverPacket) {
        if (!channel.isActive())
            return;

        if (shouldSendPacket(serverPacket)) {
            if (getPlayer() != null) {
                // Flush happen during #update()
                if (serverPacket instanceof CacheablePacket cacheablePacket && Tachyon.getServer().isPacketCachingEnabled()) {
                    final UUID identifier = cacheablePacket.getIdentifier();

                    if (identifier == null) {
                        // This packet explicitly asks to do not retrieve the cache
                        write(serverPacket);
                    } else {
                        final long timestamp = cacheablePacket.getTimestamp();
                        // Try to retrieve the cached buffer
                        TemporaryCache<TimedBuffer> temporaryCache = cacheablePacket.getCache();
                        TimedBuffer timedBuffer = temporaryCache.retrieve(identifier);

                        // Update the buffer if non-existent or outdated
                        final boolean shouldUpdate = timedBuffer == null ||
                                timestamp > timedBuffer.getTimestamp();

                        if (shouldUpdate) {
                            final ByteBuf buffer = PacketUtils.createFramedPacket(serverPacket, false);
                            timedBuffer = new TimedBuffer(buffer, timestamp);
                        }

                        temporaryCache.cache(identifier, timedBuffer);
                        write(new FramedPacket(timedBuffer.getBuffer()));
                    }

                } else {
                    write(serverPacket);
                }
            } else {
                // Player is probably not logged yet
                writeAndFlush(serverPacket);
            }
        }
    }

    public void write(@NotNull Object message) {
        if (message instanceof FramedPacket) {
            final FramedPacket framedPacket = (FramedPacket) message;
            synchronized (tickBuffer) {
                final ByteBuf body = framedPacket.getBody();
                tickBuffer.writeBytes(body, body.readerIndex(), body.readableBytes());
                preventiveWrite();
            }
            return;
        } else if (message instanceof ServerPacket) {
            final ServerPacket serverPacket = (ServerPacket) message;
            final ByteBuf buffer = PacketUtils.createFramedPacket(serverPacket, true);
            synchronized (tickBuffer) {
                tickBuffer.writeBytes(buffer);
                preventiveWrite();
            }
            buffer.release();
            return;
        } else if (message instanceof ByteBuf) {
            synchronized (tickBuffer) {
                tickBuffer.writeBytes((ByteBuf) message);
                preventiveWrite();
            }
            return;
        }
        throw new UnsupportedOperationException("type " + message.getClass() + " is not supported");
    }

    public void writeAndFlush(@NotNull Object message) {
        writeWaitingPackets();
        ChannelFuture channelFuture = channel.writeAndFlush(message);

        channelFuture.addListener(future -> {
            if (!future.isSuccess() && channel.isActive()) {
                Tachyon.getServer().getExceptionManager().handleException(future.cause());
            }
        });
    }

    private void preventiveWrite() {
        if (tickBuffer.writerIndex() > FLUSH_SIZE) {
            writeWaitingPackets();
        }
    }

    private void writeWaitingPackets() {
        synchronized (tickBuffer) {
            final ByteBuf copy = tickBuffer.copy();

            ChannelFuture channelFuture = channel.write(new FramedPacket(copy));
            channelFuture.addListener(future -> copy.release());

            // Netty debug
            channelFuture.addListener(future -> {
                if (!future.isSuccess() && channel.isActive()) {
                    Tachyon.getServer().getExceptionManager().handleException(future.cause());
                }
            });
            tickBuffer.clear();
        }
    }

    /**
     * Gets the remote address of the client.
     *
     * @return the remote address
     */
    @NotNull
    @Override
    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * Changes the internal remote address field.
     * <p>
     * Mostly unsafe, used internally when interacting with a proxy.
     *
     * @param remoteAddress the new connection remote address
     */
    public void setRemoteAddress(@NotNull SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public void disconnect() {
        this.channel.close();
    }

    @NotNull
    public Channel getChannel() {
        return channel;
    }

    /**
     * Retrieves the username received from the client during connection.
     * <p>
     * This value has not been checked and could be anything.
     *
     * @return the username given by the client, unchecked
     */
    @Nullable
    public String getLoginUsername() {
        return loginUsername;
    }

    /**
     * Sets the internal login username field.
     *
     * @param loginUsername the new login username field
     */
    public void UNSAFE_setLoginUsername(@NotNull String loginUsername) {
        this.loginUsername = loginUsername;
    }

    /**
     * Gets the server address that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server address used
     */
    @Nullable
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Gets the server port that the client used to connect.
     * <p>
     * WARNING: it is given by the client, it is possible for it to be wrong.
     *
     * @return the server port used
     */
    public int getServerPort() {
        return serverPort;
    }

    @Nullable
    public UUID getBungeeUuid() {
        return bungeeUuid;
    }

    public void UNSAFE_setBungeeUuid(UUID bungeeUuid) {
        this.bungeeUuid = bungeeUuid;
    }

    @Nullable
    public PlayerSkin getBungeeSkin() {
        return bungeeSkin;
    }

    public void UNSAFE_setBungeeSkin(PlayerSkin bungeeSkin) {
        this.bungeeSkin = bungeeSkin;
    }

    /**
     * Adds an entry to the plugin request map.
     * <p>
     * Only working if {@link #getConnectionState()} is {@link ConnectionState#LOGIN}.
     *
     * @param messageId the message id
     * @param channel   the packet channel
     * @throws IllegalStateException if a messageId with the value {@code messageId} already exists for this connection
     */
    public void addPluginRequestEntry(int messageId, @NotNull String channel) {
        if (!getConnectionState().equals(ConnectionState.LOGIN)) {
            return;
        }
        Check.stateCondition(pluginRequestMap.containsKey(messageId), "You cannot have two messageId with the same value");
        this.pluginRequestMap.put(messageId, channel);
    }

    /**
     * Gets a request channel from a message id, previously cached using {@link #addPluginRequestEntry(int, String)}.
     * <p>
     * Be aware that the internal map is cleared once the player enters the play state.
     *
     * @param messageId the message id
     * @return the channel linked to the message id, null if not found
     */
    @Nullable
    public String getPluginRequestChannel(int messageId) {
        return pluginRequestMap.get(messageId);
    }

    public void setConnectionState(@NotNull ConnectionState connectionState) {
        this.connectionState = connectionState;
        // Clear the plugin request map (since it is not used anymore)
        if (connectionState.equals(ConnectionState.PLAY)) {
            this.pluginRequestMap.clear();
        }
    }

    /**
     * Used in {@link HandshakePacket} to change the internal fields.
     *
     * @param serverAddress the server address which the client used
     * @param serverPort    the server port which the client used
     */
    public void refreshServerInformation(@Nullable String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @NotNull
    public ByteBuf getTickBuffer() {
        return tickBuffer;
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    @NotNull
    public AtomicInteger getPacketCounter() {
        return packetCounter;
    }

    /**
     * Returns a printable identifier for this connection, will be the player username
     * or the connection remote address.
     *
     * @return this connection identifier
     */
    @NotNull
    public String getIdentifier() {
        final Player player = getPlayer();
        return player != null ?
                player.getUsername() :
                getRemoteAddress().toString();
    }

    /**
     * Gets the player linked to this connection.
     *
     * @return the player, can be null if not initialized yet
     */
    @Nullable
    public Player getPlayer() {
        return player;
    }

    /**
     * Changes the player linked to this connection.
     * <p>
     * WARNING: unsafe.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets if the client is still connected to the server.
     *
     * @return true if the player is online, false otherwise
     */
    public boolean isOnline() {
        return online;
    }

    public void refreshOnline(boolean online) {
        this.online = online;
    }

    /**
     * Gets the client connection state.
     *
     * @return the client connection state
     */
    @NotNull
    public ConnectionState getConnectionState() {
        return connectionState;
    }

    /**
     * Gets the number of packet the client sent over the last second.
     *
     * @return the number of packet sent over the last second
     */
    public int getLastPacketCounter() {
        return lastPacketCounter.get();
    }

    protected boolean shouldSendPacket(@NotNull ServerPacket serverPacket) {
        return player == null ||
                PACKET_LISTENER_MANAGER.processServerPacket(serverPacket, Collections.singleton(player));
    }

    @Override
    public String toString() {
        return "PlayerConnection{" +
                "connectionState=" + connectionState +
                ", identifier=" + getIdentifier() +
                '}';
    }

}
