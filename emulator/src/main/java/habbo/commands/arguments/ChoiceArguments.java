package habbo.commands.arguments;

import habbo.commands.parameters.CommandParameterType;
import habbo.commands.parameters.ICommandParameter;
import habbo.internationalization.LocalizedString;
import networking.packets.OutgoingPacket;

import java.util.List;

public final class ChoiceArguments<T> implements ICommandArgument, ICommandParameter {
    private final String key;
    private final ArgumentType argumentType;
    private final List<T> value;

    public ChoiceArguments(final String key, final ArgumentType argumentType, final List<T> value) {
        this.key = key;
        this.argumentType = argumentType;
        this.value = value;
    }

    public static <T> ChoiceArguments<T> of(final String key, final ArgumentType argumentType, final List<T> value) {
        return new ChoiceArguments<T>(key, argumentType, value);
    }

    public String getKey() {
        return this.key;
    }

    public List<T> getValue() {
        return this.value;
    }

    public ArgumentType getArgumentType() {
        return this.argumentType;
    }

    @Override
    public void serializeArgument(final OutgoingPacket packet) {
        packet.appendInt(this.value.size());
        for (final var value : this.value) {
            packet.appendInt(this.argumentType.getCode());
            if (value instanceof LocalizedString localizedString)
                packet.appendString(localizedString.toString());

            packet.appendString("");
        }
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.Choices;
    }

    @Override
    public void serializeParameter(final OutgoingPacket packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.serializeArgument(packet);
    }
}
