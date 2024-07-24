package queries.catalog;

public enum CatalogQuery {

    SELECT_ALL_PAGES("SELECT * FROM `catalog_pages`"),
    SELECT_ALL_ITEMS("SELECT * FROM `catalog_items`"),


    ;

    private final String query;

    CatalogQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
