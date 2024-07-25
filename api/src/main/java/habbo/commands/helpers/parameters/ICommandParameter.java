package habbo.commands.helpers.parameters;

import networking.packets.outgoing.IOutgoingDTOSerializer;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final IOutgoingDTOSerializer<U> packet);
}
