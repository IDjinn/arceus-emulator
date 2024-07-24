package habbo.commands;

import habbo.commands.helpers.parameters.ICommandParameter;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ICommand {
    @NotNull LocalizedString getName();

    @NotNull LocalizedString[] getAlias();

    @NotNull LocalizedString getDescription();

    default List<ICommandParameter> getParameters() {
        return List.of();
    }

    default Optional<ICommandContext> validate(ICommandContext ctx) {
        return Optional.of(ctx);
    }

    default Optional<ICommandContext> execute(final ICommandContext ctx) {
        return Optional.of(ctx);
    }
}
