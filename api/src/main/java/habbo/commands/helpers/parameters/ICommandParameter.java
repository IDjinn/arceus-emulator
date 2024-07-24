package habbo.commands.helpers.parameters;

import networking.packets.IOutgoingPacket;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final IOutgoingPacket<U> packet);
}
