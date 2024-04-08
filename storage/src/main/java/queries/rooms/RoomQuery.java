package queries.rooms;

public enum RoomQuery {

    SELECT_ALL_ITEMS("SELECT * FROM `items` WHERE `room_id` = ?"),

    PLACE_FLOOR_ITEM(
            """
                    UPDATE `items`
                        SET `room_id` = ?,
                        `x` = ?,
                        `y` = ?,
                        `z` = ?,
                        `rot` = ?
                    WHERE `id` = ?;
                    """)
    ;

    private final String query;

    RoomQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
