package habbo.commands.generic;

import com.google.inject.Inject;
import habbo.commands.ICommand;
import habbo.commands.ICommandContext;
import habbo.habbos.IHabboManager;
import habbo.internationalization.LocalizedString;
import habbo.variables.Variable;
import org.jetbrains.annotations.NotNull;

public class AboutCommand implements ICommand {
    @Inject
    private IHabboManager habboManager;
    
    private static final LocalizedString name = LocalizedString.of("command.about.name");
    private static final LocalizedString[] alias = new LocalizedString[]{};
    private static final LocalizedString description = LocalizedString.of("command.about.description");

    @Override
    public void execute(final ICommandContext ctx) {
        ctx.whisper(LocalizedString.of("command.about.response",
                new Variable("hotel.users.count", String.valueOf(this.habboManager.onlineUsersCount())))
        );
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
}
