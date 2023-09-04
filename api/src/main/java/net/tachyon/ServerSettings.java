package net.tachyon;

import net.tachyon.world.Difficulty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ServerSettings(
        @NotNull String brand,
        @NotNull String host,
        boolean onlineMode,
        int targetTps,
        int maxPlayers,
        @NotNull List<String> motd,
        @NotNull Difficulty difficulty,
        int chunkViewDistance,
        int entityViewDistance,
        @NotNull PacketSettings packet,
        @NotNull ThreadSettings thread,
        @NotNull ProxySettings proxy
) {

    public static final ServerSettings DEFAULT_SETTINGS = new ServerSettings(
            "Tachyon",
            "0.0.0.0:25565",
            false,
            20,
            100,
            List.of("A tachyon server", "Welcome to my server"),
            Difficulty.NORMAL,
            8, 5,
            new PacketSettings(
                    256,
                    300,
                    30000,
                    true,
                    true
            ),
            new ThreadSettings(-1,1, 4, 4),
            new ProxySettings(false, false, null)
    );

    public record ThreadSettings(
            int nettyThreadCount,
            int schedulerThreadCount,
            int blockBatchThreadCount,
            int chunkSavingThreadCount) {}

    public record PacketSettings(
            int compressionThreshold,
            int rateLimitLimit,
            int sizeLimit,
            boolean caching,
            boolean grouping) {}

    public record ProxySettings(
            boolean bungee,
            boolean velocity,
            @Nullable String secret) { }

}
