package habbo.commands.helpers.arguments;

import networking.packets.outgoing.IOutgoingDTOSerializer;

public interface ICommandArgument {
    String key();

    ArgumentType argumentType();

    void serializeArgument(final IOutgoingDTOSerializer<U> packet);
}
