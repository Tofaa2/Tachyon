package net.tachyon.entry;

import net.tachyon.MinecraftServer;
import net.tachyon.ServerSettings;
import net.tachyon.Tachyon;
import net.tachyon.UnsafeServer;
import net.tachyon.block.Block;
import net.tachyon.config.Config;
import net.tachyon.coordinate.Position;
import net.tachyon.event.player.PlayerLoginEvent;
import net.tachyon.extras.MojangAuth;
import net.tachyon.extras.optifine.OptifineSupport;
import net.tachyon.extras.proxy.BungeeCordProxy;
import net.tachyon.world.chunk.ChunkGenerator;
import net.tachyon.world.chunk.ChunkPopulator;
import net.tachyon.world.InstanceContainer;
import net.tachyon.world.InstanceManager;
import net.tachyon.world.batch.ChunkBatch;
import net.tachyon.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        File dataDir;
        if (System.getProperty("tachyon.dir") != null) {
            dataDir = new File(System.getProperty("tachyon.dir"));
        } else {
            dataDir = Path.of(".").toFile();
        }
        Config<ServerSettings> config = Config.create(ServerSettings.class, new File(dataDir, "config.json").toPath());
        config.load(ServerSettings.DEFAULT_SETTINGS);
        MinecraftServer server = new MinecraftServer(config.get(), dataDir);
        Tachyon.init(server, new UnsafeServer());
        server.init();
        OptifineSupport.enable(); // Not sure if this is needed in 1.8, but better safe than sorry.

        ServerSettings.ProxySettings proxy = config.get().proxy();
        boolean onlineMode = config.get().onlineMode();
        if (proxy.bungee()) {
            if (onlineMode) {
                Tachyon.LOGGER.warn("BungeeCord mode is enabled, but online mode is also enabled, disabling online mode...");
                onlineMode = false;
            }
            BungeeCordProxy.enable();
        }
        else if (proxy.velocity()) {
            throw new UnsupportedOperationException("Velocity is not supported yet.");
//            // TODO;
//            if (onlineMode) {
//                Tachyon.LOGGER.warn("Velocity mode is enabled, but online mode is also enabled, disabling online mode...");
//                onlineMode = false;
//            }
        }
        if (onlineMode) {
            Tachyon.LOGGER.info("Online mode is enabled, players will be authenticated with Mojang's servers.");
            MojangAuth.init();
        }
        else {
            Tachyon.LOGGER.info("Online mode is disabled, players will not be authenticated with Mojang's servers. (This is not secure!)");
        }


        InstanceContainer instance = ((InstanceManager)Tachyon.getServer().getWorldManager()).createInstanceContainer();
        instance.setChunkGenerator(new ChunkGenerator() {
            @Override
            public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
                for (int y = 0; y < 64; y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            batch.setBlock(x, y, z, Block.STONE);
                        }
                    }
                }
            }

            @Override
            public void fillBiomes(@NotNull Biome[] biomes, int chunkX, int chunkZ) {

            }

            @Nullable
            @Override
            public List<ChunkPopulator> getPopulators() {
                return null;
            }
        });

        Tachyon.getServer().getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, event -> {
            event.setSpawningWorld(instance);
            event.getPlayer().setRespawnPoint(new Position(0, 66, 0));
        });

        String[] split = config.get().host().split(":");
        String host = split[0];
        int port = Integer.parseInt(split[1]);
        server.start(host, port);
    }

}
