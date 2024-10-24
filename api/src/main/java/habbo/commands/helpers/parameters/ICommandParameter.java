package habbo.commands.helpers.parameters;

import networking.packets.IPacketSerializer;
import networking.packets.IPacketWriter;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final IPacketWriter writer);
}
