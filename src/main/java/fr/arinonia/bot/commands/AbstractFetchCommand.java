package fr.arinonia.bot.commands;

import fr.arinonia.bot.impl.command.AbstractCommand;
import fr.arinonia.bot.utils.FormatMarkdown;
import fr.arinonia.bot.utils.ProjectType;
import fr.arinonia.bot.utils.Util;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public abstract class AbstractFetchCommand extends AbstractCommand {

    public AbstractFetchCommand(final String name, final String description) {
        super(name, description);
        this.addOption(new OptionData(OptionType.STRING, "name", "name", true, false)
                .setRequired(true));
        this.addOption(new OptionData(OptionType.STRING, "type", "type", false, false)
                .addChoice("Go", "GO")
                .addChoice("JavaScript", "JS")
                .addChoice("Rust", "RUST")
                .addChoice("Java", "JAVA")
        );
    }

    protected abstract String getUrlPath(final String type, final String name);

    @Override
    public void onCommand(final SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        ProjectType type;
        if (event.getOption("type") == null) {
            type = ProjectType.OTHER;
        } else {
            type = ProjectType.valueOf(event.getOption("type").getAsString().toUpperCase());
        }
        final String name = event.getOption("name").getAsString();

        final String url = getUrlPath(type.getType(), name);

        try {
            final Document doc = Jsoup.connect(url).get();
            final Element element = doc.getElementsByClass("file-view markup markdown").first();

            if (element == null) {
                event.getHook().sendMessage("Cannot parse class 'file-view markup markdown' in page '" + url + "'.").queue();
                return;
            }
            final String text = FormatMarkdown.formatToMarkdown(element);

            Util.sendLongFormattedMessage(event, text);
        } catch (final IOException e) {
            event.getHook().sendMessage(e.getMessage()).queue();
        }
    }
}
