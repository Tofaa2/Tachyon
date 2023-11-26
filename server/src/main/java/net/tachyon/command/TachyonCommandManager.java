package net.tachyon.command;

import net.tachyon.Tachyon;
import net.tachyon.entity.Player;
import net.tachyon.event.player.PlayerCommandEvent;
import net.tachyon.utils.validate.Check;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Manager used to register {@link Command} and {@link CommandProcessor}.
 * <p>
 * It is also possible to simulate a command using {@link #execute(CommandSender, String)}.
 */
public final class TachyonCommandManager implements CommandManager {

    public static final String COMMAND_PREFIX = "/";

    private volatile boolean running = true;

    private final ServerSender serverSender = new ServerSender();
    private final ConsoleSender consoleSender = new ConsoleSender();

    private final CommandDispatcher dispatcher = new CommandDispatcher();
    private final Map<String, CommandProcessor> commandProcessorMap = new HashMap<>();

    private CommandCallback unknownCommandCallback;

    public TachyonCommandManager() {
    }

    /**
     * Stops the console responsible for the console commands processing.
     * <p>
     * WARNING: it cannot be re-run later.
     */
    public void stopConsoleThread() {
        running = false;
    }

    /**
     * Registers a {@link Command}.
     *
     * @param command the command to register
     * @throws IllegalStateException if a command with the same name already exists
     */
    public synchronized void register(@NotNull Command command) {
        Check.stateCondition(commandExists(command.getName()),
                "A command with the name " + command.getName() + " is already registered!");
        if (command.getAliases() != null) {
            for (String alias : command.getAliases()) {
                Check.stateCondition(commandExists(alias),
                        "A command with the name " + alias + " is already registered!");
            }
        }
        this.dispatcher.register(command);
    }

    /**
     * Removes a command from the currently registered commands.
     * Does nothing if the command was not registered before
     *
     * @param command the command to remove
     */
    public void unregister(@NotNull Command command) {
        this.dispatcher.unregister(command);
    }

    /**
     * Gets the {@link Command} registered by {@link #register(Command)}.
     *
     * @param commandName the command name
     * @return the command associated with the name, null if not any
     */
    @Nullable
    public Command getCommand(@NotNull String commandName) {
        return dispatcher.findCommand(commandName);
    }

    /**
     * Registers a {@link CommandProcessor}.
     *
     * @param commandProcessor the command to register
     * @throws IllegalStateException if a command with the same name already exists
     * @deprecated use {@link Command} or {@link SimpleCommand} instead
     */
    @Deprecated
    public synchronized void register(@NotNull CommandProcessor commandProcessor) {
        final String commandName = commandProcessor.getCommandName().toLowerCase();
        Check.stateCondition(commandExists(commandName),
                "A command with the name " + commandName + " is already registered!");
        this.commandProcessorMap.put(commandName, commandProcessor);
        // Register aliases
        final String[] aliases = commandProcessor.getAliases();
        if (aliases != null && aliases.length > 0) {
            for (String alias : aliases) {
                Check.stateCondition(commandExists(alias),
                        "A command with the name " + alias + " is already registered!");

                this.commandProcessorMap.put(alias.toLowerCase(), commandProcessor);
            }
        }
    }

    /**
     * Gets the {@link CommandProcessor} registered by {@link #register(CommandProcessor)}.
     *
     * @param commandName the command name
     * @return the command associated with the name, null if not any
     * @deprecated use {@link #getCommand(String)} instead
     */
    @Deprecated
    @Nullable
    public CommandProcessor getCommandProcessor(@NotNull String commandName) {
        return commandProcessorMap.get(commandName.toLowerCase());
    }

    /**
     * Gets if a command with the name {@code commandName} already exists or name.
     *
     * @param commandName the command name to check
     * @return true if the command does exist
     */
    public boolean commandExists(@NotNull String commandName) {
        commandName = commandName.toLowerCase();
        return dispatcher.findCommand(commandName) != null ||
                commandProcessorMap.get(commandName) != null;
    }

    @NotNull
    public CommandResult execute(@NotNull CommandSender sender, @NotNull String command) {

        // Command event
        if (sender instanceof Player player) {

            PlayerCommandEvent playerCommandEvent = new PlayerCommandEvent(player, command);
            ( player).callEvent(PlayerCommandEvent.class, playerCommandEvent);

            if (playerCommandEvent.isCancelled())
                return CommandResult.of(CommandResult.Type.CANCELLED, command);

            command = playerCommandEvent.getCommand();
        }

        // Process the command

        {
            // Check for rich-command
            final CommandResult result = this.dispatcher.execute(sender, command);
            if (result.getType() != CommandResult.Type.UNKNOWN) {
                return result;
            } else {
                // Check for legacy-command
                final String[] splitCommand = command.split(StringUtils.SPACE);
                final String commandName = splitCommand[0];
                final CommandProcessor commandProcessor = commandProcessorMap.get(commandName.toLowerCase());
                if (commandProcessor == null) {
                    if (unknownCommandCallback != null) {
                        this.unknownCommandCallback.apply(sender, command);
                    }
                    return CommandResult.of(CommandResult.Type.UNKNOWN, command);
                }

                // Execute the legacy-command
                final String[] args = command.substring(command.indexOf(StringUtils.SPACE) + 1).split(StringUtils.SPACE);
                commandProcessor.process(sender, commandName, args);
                return CommandResult.of(CommandResult.Type.SUCCESS, command);
            }
        }
    }

    /**
     * Executes the command using a {@link ServerSender} to do not
     * print the command messages, and rely instead on the command return data.
     *
     * @see #execute(CommandSender, String)
     */
    @NotNull
    public CommandResult execute(@NotNull String command) {
        return execute(serverSender, command);
    }

    @NotNull
    public CommandDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Gets the callback executed once an unknown command is run.
     *
     * @return the unknown command callback, null if not any
     */
    @Nullable
    public CommandCallback getUnknownCommandCallback() {
        return unknownCommandCallback;
    }

    /**
     * Sets the callback executed once an unknown command is run.
     *
     * @param unknownCommandCallback the new unknown command callback,
     *                               setting it to null mean that nothing will be executed
     */
    public void setUnknownCommandCallback(@Nullable CommandCallback unknownCommandCallback) {
        this.unknownCommandCallback = unknownCommandCallback;
    }

    /**
     * Gets the {@link ConsoleSender} (which is used as a {@link CommandSender}).
     *
     * @return the {@link ConsoleSender}
     */
    @NotNull
    public ConsoleSender getConsoleSender() {
        return consoleSender;
    }

    /**
     * Starts the thread responsible for executing commands from the console.
     */
    public void startConsoleThread() {
        Thread consoleThread = new Thread(() -> {
            BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
            while (running) {

                try {

                    if (bi.ready()) {
                        final String command = bi.readLine();
                        execute(consoleSender, command);
                    }
                } catch (IOException e) {
                    Tachyon.getServer().getExceptionManager().handleException(e);
                    continue;
                }

                // Prevent permanent looping
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Tachyon.getServer().getExceptionManager().handleException(e);
                }

            }
            try {
                bi.close();
            } catch (IOException e) {
                Tachyon.getServer().getExceptionManager().handleException(e);
            }
        }, "ConsoleCommand-Thread");
        consoleThread.setDaemon(true);
        consoleThread.start();
    }
}
