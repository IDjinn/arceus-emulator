package habbo.messenger;

public enum RelationshipType {
    None(0),
    Hearth(1),
    Smile(2),
    Bobba(3),

    ;
    private final int type;

    RelationshipType(final int type) {
        this.type = type;
    }

    public static RelationshipType fromType(int type) {
        return switch (type) {
            default -> None;
            case 1 -> Hearth;
            case 2 -> Smile;
            case 3 -> Bobba;
        };
    }

    public int getType() {
        return type;
    }
}
