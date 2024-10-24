package habbo.commands.helpers.arguments;

import networking.packets.IPacketSerializer;
import networking.packets.IPacketWriter;

public interface ICommandArgument {
    String key();

    ArgumentType argumentType();

    void serializeArgument(final IPacketWriter writer);
}
