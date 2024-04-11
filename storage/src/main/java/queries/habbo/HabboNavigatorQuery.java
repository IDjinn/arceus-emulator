package queries.habbo;

public enum HabboNavigatorQuery {
    SELECT_ALL_NAVIGATOR_SEARCHES("SELECT * FROM users_saved_searches WHERE user_id = ?"),

    SELECT_ALL_NAVIGATOR_CATEGORIES_SETTINGS("SELECT * FROM users_navigator_settings WHERE user_id = ?"),

    ;

    private final String query;

    HabboNavigatorQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
