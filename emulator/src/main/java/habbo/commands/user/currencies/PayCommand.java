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
import habbo.commands.parameters.OptionalParameter;
import habbo.internationalization.IInternationalizationManager;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PayCommand implements ICommand {
    private static final List<ICommandParameter> parameters = List.of(
            RequiredArgument.of("target", ArgumentType.TargetHabbo),
            OptionalParameter.of(ChoiceArguments.of("currency-name", ArgumentType.String, List.of(
                    LocalizedString.of("command.user.currencies.pay.currency.test"),
                    LocalizedString.of("command.user.currencies.pay.currency.other-test2")
            ))),
            RangeArgument.of("value", ArgumentType.Integer, 0, Integer.MAX_VALUE)
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
    public Optional<ICommandContext> validate(final ICommandContext ctx) {
        return ICommand.super.validate(ctx);
    }

    //
//        ctx.getPlayerEntity().getClient().sendMessage(new CommandListComposer(
//            this.commandManager.getCommands().values().stream().toList(),
//                this.internationalizationManager,
//    Locale.ENGLISH
//        ));
    @Override
    public Optional<ICommandContext> execute(final ICommandContext ctx) {
        final var target = ctx.popPlayerEntity("target");
        if (target.isEmpty())
            return ctx.error(LocalizedString.of("command.user.currencies.pay.error.target-not-found"));

        if (target.get().equals(ctx.getPlayerEntity()))
            return ctx.error(LocalizedString.of("command.user.currencies.pay.error.target-is-self"));

        final Optional<String> currency = ctx.optional("currency-name", ArgumentType.String, ChoiceArguments.class);
        if (currency.isEmpty())
            return ctx.error(LocalizedString.of("command.user.currencies.pay.error.missing-value"));

        final var value = ctx.required("value", ArgumentType.Integer);
        if (value.isEmpty())
            return ctx.error(LocalizedString.of("command.user.currencies.pay.error.missing-value"));

        return ctx.whisper(LocalizedString.of("none", "hey"));
    }
}