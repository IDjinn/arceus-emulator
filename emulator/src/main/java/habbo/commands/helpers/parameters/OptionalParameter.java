package habbo.commands.helpers.parameters;

import networking.packets.OutgoingPacket;

public class OptionalParameter implements ICommandParameter {
    private final ICommandParameter parameter;

    public OptionalParameter(final ICommandParameter parameter) {
        this.parameter = parameter;
    }

    public static OptionalParameter of(final ICommandParameter parameter) {
        return new OptionalParameter(parameter);
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.Optional;
    }

    @Override
    public void serializeParameter(final OutgoingPacket packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.parameter.serializeParameter(packet);
    }
}
