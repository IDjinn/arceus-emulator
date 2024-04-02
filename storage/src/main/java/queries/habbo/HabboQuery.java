package queries.habbo;

public enum HabboQuery {
    GET_ALL_DATA_BY_AUTH_TICKET("SELECT * FROM users WHERE auth_ticket = ? LIMIT 1"),
    GET_ID_BY_AUTH_TICKET("SELECT id FROM users WHERE auth_ticket = ? LIMIT 1");

    private final String query;

    HabboQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}
