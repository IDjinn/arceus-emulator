package habbo.commands.arguments;

import habbo.commands.ICommandContext;

import java.util.function.Function;

public final class CommandArgument implements ICommandArgument {
    private final ArgumentType type;
    private final Function<ICommandContext, Object> valueHandler;

    public CommandArgument(final ArgumentType type, final Function<ICommandContext, Object> valueHandler) {
        this.type = type;
        this.valueHandler = valueHandler;
    }

    public static ICommandArgument of(final ArgumentType argumentType, final Function<ICommandContext, Object> valueHandler) {
        return new CommandArgument(argumentType, valueHandler);
    }

    public ArgumentType getType() {
        return type;
    }

    public Function<ICommandContext, Object> getValueHandler() {
        return valueHandler;
    }
}
