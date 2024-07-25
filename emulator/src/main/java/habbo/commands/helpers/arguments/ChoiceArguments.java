package habbo.commands.helpers.arguments;

import habbo.commands.helpers.parameters.CommandParameterType;
import habbo.commands.helpers.parameters.ICommandParameter;
import habbo.internationalization.LocalizedString;
import networking.packets.outgoing.IOutgoingDTOSerializer;

import java.util.List;

public record ChoiceArguments<T>(String key, ArgumentType argumentType,
                                 List<T> value) implements ICommandArgument, ICommandParameter {

    public static <T> ChoiceArguments<T> of(final String key, final ArgumentType argumentType, final List<T> value) {
        return new ChoiceArguments<T>(key, argumentType, value);
    }

    @Override
    public void serializeArgument(final IOutgoingDTOSerializer<U> packet) {
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
    public void serializeParameter(final IOutgoingDTOSerializer<U> packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.serializeArgument(packet);
    }
}
