package queries.habbo;

public enum HabboInventoryQuery {

    GET_ALL_ITEMS_BY_OWNER_ID("""
            SELECT * FROM `items` WHERE `user_id` = ? AND room_id = 0;
            """),

    INSERT_ITEM("""
            INSERT INTO items (user_id, item_id, extra_data, limited_data) VALUES (?, ?, ?, ?);
            """),

    PICKUP_ITEM("""
            INSERT INTO items (id, user_id, room_id, item_id, wall_pos, x, y, z, rot, extra_data, limited_data) VALUES (?,?,?,?,?,?,?,?,?,?,?);
            """);

    private final String query;

    HabboInventoryQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}
