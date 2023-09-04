package net.tachyon.utils.async;

import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class AsyncUtils {

    public static void runAsync(@NotNull Runnable runnable) {
        CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Tachyon.getServer().getExceptionManager().handleException(e);
            }
        });
    }

}
