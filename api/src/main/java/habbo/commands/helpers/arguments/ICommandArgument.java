package habbo.commands.helpers.arguments;

import networking.packets.IOutgoingPacket;

public interface ICommandArgument {
    String key();

    ArgumentType argumentType();

    void serializeArgument(final IOutgoingPacket<U> packet);
}
