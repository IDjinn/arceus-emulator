package habbo.commands.generic;

import habbo.commands.ICommand;
import habbo.commands.ICommandContext;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

public class AboutCommand implements ICommand {
    private static final LocalizedString name = LocalizedString.of("command.about.name");
    private static final LocalizedString[] alias = new LocalizedString[]{};
    private static final LocalizedString description = LocalizedString.of("command.about.description");
    private static final LocalizedString response = LocalizedString.of("command.about.response", "hello world!");

    @Override
    public void execute(final ICommandContext ctx) {
        ctx.whisper(response);
    }

    @Override
    public @NotNull LocalizedString getName() {
        return name;
    }

    @Override
    public @NotNull LocalizedString[] getAlias() {
        return alias;
    }

    @Override
    public @NotNull LocalizedString getDescription() {
        return description;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
