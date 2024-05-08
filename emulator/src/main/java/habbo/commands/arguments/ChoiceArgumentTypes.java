package habbo.commands.arguments;

import java.util.Arrays;
import java.util.List;

public final class ChoiceArgumentTypes implements IChoiceArguments {
    private final List<ICommandArgument> choices;

    public ChoiceArgumentTypes(final List<ICommandArgument> choices) {
        this.choices = choices;
    }

    public static ChoiceArgumentTypes of(ICommandArgument... arguments) {
        return new ChoiceArgumentTypes(Arrays.stream(arguments).toList());
    }

    @Override
    public List<ICommandArgument> getChoices() {
        return this.choices;
    }
}
