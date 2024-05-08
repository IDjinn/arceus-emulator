package habbo.commands.parameters;

public enum CommandParameterType {
    Required(0),
    Optional(1),
    MultipleParameters(2),
    Range(3),
    Choices(4),
    ;

    private final int code;

    CommandParameterType(final int code) {
        this.code = code;
    }

    public static CommandParameterType fromType(int type) {
        return switch (type) {
            default -> Required;
            case 1 -> Optional;
            case 2 -> MultipleParameters;
            case 3 -> Range;
        };
    }

    public int getCode() {
        return code;
    }
}
