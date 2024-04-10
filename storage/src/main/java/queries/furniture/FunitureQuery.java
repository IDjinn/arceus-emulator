package queries.furniture;

public enum FunitureQuery {
    SELECT_ALL_FURNITURE("SELECT * FROM `items_base`"),

    CREATE_TELEPORT_LINK("REPLACE INTO `items_teleports` VALUES (?, ?)")
    ;
    
    private final String query;


    FunitureQuery(String query) {
        this.query = query;
    }
    
    public String get() {
        return query;
    }
}
