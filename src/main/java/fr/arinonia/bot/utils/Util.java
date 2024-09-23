package fr.arinonia.bot.utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Util {

    public static void sendLongFormattedMessage(final SlashCommandInteractionEvent event, final String text) {
        final int maxMessageLength = 2000;
        final StringBuilder messagePart = new StringBuilder();
        boolean inCodeBlock = false;

        for (final String line : text.split("\n")) {
            if (line.startsWith("```")) {
                inCodeBlock = !inCodeBlock;
            }

            if (messagePart.length() + line.length() + 1 > maxMessageLength) {
                if (inCodeBlock) {
                    messagePart.append("```");
                    event.getHook().sendMessage(messagePart.toString()).queue();
                    messagePart.setLength(0);
                    messagePart.append("```");
                } else {
                    event.getHook().sendMessage(messagePart.toString()).queue();
                    messagePart.setLength(0);
                }
            }
            messagePart.append(line).append("\n");
        }

        if (!messagePart.isEmpty()) {
            if (inCodeBlock) {
                messagePart.append("```");
            }
            event.getHook().sendMessage(messagePart.toString()).queue();
        }
    }
}
