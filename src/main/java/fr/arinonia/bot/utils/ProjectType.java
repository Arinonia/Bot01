package fr.arinonia.bot.utils;

public enum ProjectType {
    GO(""),
    JS(""),
    RUST(""),
    JAVA("java"),
    FREE(""),
    OTHER("");

    private final String type;

    ProjectType(final String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
