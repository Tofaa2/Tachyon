package net.tachyon.listener;

import net.tachyon.command.TachyonCommandManager;
import net.tachyon.command.CommandSyntax;
import net.tachyon.command.arguments.Argument;
import net.tachyon.command.parser.ArgumentQueryResult;
import net.tachyon.command.parser.CommandParser;
import net.tachyon.command.parser.CommandQueryResult;
import net.tachyon.command.suggestion.Suggestion;
import net.tachyon.command.suggestion.SuggestionCallback;
import net.tachyon.command.suggestion.SuggestionEntry;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.network.packet.client.play.ClientTabCompletePacket;
import net.tachyon.network.packet.server.play.TabCompletePacket;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class TabCompleteListener {

    public static void listener(ClientTabCompletePacket packet, TachyonPlayer player) {
        final String text = packet.text;

        String commandString = packet.text.replaceFirst(TachyonCommandManager.COMMAND_PREFIX, "");
        String[] split = commandString.split(StringUtils.SPACE);
        String commandName = split[0];

        String args = commandString.replaceFirst(commandName, "");

        final CommandQueryResult commandQueryResult = CommandParser.findCommand(commandString);
        if (commandQueryResult == null) {
            // Command not found
            return;
        }

        final ArgumentQueryResult queryResult = CommandParser.findEligibleArgument(commandQueryResult.command,
                commandQueryResult.args, commandString, text.endsWith(StringUtils.SPACE), false,
                CommandSyntax::hasSuggestion, Argument::hasSuggestion);
        if (queryResult == null) {
            // Suggestible argument not found
            return;
        }

        final Argument<?> argument = queryResult.argument;

        final SuggestionCallback suggestionCallback = argument.getSuggestionCallback();
        if (suggestionCallback != null) {
            final String input = queryResult.input;
            final int inputLength = input.length();

            final int commandLength = Arrays.stream(split).map(String::length).reduce(0, Integer::sum) +
                    StringUtils.countMatches(args, StringUtils.SPACE);
            final int trailingSpaces = !input.isEmpty() ? text.length() - text.trim().length() : 0;

            final int start = commandLength - inputLength + 1 - trailingSpaces;

            Suggestion suggestion = new Suggestion(input, start, inputLength);
            suggestionCallback.apply(player, queryResult.context, suggestion);

            TabCompletePacket tabCompletePacket = new TabCompletePacket();
            tabCompletePacket.matches = suggestion.getEntries()
                    .stream()
                    .map(SuggestionEntry::getEntry)
                    .toArray(String[]::new);

            player.getPlayerConnection().sendPacket(tabCompletePacket);
        }


    }

}