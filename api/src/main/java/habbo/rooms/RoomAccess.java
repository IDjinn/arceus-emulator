package habbo.rooms;

public enum RoomAccess {
    Open(0),
    Doorbell(1),
    Locked(2),
    Invisible(3),

    ;

    private final int state;

    RoomAccess(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
