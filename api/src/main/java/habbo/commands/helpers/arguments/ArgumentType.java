package habbo.commands.helpers.arguments;

public enum ArgumentType {
    Unknown(-1),

    SubCommand(1),
    Boolean(2),
    Integer(3),
    Double(4),
    String(5),

    TargetHabbo(6),
    TargetEntity(7),
    TargetRoom(8),
    TargetItem(9),

    Custom(10),
    ;

    private final int code;

    ArgumentType(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static ArgumentType fromType(int type) {
        return switch (type) {
            case 1 -> SubCommand;
            case 2 -> Boolean;
            case 3 -> Integer;
            case 4 -> Double;
            case 5 -> String;
            case 7 -> TargetHabbo;
            case 8 -> TargetEntity;
            case 9 -> TargetRoom;
            default -> Unknown;
        };
    }
}
