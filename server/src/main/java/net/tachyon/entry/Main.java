package net.tachyon.entry;

import net.tachyon.MinecraftServer;
import net.tachyon.ServerSettings;
import net.tachyon.Tachyon;
import net.tachyon.config.Config;
import net.tachyon.extras.MojangAuth;
import net.tachyon.extras.optifine.OptifineSupport;
import net.tachyon.extras.proxy.BungeeCordProxy;

import java.nio.file.Path;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        Config<ServerSettings> config = Config.create(ServerSettings.class, Path.of("config.json"));
        config.load(ServerSettings.DEFAULT_SETTINGS);
        MinecraftServer server = new MinecraftServer(config.get());
        Tachyon.setServer(server);
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
            // TODO;
            if (onlineMode) {
                Tachyon.LOGGER.warn("Velocity mode is enabled, but online mode is also enabled, disabling online mode...");
                onlineMode = false;
            }
        }
        if (onlineMode) {
            Tachyon.LOGGER.info("Online mode is enabled, players will be authenticated with Mojang's servers.");
            MojangAuth.init();
        }
        else {
            Tachyon.LOGGER.info("Online mode is disabled, players will not be authenticated with Mojang's servers. (This is not secure!)");
        }

        String[] split = config.get().host().split(":");
        String host = split[0];
        int port = Integer.parseInt(split[1]);
        server.start(host, port);
    }

}