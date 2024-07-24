package queries.habbo;

public enum HabboBadgesQuery {

    GET_ALL_BADGES("""
            SELECT * FROM `users_badges` WHERE `user_id` = ?;
            """),

    GET_EQUIPPED_BADGES("""
            SELECT * FROM `users_badges` WHERE `slot_id` > 0 AND `user_id` = ?;
            """),
    ;
    private final String query;

    HabboBadgesQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
