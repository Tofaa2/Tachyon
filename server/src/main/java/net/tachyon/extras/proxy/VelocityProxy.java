package net.tachyon.extras.proxy;

import io.netty.buffer.ByteBuf;
import net.tachyon.binary.BinaryReader;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.utils.binary.TachyonBinaryReader;
import org.jetbrains.annotations.NotNull;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public final class VelocityProxy {

    private VelocityProxy() {}
    public static final String PLAYER_INFO_CHANNEL = "velocity:player_info";
    private static final int SUPPORTED_FORWARDING_VERSION = 1;
    private static final String MAC_ALGORITHM = "HmacSHA256";

    private static volatile boolean enabled;
    private static Key key;


    /**
     * Enables velocity modern forwarding.
     *
     * @param secret the forwarding secret,
     *               be sure to do not hardcode it in your code but to retrieve it from a file or anywhere else safe
     */
    public static void enable(@NotNull String secret) {
        VelocityProxy.enabled = true;
        VelocityProxy.key = new SecretKeySpec(secret.getBytes(), MAC_ALGORITHM);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean verifyIntegrity(@NotNull BinaryReader reader) {
        return false;
    }

}
