package habbo.commands.helpers.parameters;

public interface ICommandParameter {
    CommandParameterType getParameterType();

    void serializeParameter(final IOutgoingDTOSerializer<U> packet);
}
