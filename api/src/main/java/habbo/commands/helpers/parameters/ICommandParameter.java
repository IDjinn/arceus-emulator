package habbo.commands.helpers.parameters;

import networking.packets.IPacketSerializer;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final IPacketSerializer<?> serializer);
}
