package habbo.commands.user.currencies;

import habbo.commands.ICommand;
import habbo.commands.ICommandContext;
import habbo.commands.arguments.*;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class PayCommand implements ICommand {
    private static final LocalizedString name = LocalizedString.of("command.user.currencies.pay.name");
    private static final LocalizedString description = LocalizedString.of("command.user.currencies.pay.description");
    private static final LocalizedString[] alias = new LocalizedString[]{};
    private static final List<ICommandArgument> parameters = List.of(
            RequiredArgument.of(ArgumentType.TargetHabbo),
            ChoiceArgumentTypes.of(
                    RangeArgument.of("value", ArgumentType.Integer, 0, Integer.MAX_VALUE),
                    ChoiceArgumentsValues.of("currency-name", ArgumentType.String, List.of(
                            LocalizedString.of("command.user.currencies.pay.currency.test"),
                            LocalizedString.of("command.user.currencies.pay.currency.other-test2")
                    ))
            )
    );

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
    public List<ICommandArgument> getParameters() {
        return parameters;
    }

    @Override
    public Optional<LocalizedString> validate(final ICommandContext ctx) {
//        ArgumentType.Integer.validate(ctx -> {
//            final var value = ctx.popArg();
//            if (value.isEmpty())
//                return ctx.error(LocalizedString.of("command.user.currencies.pay.error.no-amount"));
//
//            final var amount = Integer.parseInt(value.get());
//            if (amount <= 0)
//                return ctx.error(LocalizedString.of("command.user.currencies.pay.error.invalid-amount"));
//
//            if (amount>ctx.getPlayer().getHabbo().getData().getCredits())
//                return ctx.error(LocalizedString.of("command.user.currencies.pay.error.not-enough-credits"));
//
//            return amount;
//        })
        return ICommand.super.validate(ctx);
    }

    @Override
    public void execute(final ICommandContext ctx) {
        ctx.whisper(LocalizedString.of("none", "hey"));
    }
}