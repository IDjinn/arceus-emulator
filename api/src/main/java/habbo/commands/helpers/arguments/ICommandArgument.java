package habbo.commands.helpers.arguments;

import networking.packets.OutgoingPacket;

public interface ICommandArgument {
    String key();

    ArgumentType argumentType();

    void serializeArgument(final OutgoingPacket packet);
}
