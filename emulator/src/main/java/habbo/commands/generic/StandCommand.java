package habbo.commands.generic;

import habbo.commands.ICommand;
import habbo.commands.ICommandContext;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StandCommand implements ICommand {
    private static final LocalizedString name = LocalizedString.of("command.generic.stand.name");
    private static final LocalizedString[] alias = new LocalizedString[]{};
    private static final LocalizedString description = LocalizedString.of("command.generic.stand.description");

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
    public Optional<ICommandContext> execute(final ICommandContext ctx) {
        return Optional.empty();
    }
}
