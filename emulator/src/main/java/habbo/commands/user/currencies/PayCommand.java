package habbo.commands.user.currencies;

import com.google.inject.Inject;
import habbo.commands.ICommand;
import habbo.commands.ICommandContext;
import habbo.commands.ICommandManager;
import habbo.commands.arguments.ArgumentType;
import habbo.commands.arguments.ChoiceArguments;
import habbo.commands.arguments.RangeArgument;
import habbo.commands.arguments.RequiredArgument;
import habbo.commands.parameters.ICommandParameter;
import habbo.commands.parameters.MultipleParameters;
import habbo.internationalization.IInternationalizationManager;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.rooms.entities.chat.CommandListComposer;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class PayCommand implements ICommand {
    private static final List<ICommandParameter> parameters = List.of(
            RequiredArgument.of("target", ArgumentType.TargetHabbo),
            MultipleParameters.of(
                    RangeArgument.of("value", ArgumentType.Integer, 0, Integer.MAX_VALUE),
                    ChoiceArguments.of("currency-name", ArgumentType.String, List.of(
                            LocalizedString.of("command.user.currencies.pay.currency.test"),
                            LocalizedString.of("command.user.currencies.pay.currency.other-test2")
                    ))
            )
    );
    @Inject
    private ICommandManager commandManager;
    
    private static final LocalizedString name = LocalizedString.of("command.user.currencies.pay.name");
    private static final LocalizedString description = LocalizedString.of("command.user.currencies.pay.description");
    private static final LocalizedString[] alias = new LocalizedString[]{};
    @Inject
    private IInternationalizationManager internationalizationManager;

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
    public List<ICommandParameter> getParameters() {
        return parameters;
    }

    @Override
    public Optional<LocalizedString> validate(final ICommandContext ctx) {
        return ICommand.super.validate(ctx);
    }

    @Override
    public void execute(final ICommandContext ctx) {
        ctx.getPlayer().getClient().sendMessage(new CommandListComposer(
                this.commandManager.getCommands().values().stream().toList(),
                this.internationalizationManager,
                Locale.ENGLISH
        ));
        ctx.whisper(LocalizedString.of("none", "hey"));
    }
}