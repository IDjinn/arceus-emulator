package habbo.commands.helpers.arguments;

import habbo.commands.helpers.parameters.CommandParameterType;
import habbo.commands.helpers.parameters.ICommandParameter;
import networking.packets.OutgoingPacket;

public final class RequiredArgument implements ICommandParameter, ICommandArgument {
    private final String key;
    private final ArgumentType argumentType;

    public RequiredArgument(final String key, final ArgumentType argumentType) {
        this.key = key;
        this.argumentType = argumentType;
    }

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
    public String getKey() {
        return this.key;
    }

    @Override
    public ArgumentType getArgumentType() {
        return this.argumentType;
    }

    @Override
    public void serializeArgument(final OutgoingPacket packet) {
        packet.appendString(this.key);
        packet.appendInt(this.argumentType.getCode());
    }
}
