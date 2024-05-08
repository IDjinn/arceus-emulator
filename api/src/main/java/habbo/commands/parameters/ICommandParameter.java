package habbo.commands.parameters;

import networking.packets.OutgoingPacket;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final OutgoingPacket packet);
}
