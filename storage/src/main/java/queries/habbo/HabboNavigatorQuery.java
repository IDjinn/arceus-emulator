package queries.habbo;

public enum HabboNavigatorQuery {
    SELECT_ALL_NAVIGATOR_SEARCHES("SELECT * FROM users_saved_searches WHERE user_id = ?"),

    ;

    private final String query;

    HabboNavigatorQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}
