package net.tachyon.network.packet.client.handshake;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.tachyon.MinecraftServer;
import net.tachyon.binary.BinaryReader;
import net.tachyon.entity.PlayerSkin;
import net.tachyon.extras.proxy.BungeeCordProxy;
import net.tachyon.network.packet.server.login.LoginDisconnectPacket;
import net.tachyon.network.ConnectionState;
import net.tachyon.network.packet.client.ClientPreplayPacket;
import net.tachyon.network.player.NettyPlayerConnection;
import net.tachyon.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;
import java.util.UUID;

public class HandshakePacket implements ClientPreplayPacket {

    /**
     * Text sent if a player tries to connect with an invalid version of the client
     */
    private static final Component INVALID_VERSION_TEXT = Component.text("Invalid Version, please use " + MinecraftServer.VERSION_NAME, NamedTextColor.RED);

    private static final Component INVALID_BUNGEE_FORWARDING = Component.text("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!", NamedTextColor.RED);

    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private int nextState;

    @Override
    public void read(@NotNull BinaryReader reader) {
        this.protocolVersion = reader.readVarInt();
        this.serverAddress = reader.readSizedString(BungeeCordProxy.isEnabled() ? Short.MAX_VALUE : 255);
        this.serverPort = reader.readUnsignedShort();
        this.nextState = reader.readVarInt();
    }

    @Override
    public void process(@NotNull PlayerConnection connection) {

        // Bungee support (IP forwarding)
        if (BungeeCordProxy.isEnabled() && connection instanceof NettyPlayerConnection nettyPlayerConnection) {

            if (serverAddress != null) {
                final String[] split = serverAddress.split("\00");

                if (split.length == 3 || split.length == 4) {
                    this.serverAddress = split[0];

                    final SocketAddress socketAddress = new java.net.InetSocketAddress(split[1],
                            ((java.net.InetSocketAddress) connection.getRemoteAddress()).getPort());
                    nettyPlayerConnection.setRemoteAddress(socketAddress);

                    UUID playerUuid = UUID.fromString(
                            split[2]
                                    .replaceFirst(
                                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                                    )
                    );
                    PlayerSkin playerSkin = null;

                    if (split.length == 4) {
                        playerSkin = BungeeCordProxy.readSkin(split[3]);
                    }

                    nettyPlayerConnection.UNSAFE_setBungeeUuid(playerUuid);
                    nettyPlayerConnection.UNSAFE_setBungeeSkin(playerSkin);

                } else {
                    nettyPlayerConnection.sendPacket(new LoginDisconnectPacket(INVALID_BUNGEE_FORWARDING));
                    nettyPlayerConnection.disconnect();
                    return;
                }
            } else {
                // Happen when a client ping the server, ignore
                return;
            }
        }

        switch (nextState) {
            case 1:
                connection.setConnectionState(ConnectionState.STATUS);
                break;
            case 2:
                if (protocolVersion == MinecraftServer.PROTOCOL_VERSION) {
                    connection.setConnectionState(ConnectionState.LOGIN);

                    if (connection instanceof NettyPlayerConnection) {
                        // Give to the connection the server info that the client used
                        ((NettyPlayerConnection) connection).refreshServerInformation(serverAddress, serverPort);
                    }
                } else {
                    // Incorrect client version
                    connection.sendPacket(new LoginDisconnectPacket(INVALID_VERSION_TEXT));
                    connection.disconnect();
                }
                break;
            default:
                // Unexpected error
                break;
        }
    }
}
