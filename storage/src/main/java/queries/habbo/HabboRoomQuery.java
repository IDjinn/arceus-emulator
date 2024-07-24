package queries.habbo;

public enum HabboRoomQuery {
    SELECT_ALL_ROOMS("SELECT * FROM rooms WHERE owner_id = ?"),

    SELECT_ALL_FAVORITE_ROOMS("SELECT * FROM rooms WHERE id IN (SELECT room_id FROM users_favorite_rooms WHERE user_id = ?)"),

    SELECT_ALL_ROOM_HISTORY("SELECT rooms.* FROM room_enter_log INNER JOIN rooms ON room_enter_log.room_id = rooms.id WHERE user_id = ? AND timestamp >= ? AND rooms.owner_id != ? GROUP BY rooms.id ORDER BY timestamp DESC LIMIT 10"),

    SELECT_ALL_ROOMS_WITH_RIGHTS("SELECT rooms.* FROM rooms INNER JOIN room_rights ON room_rights.room_id = rooms.id WHERE room_rights.user_id = ? ORDER BY rooms.id DESC LIMIT 20"),

    ;

    private final String query;

    HabboRoomQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
