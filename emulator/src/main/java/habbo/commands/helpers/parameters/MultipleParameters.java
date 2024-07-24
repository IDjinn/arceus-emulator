package habbo.commands.helpers.parameters;

import networking.packets.OutgoingPacket;

import java.util.Arrays;
import java.util.List;

public record MultipleParameters(List<ICommandParameter> choices) implements ICommandParameter {

    public static MultipleParameters of(ICommandParameter... arguments) {
        return new MultipleParameters(Arrays.stream(arguments).toList());
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.MultipleParameters;
    }

    @Override
    public void serializeParameter(final OutgoingPacket<U> packet) {
        packet.appendInt(this.getParameterType().getCode());
        packet.appendInt(this.choices.size());
        for (final var parameter : this.choices) {
            parameter.serializeParameter(packet);
        }
    }
}
