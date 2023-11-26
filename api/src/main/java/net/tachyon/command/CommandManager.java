package net.tachyon.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommandManager {


    void register(@NotNull Command command);

    void unregister(@NotNull Command command);

    @Nullable Command getCommand(@NotNull String name);

    boolean commandExists(@NotNull String name);

    /**
     * Executes a command for a {@link ConsoleSender}.
     *
     * @param sender  the sender of the command
     * @param command the raw command string (without the command prefix)
     * @return the execution result
     */
    @NotNull CommandResult execute(@NotNull CommandSender sender, @NotNull String command);

    /**
     * Executes the command using a {@link ServerSender} to do not
     * print the command messages, and rely instead on the command return data.
     *
     * @see #execute(CommandSender, String)
     */
    @NotNull CommandResult execute(@NotNull String command);

    @NotNull CommandDispatcher getDispatcher();

    @Nullable CommandCallback getUnknownCommandCallback();

    void setUnknownCommandCallback(@Nullable CommandCallback callback);

    @NotNull ConsoleSender getConsoleSender();
}
