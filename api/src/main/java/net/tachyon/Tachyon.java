package net.tachyon;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tachyon {

    public static final Logger LOGGER = LoggerFactory.getLogger("Tachyon");

    private static Server server;
    private static Unsafe unsafe;

    @ApiStatus.Internal
    public static void init(@NotNull Server server, @NotNull Unsafe unsafe) {
        Tachyon.server = server;
        Tachyon.unsafe = unsafe;
    }

    public static @NotNull Server getServer() {
        return server;
    }

    public static @NotNull Unsafe getUnsafe() {
        return unsafe;
    }
}
