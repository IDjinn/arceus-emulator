package habbo.commands.helpers.arguments;

public interface ICommandArgument {
    String key();

    ArgumentType argumentType();

    void serializeArgument(final IOutgoingDTOSerializer<U> packet);
}
