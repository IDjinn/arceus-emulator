package habbo.rooms.enums;

public enum RoomAccessState {
    OPEN(0),
    LOCKED(1),
    PASSWORD(2),
    INVISIBLE(3);

    private final int state;

    RoomAccessState(int state) {
        this.state = state;
    }

    public static RoomAccessState fromValue(String state) {
        return switch (state) {
            case "open" -> OPEN;
            case "locked" -> LOCKED;
            case "password" -> PASSWORD;
            case "invisible" -> INVISIBLE;
            default -> throw new IllegalArgumentException("State cannot be null");
        };
    }

    public int getState() {
        return this.state;
    }

}
