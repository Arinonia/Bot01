package fr.arinonia.bot.impl.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand {

    private final String name;
    private final String description;
    private final List<OptionData> options;

    public AbstractCommand(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.options = new ArrayList<>();
    }

    public AbstractCommand(final String name) {
        this(name, "01Bot-API");
    }

    public abstract void onCommand(final SlashCommandInteractionEvent var1);

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void addOption(final OptionData option) {
        this.options.add(option);
    }

    public List<OptionData> getOptions() {
        return this.options;
    }
}
