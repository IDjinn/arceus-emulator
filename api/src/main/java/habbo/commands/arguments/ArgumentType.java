package habbo.commands.arguments;

public enum ArgumentType {
    Unknown(-1),

    SubCommand(1),
    Boolean(2),
    Integer(3),
    Double(4),
    String(5),
    Text(6),

    TargetHabbo(7),
    TargetEntity(8),
    TargetRoom(9),
    ;

    private final int type;

    ArgumentType(final int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public static ArgumentType fromType(int type) {
        return switch (type) {
            case 1 -> SubCommand;
            case 2 -> Boolean;
            case 3 -> Integer;
            case 4 -> Double;
            case 5 -> String;
            case 6 -> Text;
            case 7 -> TargetHabbo;
            case 8 -> TargetEntity;
            case 9 -> TargetRoom;
            default -> Unknown;
        };
    }
}
