package fr.arinonia.bot.impl.listener;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JDAListenerImpl {

    private final List<ListenerAdapter> listeners = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(JDAListenerImpl.class);

    public void registerJDAListener(final ListenerAdapter listenerAdapter) {
        logger.info("Registering JDA Listener: {}", listenerAdapter.getClass().getSimpleName());
        this.listeners.add(listenerAdapter);
    }

    public void unRegisterJDAListener(final ListenerAdapter listenerAdapter) {
        logger.info("Unregistering JDA Listener: {}", listenerAdapter.getClass().getSimpleName());
        this.listeners.remove(listenerAdapter);
    }

    public List<ListenerAdapter> getListeners() {
        return this.listeners;
    }
}
