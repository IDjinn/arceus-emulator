package queries.habbo;

public enum HabboRoomQuery {
    SELECT_ALL_ROOMS("SELECT * FROM rooms WHERE owner_id = ?"),

    SELECT_ALL_FAVORITE_ROOMS("SELECT * FROM rooms WHERE id IN (SELECT room_id FROM users_favorite_rooms WHERE user_id = ?)"),

    ;

    private final String query;

    HabboRoomQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
