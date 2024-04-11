package queries.navigator;

public enum NavigatorQuery {
    SELECT_ALL_NAVIGATOR_FLAT_CATEGORIES("SELECT * FROM navigator_flatcats"),

    SELECT_ALL_PUBLIC_CATEGORIES("SELECT * FROM navigator_publiccats WHERE visible = '1' ORDER BY order_num DESC"),

    SELECT_ALL_FILTER_TYPES("SELECT * FROM navigator_filter"),
    ;

    private final String query;

    NavigatorQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
