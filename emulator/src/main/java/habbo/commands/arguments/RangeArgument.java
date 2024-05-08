package habbo.commands.arguments;

import habbo.commands.ICommandContext;

import java.util.Optional;
import java.util.function.Function;

public final class RangeArgument implements ICustomArgument {
    private final String key;
    private final ArgumentType type;
    private final int start;
    private final int end;

    public RangeArgument(final String key, final ArgumentType type, final int start, final int end) {
        this.key = key;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public static RangeArgument of(final String key, final ArgumentType argumentType, final int minValue,
                                   final int maxValue) {
        return new RangeArgument(key, argumentType, minValue, maxValue);
    }

    @Override
    public Function<ICommandContext, Optional<Object>> getHandler() {
        switch (type) {
            case Integer:
                return ctx -> ctx.popInt().flatMap(
                        value -> value > this.start && value < this.end
                                ? Optional.of(value)
                                : Optional.empty()
                );

            case Double:
                return ctx -> ctx.popDouble().flatMap(
                        value -> value > this.start && value < this.end
                                ? Optional.of(value)
                                : Optional.empty()
                );

            case String:
                return ctx -> ctx.popArg().flatMap(
                        value -> value.length() > this.start && value.length() < this.end
                                ? Optional.of(value)
                                : Optional.empty()
                );

            default:
                return ctx -> Optional.empty();
        }
    }
}
