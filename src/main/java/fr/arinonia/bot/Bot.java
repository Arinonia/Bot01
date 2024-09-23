package fr.arinonia.bot;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import fr.arinonia.bot.commands.AuditCommand;
import fr.arinonia.bot.commands.SubjectCommand;
import fr.arinonia.bot.config.Config;
import fr.arinonia.bot.impl.command.CommandRegistryImpl;
import fr.arinonia.bot.impl.listener.JDAListenerImpl;
import fr.arinonia.bot.listeners.SlashCommandListener;
import fr.arinonia.bot.utils.Constants;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

public class Bot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    private final JDAListenerImpl listenerRegistry  = new JDAListenerImpl();
    private final CommandRegistryImpl commandRegistry = new CommandRegistryImpl();
    private Config config;
    private JDA jda;

    public void preInit() {
        LOGGER.info("Starting bot initialization...");
        this.loadConfig();
        this.buildJDA();
        this.init();
        LOGGER.info("{} initialization completed. Version: {}", this.jda.getSelfUser().getName(), Constants.VERSION);
    }

    private void init() {
        System.gc();
        try {
            jda.awaitReady();
            LOGGER.info("JDA is ready and connected.");
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Thread was interrupted while waiting for JDA to become ready. The bot may not function as expected.", e);
        }

        //Tht look kinda ugly :c to much \t in here
        this.jda.getGuilds().forEach(guild -> {
            this.registerCommands(guild);
            guild.retrieveCommands().queue(commands -> {
                commands.forEach(command -> {
                    if (this.commandRegistry.getCommand(command.getName()) == null) {
                        guild.retrieveCommandById(command.getId()).queue(commandToDelete -> {
                            commandToDelete.delete().queue();
                            LOGGER.info("Deleted command: {}", command.getName());
                        });
                    }
                });
            });
        });


    }
    private void loadConfig() {
        LOGGER.info("Loading configuration...");
        try {
            final Gson gson = new Gson();
            this.config = gson.fromJson(new FileReader("config.json"), Config.class);
            LOGGER.info("Token loaded successfully.");
        } catch (final JsonIOException | JsonSyntaxException | IOException e) {
            LOGGER.error("Failed to load config.json", e);
            throw new RuntimeException("Unable to start bot without configuration");
        }
    }

    private void buildJDA() {
        LOGGER.info("Building JDA...");

        final JDABuilder builder = JDABuilder.createDefault(this.config.getToken());
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.competing("Crying on rust with u"));
        this.registerListeners();

        for (final ListenerAdapter listener : this.listenerRegistry.getListeners()) {
            builder.addEventListeners(listener);
            LOGGER.info("Added event listener: {}", listener.getClass().getSimpleName());
        }

        try {
            this.jda = builder.build();
            LOGGER.info("JDA built successfully!");
        } catch (final Exception e) {
            LOGGER.error("Failed to build JDA", e);
        }
    }

    private void registerCommands(final Guild guild) {
        LOGGER.info("Registering commands for guild: {}", guild.getName());
        this.registerCommands();
        this.commandRegistry.getCommands().forEach(cmd -> {
            final CommandCreateAction cca = guild.upsertCommand(cmd.getName(), cmd.getDescription());

            if (!cmd.getOptions().isEmpty()) {
                for (final OptionData option : cmd.getOptions()) {
                    cca.addOption(option.getType(), option.getName(), option.getDescription(), option.isRequired()).queue();
                }
            }
            cca.queue();
            LOGGER.info("Registered command: {}", cmd.getName());
        });
    }

    private void registerCommands() {
        this.commandRegistry.registerCommand(new SubjectCommand());
        this.commandRegistry.registerCommand(new AuditCommand());
    }

    private void registerListeners() {
        this.listenerRegistry.registerJDAListener(new SlashCommandListener(this));
    }

    public JDAListenerImpl getListenerRegistry() {
        return this.listenerRegistry;
    }

    public CommandRegistryImpl getCommandRegistry() {
        return this.commandRegistry;
    }

    public JDA getJda() {
        return this.jda;
    }
}
