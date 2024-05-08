package habbo.commands.arguments;

public final class RequiredArgument implements IRequiredArgument {
    private final ArgumentType type;

    public RequiredArgument(final ArgumentType type) {
        this.type = type;
    }

    public static RequiredArgument of(final ArgumentType type) {
        return new RequiredArgument(type);
    }

    @Override
    public ArgumentType getArgumentType() {
        return this.type;
    }
}
