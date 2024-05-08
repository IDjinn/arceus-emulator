package habbo.commands.parameters;

import networking.packets.OutgoingPacket;

import java.util.Arrays;
import java.util.List;

public final class MultipleParameters implements ICommandParameter {
    private final List<ICommandParameter> choices;

    public MultipleParameters(final List<ICommandParameter> choices) {
        this.choices = choices;
    }

    public static MultipleParameters of(ICommandParameter... arguments) {
        return new MultipleParameters(Arrays.stream(arguments).toList());
    }

    public List<ICommandParameter> getChoices() {
        return this.choices;
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.MultipleParameters;
    }

    @Override
    public void serializeParameter(final OutgoingPacket packet) {
        packet.appendInt(this.getParameterType().getCode());
        packet.appendInt(this.choices.size());
        for (final var parameter : this.choices) {
            parameter.serializeParameter(packet);
        }
    }
}
