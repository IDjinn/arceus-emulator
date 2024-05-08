package habbo.commands.arguments;

import java.util.List;

public final class ChoiceArgumentsValues<T> implements ICommandArgument {
    private final String key;
    private final ArgumentType type;
    private final List<T> value;

    public ChoiceArgumentsValues(final String key, final ArgumentType type, final List<T> value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public static <T> ChoiceArgumentsValues<T> of(final String key, final ArgumentType type, final List<T> value) {
        return new ChoiceArgumentsValues<T>(key, type, value);
    }

    public ArgumentType getType() {
        return this.type;
    }

    public List<T> getValue() {
        return this.value;
    }

    public String getKey() {
        return key;
    }
}
