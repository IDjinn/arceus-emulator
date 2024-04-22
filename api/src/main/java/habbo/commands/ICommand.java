package habbo.commands;

import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

public interface ICommand {
    @NotNull LocalizedString getName();

    @NotNull LocalizedString[] getAlias();

    @NotNull LocalizedString getDescription();

    Object[] getParameters();


    void execute(final ICommandContext ctx);
}
