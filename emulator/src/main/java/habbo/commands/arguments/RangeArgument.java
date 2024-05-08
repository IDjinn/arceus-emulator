package habbo.commands.arguments;

import habbo.commands.ICommandContext;
import habbo.commands.parameters.CommandParameterType;
import habbo.commands.parameters.ICommandParameter;
import networking.packets.OutgoingPacket;

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
        switch (argumentType) {
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

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public ArgumentType getArgumentType() {
        return this.argumentType;
    }

    @Override
    public void serializeArgument(final OutgoingPacket packet) {
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
    public void serializeParameter(final OutgoingPacket packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.serializeArgument(packet);
    }
}
