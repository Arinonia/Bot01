package fr.arinonia.bot.listeners;

import fr.arinonia.bot.Bot;
import fr.arinonia.bot.impl.command.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashCommandListener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashCommandListener.class);
    private final Bot bot;

    public SlashCommandListener(final Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull final SlashCommandInteractionEvent event) {
        final String name = event.getName();
        final AbstractCommand command = this.bot.getCommandRegistry().getCommand(name);
        if (command != null) {
            command.onCommand(event);
        } else {
            LOGGER.warn("No command found for name {}", name);
        }
    }

}
