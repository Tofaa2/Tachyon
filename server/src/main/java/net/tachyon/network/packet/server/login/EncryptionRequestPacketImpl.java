package net.tachyon.network.packet.server.login;

import net.tachyon.Tachyon;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.data.type.array.ByteArrayData;
import net.tachyon.network.player.NettyPlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class EncryptionRequestPacketImpl implements EncryptionRequestPacket {

    public byte[] publicKey;
    public byte[] nonce = new byte[4];

    public EncryptionRequestPacketImpl(NettyPlayerConnection connection) {
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
}
