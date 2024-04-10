package queries.habbo;

public enum HabboInventoryQuery {

    GET_ALL_ITEMS_BY_OWNER_ID("""
            SELECT * FROM `items` WHERE `user_id` = ? AND room_id = 0;
            """),

    INSERT_ITEM("""
            INSERT INTO items (user_id, item_id, extra_data, limited_data) VALUES (?, ?, ?, ?);
            """),

    PICKUP_ITEM("""
            UPDATE items SET room_id = 0, user_id = ? WHERE id = ?;
            """);

    private final String query;

    HabboInventoryQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}
