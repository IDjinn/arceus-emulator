package queries.rooms;

public enum RoomQuery {

    SELECT_ALL_ITEMS("SELECT * FROM `items` WHERE `room_id` = ?"),


    ;

    private final String query;

    RoomQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
