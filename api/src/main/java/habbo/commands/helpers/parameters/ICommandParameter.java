package habbo.commands.helpers.parameters;

import networking.packets.OutgoingPacket;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final OutgoingPacket<U> packet);
}
