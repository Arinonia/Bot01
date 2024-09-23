package fr.arinonia.bot.commands;

import fr.arinonia.bot.utils.Constants;

public class AuditCommand extends AbstractFetchCommand {

    public AuditCommand() {
        super("audit", "Lazy? Don't start any browser and get the audit here bud ;)");
    }

    @Override
    protected String getUrlPath(final String type, final String name) {
        return Constants.ROOT_SUBJECTS_URL + type + "/" + name + "/audit/README.md";
    }
}
