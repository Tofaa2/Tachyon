package net.tachyon;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tachyon {

    public static final String VERSION_NAME = "1.8.9";
    public static final int PROTOCOL_VERSION = 47;
    public static final Logger LOGGER = LoggerFactory.getLogger("Tachyon");

    private static Server server;

    @ApiStatus.Internal
    public static void setServer(@NotNull Server server) {
        Tachyon.server = server;
    }

    public static @NotNull Server getServer() {
        return server;
    }

}
