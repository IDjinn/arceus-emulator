package habbo.commands.helpers.arguments;

import habbo.commands.ICommandContext;
import habbo.commands.helpers.parameters.CommandParameterType;
import habbo.commands.helpers.parameters.ICommandParameter;
import networking.packets.IOutgoingPacket;

import java.util.Optional;
import java.util.function.Function;

public final class RangeArgument implements ICustomArgument, ICommandParameter {
    private final String key;
    private final ArgumentType argumentType;
    private final int start;
    private final int end;

    public RangeArgument(final String key, final ArgumentType argumentType, final int start, final int end) {
        this.key = key;
        this.argumentType = argumentType;
        this.start = start;
        this.end = end;
    }

    public static RangeArgument of(final String key, final ArgumentType argumentType, final int minValue,
                                   final int maxValue) {
        return new RangeArgument(key, argumentType, minValue, maxValue);
    }

    @Override
    public Function<ICommandContext, Optional<Object>> getHandler() {
        return switch (this.argumentType) {
            case Integer -> ctx -> ctx.popInt().flatMap(
                    value -> value > this.start && value < this.end
                            ? Optional.of(value)
                            : Optional.empty()
            );
            case Double -> ctx -> ctx.popDouble().flatMap(
                    value -> value > this.start && value < this.end
                            ? Optional.of(value)
                            : Optional.empty()
            );
            case String -> ctx -> ctx.popArg().flatMap(
                    value -> value.length() > this.start && value.length() < this.end
                            ? Optional.of(value)
                            : Optional.empty()
            );
            default -> ctx -> Optional.empty();
        };
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public ArgumentType argumentType() {
        return this.argumentType;
    }

    @Override
    public void serializeArgument(final IOutgoingPacket<U> packet) {
        packet.appendString(this.key);
        packet.appendInt(this.argumentType.getCode());
        packet.appendInt(this.end);
        packet.appendInt(this.start);
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.Range;
    }

    @Override
    public void serializeParameter(final IOutgoingPacket<U> packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.serializeArgument(packet);
    }
}
