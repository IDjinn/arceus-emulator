package habbo.commands.helpers.arguments;

import habbo.commands.helpers.parameters.CommandParameterType;
import habbo.commands.helpers.parameters.ICommandParameter;
import networking.packets.OutgoingPacket;

public record RequiredArgument(String key, ArgumentType argumentType) implements ICommandParameter, ICommandArgument {

    public static RequiredArgument of(final String key, final ArgumentType argumentType) {
        return new RequiredArgument(key, argumentType);
    }

    @Override
    public CommandParameterType getParameterType() {
        return CommandParameterType.Required;
    }

    @Override
    public void serializeParameter(final OutgoingPacket packet) {
        packet.appendInt(this.getParameterType().getCode());
        this.serializeArgument(packet);
    }

    @Override
    public void serializeArgument(final OutgoingPacket packet) {
        packet.appendString(this.key);
        packet.appendInt(this.argumentType.getCode());
    }
}
