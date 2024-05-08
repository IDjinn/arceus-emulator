package habbo.commands.arguments;

import networking.packets.OutgoingPacket;

public interface ICommandArgument {
    String getKey();

    ArgumentType getArgumentType();

    void serializeArgument(final OutgoingPacket packet);
}
