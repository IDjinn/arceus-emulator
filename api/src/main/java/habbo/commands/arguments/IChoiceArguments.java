package habbo.commands.arguments;

public interface IChoiceArguments extends ICommandArgument {
    ArgumentType[] getChoices();
}
