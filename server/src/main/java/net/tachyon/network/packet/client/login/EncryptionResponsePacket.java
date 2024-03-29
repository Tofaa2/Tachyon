package net.tachyon.network.packet.client.login;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.binary.BinaryReader;
import net.tachyon.data.type.array.ByteArrayData;
import net.tachyon.extras.MojangAuth;
import net.tachyon.extras.mojangAuth.MojangCrypt;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.player.NettyPlayerConnection;
import net.tachyon.network.player.PlayerConnection;
import net.tachyon.network.packet.client.ClientPreplayPacket;
import net.tachyon.utils.async.AsyncUtils;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.util.Arrays;

public class EncryptionResponsePacket implements ClientPreplayPacket {

    private byte[] sharedSecret;
    private byte[] verifyToken;

    @Override
    public void process(@NotNull PlayerConnection connection) {

        // Encryption is only support for netty connection
        if (!(connection instanceof NettyPlayerConnection nettyConnection)) {
            return;
        }
        AsyncUtils.runAsync(() -> {
            try {
                final String loginUsername = nettyConnection.getLoginUsername();
                if (!Arrays.equals(nettyConnection.getNonce(), getNonce())) {
                    MinecraftServer.LOGGER.error("{} tried to login with an invalid nonce!", loginUsername);
                    return;
                }
                if (!loginUsername.isEmpty()) {

                    final byte[] digestedData = MojangCrypt.digestData("", Tachyon.getServer().getSecretKeypair().getPublic(), getSecretKey());

                    if (digestedData == null) {
                        // Incorrect key, probably because of the client
                        MinecraftServer.LOGGER.error("Connection {} failed initializing encryption.", nettyConnection.getRemoteAddress());
                        connection.disconnect();
                        return;
                    }

                    final String string3 = new BigInteger(digestedData).toString(16);
                    final GameProfile gameProfile = MojangAuth.getSessionService().hasJoinedServer(new GameProfile(null, loginUsername), string3);
                    nettyConnection.setEncryptionKey(getSecretKey());

                    MinecraftServer.LOGGER.info("UUID of player {} is {}", loginUsername, gameProfile.getId());
                    ((ConnectionManager)Tachyon.getServer().getConnectionManager()).startPlayState(connection, gameProfile.getId(), gameProfile.getName(), true);
                }
            } catch (AuthenticationUnavailableException e) {
                Tachyon.getServer().getExceptionManager().handleException(e);
            }
        });
    }

    @Override
    public void read(@NotNull BinaryReader reader) {
        sharedSecret = ByteArrayData.decodeByteArray(reader);
        verifyToken = ByteArrayData.decodeByteArray(reader);
    }

    public SecretKey getSecretKey() {
        return MojangCrypt.decryptByteToSecretKey(Tachyon.getServer().getSecretKeypair().getPrivate(), sharedSecret);
    }

    public byte[] getNonce() {
        return Tachyon.getServer().getSecretKeypair().getPrivate() == null ?
                this.verifyToken : MojangCrypt.decryptUsingKey(Tachyon.getServer().getSecretKeypair().getPrivate(), this.verifyToken);
    }
}
