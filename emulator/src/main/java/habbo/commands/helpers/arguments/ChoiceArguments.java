package habbo.commands.helpers.arguments;

import habbo.commands.helpers.parameters.CommandParameterType;
import habbo.commands.helpers.parameters.ICommandParameter;
import habbo.internationalization.LocalizedString;
import networking.packets.IPacketSerializer;
import networking.packets.IPacketWriter;

import java.util.List;

public record ChoiceArguments<T>(String key, ArgumentType argumentType,
                                 List<T> value) implements ICommandArgument, ICommandParameter {

    public static <T> ChoiceArguments<T> of(final String key, final ArgumentType argumentType, final List<T> value) {
        return new ChoiceArguments<T>(key, argumentType, value);
    }

    @Override
    public void serializeArgument(final IPacketWriter writer) {
        writer.appendInt(this.value.size());
        for (final var value : this.value) {
            writer.appendInt(this.argumentType.getCode());
            if (value instanceof LocalizedString localizedString)
                writer.appendString(localizedString.toString());

            writer.appendString("");
        }
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.Choices;
    }

    @Override
    public void serializeParameter(IPacketWriter writer) {
        writer.appendInt(this.getParameterType().getCode());
        this.serializeArgument(writer);
    }
}
