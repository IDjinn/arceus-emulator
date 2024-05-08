package habbo.commands.arguments;

import java.util.List;

public interface IChoiceArguments extends ICommandArgument {
    List<ICommandArgument> getChoices();
}
