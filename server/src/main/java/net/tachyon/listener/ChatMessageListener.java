package net.tachyon.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.command.CommandManager;
import net.tachyon.command.TachyonCommandManager;
import net.tachyon.entity.Player;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerChatEvent;
import net.tachyon.network.ConnectionManager;
import net.tachyon.network.packet.client.play.ClientChatMessagePacket;
import net.tachyon.network.packet.server.play.ChatMessagePacket;
import net.tachyon.utils.PacketUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.function.Function;

public class ChatMessageListener {

    private static final CommandManager COMMAND_MANAGER = Tachyon.getServer().getCommandManager();
    private static final ConnectionManager CONNECTION_MANAGER = (ConnectionManager) Tachyon.getServer().getConnectionManager();

    public static void listener(ClientChatMessagePacket packet, TachyonPlayer player) {
        String message = packet.message;

        final String cmdPrefix = TachyonCommandManager.COMMAND_PREFIX;
        if (message.startsWith(cmdPrefix)) {
            // The message is a command
            message = message.replaceFirst(cmdPrefix, "");

            COMMAND_MANAGER.execute(player, message);

            // Do not call chat event
            return;
        }

        final Collection<Player> players = CONNECTION_MANAGER.getOnlinePlayers();
        PlayerChatEvent playerChatEvent = new PlayerChatEvent(player, players, message);

        // Call the event
        player.callCancellableEvent(PlayerChatEvent.class, playerChatEvent, () -> {

            final Function<PlayerChatEvent, Component> formatFunction = playerChatEvent.getChatFormatFunction();

            Component textObject;

            if (formatFunction != null) {
                // Custom format
                textObject = formatFunction.apply(playerChatEvent);
            } else {
                // Default format
                textObject = buildDefaultChatMessage(playerChatEvent);
            }

            final Collection<Player> recipients = playerChatEvent.getRecipients();
            if (!recipients.isEmpty()) {
                // Send the message with the correct player UUID
                ChatMessagePacket chatMessagePacket = new ChatMessagePacket(textObject, ChatMessagePacket.Position.CHAT);

                PacketUtils.sendGroupedPacket(recipients, chatMessagePacket);
            }
        });

    }

    private static Component buildDefaultChatMessage(PlayerChatEvent chatEvent) {
        final String username = chatEvent.getPlayer().getUsername();

        final Component usernameText = Component.text(String.format("<%s>", username));

        return usernameText
                .hoverEvent(HoverEvent.showText(Component.text("Click to send a message to " + username)))
                .clickEvent(ClickEvent.suggestCommand("/msg " + username + StringUtils.SPACE))
                .append(Component.text(StringUtils.SPACE + chatEvent.getMessage()));
    }

}
