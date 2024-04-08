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
                    """),
    PLACE_WALL_ITEM(
            """
                    UPDATE `items`
                        SET `room_id` = ?,
                        `wall_pos` = ?
                    WHERE `id` = ?;
                    """),
    SELECT_ALL_PUBLIC_ROOMS("SELECT * FROM rooms WHERE is_public = ? OR is_staff_picked = ? ORDER BY id DESC"),

    SELECT_ALL_STAFF_PICKED_ROOMS("SELECT * FROM navigator_publics JOIN rooms ON rooms.id = navigator_publics.room_id WHERE visible = ?"),

    SELECT_ALL_ROOMS_CATEGORIES("SELECT * FROM navigator_flatcats"),

    SELECT_ALL_ROOM_MODELS("SELECT * FROM room_models"),

    ;

    private final String query;

    RoomQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
