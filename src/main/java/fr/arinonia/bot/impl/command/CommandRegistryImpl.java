package fr.arinonia.bot.impl.command;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CommandRegistryImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRegistryImpl.class);
    private final List<AbstractCommand> commands = new ArrayList<>();

    public void registerCommand(final AbstractCommand command) {
        LOGGER.info("Registering command {}", command.getClass().getSimpleName());
        this.commands.add(command);
    }

    public void unRegisterCommand(final AbstractCommand command) {
        LOGGER.info("Unregistering command {}", command.getClass().getSimpleName());
        this.commands.remove(command);
    }

    public List<AbstractCommand> getCommands() {
        return this.commands;
    }

    @Nullable
    public AbstractCommand getCommand(final String name) {
        for (final AbstractCommand command : this.commands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }
}
