package habbo.commands;

import habbo.commands.arguments.ICommandArgument;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ICommand {
    @NotNull LocalizedString getName();

    @NotNull LocalizedString[] getAlias();

    @NotNull LocalizedString getDescription();

    default List<ICommandArgument> getParameters() {
        return List.of();
    }

    default Optional<LocalizedString> validate(ICommandContext ctx) {
        return Optional.empty();
    }

    void execute(final ICommandContext ctx);
}
