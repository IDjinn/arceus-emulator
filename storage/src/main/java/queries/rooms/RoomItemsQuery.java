package queries.rooms;

public enum RoomItemsQuery {

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


    FIND_TELEPORT_PAIR(
            """
                    SELECT * FROM `items_teleports`
                    WHERE `teleport_one_id` = ? OR `teleport_two_id` = ?;
                    """),


    UPDATE_ITEM("""
            UPDATE `items`
                SET `room_id` = ?,
                `x` = ?,
                `y` = ?,
                `z` = ?,
                `rot` = ?,
                `wall_pos` = ?,
                `extra_data` = ?,
                `limited_data` = ?,
                `user_id` = ?,
                `guild_id` = ?
            WHERE `id` = ?;
            """),
    ;

    private final String query;

    RoomItemsQuery(final String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
