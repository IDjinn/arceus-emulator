package queries.habbo;

public enum HabboSettingsQuery {
    INSERT_DEFAULT_NAVIGATOR_WINDOW_SETTINGS("INSERT INTO `user_window_settings` (`user_id`, `x`, `y`, `width`, `height`, `open_searches`) VALUES (?, ?, ?, ?, ?, ?);"),

    ;

    private final String query;

    HabboSettingsQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
