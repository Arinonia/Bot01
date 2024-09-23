package fr.arinonia.bot.config;

public class Config {

    private final String token;

    public Config(final String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
