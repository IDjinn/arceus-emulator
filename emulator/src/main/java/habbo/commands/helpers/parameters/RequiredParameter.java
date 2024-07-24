package habbo.commands.helpers.parameters;

import networking.packets.IOutgoingPacket;

public class RequiredParameter implements ICommandParameter {
    private final ICommandParameter parameter;

    public RequiredParameter(final ICommandParameter parameter) {
        this.parameter = parameter;
    }

    public static RequiredParameter of(final ICommandParameter parameter) {
        return new RequiredParameter(parameter);
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.Required;
    }

    @Override
    public void serializeParameter(final IOutgoingPacket<U> packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.parameter.serializeParameter(packet);
    }
}
