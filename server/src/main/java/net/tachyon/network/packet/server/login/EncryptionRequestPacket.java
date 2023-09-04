package net.tachyon.network.packet.server.login;

import net.tachyon.Tachyon;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.type.array.ByteArrayData;
import net.tachyon.extras.MojangAuth;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import net.tachyon.network.player.NettyPlayerConnection;
import net.tachyon.utils.binary.TachyonBinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class EncryptionRequestPacket implements ServerPacket {

    public byte[] publicKey;
    public byte[] nonce = new byte[4];

    public EncryptionRequestPacket(NettyPlayerConnection connection) {
        ThreadLocalRandom.current().nextBytes(nonce);
        connection.setNonce(nonce);
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString("");
        final byte[] publicKey = Tachyon.getServer().getSecretKeypair().getPublic().getEncoded();
        ByteArrayData.encodeByteArray(writer, publicKey);
        ByteArrayData.encodeByteArray(writer, nonce);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_ENCRYPTION_REQUEST;
    }
}
