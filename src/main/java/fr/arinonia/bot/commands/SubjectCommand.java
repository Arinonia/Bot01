package fr.arinonia.bot.commands;

import fr.arinonia.bot.utils.Constants;

public class SubjectCommand extends AbstractFetchCommand {

    public SubjectCommand() {
        super("subject", "Lazy? Don't start any browser and get the subject here bud ;)");
    }

    @Override
    protected String getUrlPath(final String type, final String name) {
        return Constants.ROOT_SUBJECTS_URL + type + "/" + name + "/README.md";
    }
}
