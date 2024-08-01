package habbo.commands.helpers.arguments;

import networking.packets.IPacketSerializer;

public interface ICommandArgument {
    String key();

    ArgumentType argumentType();

    void serializeArgument(final IPacketSerializer<?> serializer);
}
