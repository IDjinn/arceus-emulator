package queries.habbo;

public enum HabboWalletQuery {

    GET_ALL_CURRENCIES("""
            SELECT * FROM `users_currency` WHERE `user_id` = ?;
            """),

    ;
    private final String query;

    HabboWalletQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
