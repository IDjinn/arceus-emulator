package queries.messenger;

public enum MessengerQuery {
//    SELECT_ALL_FRIENDS_OF_HABBO("""
//           SELECT
//            `users.id`,
//            `users.username`,
//            `users.look`,
//            `users.genter`,
//            `users.online`,
//            `messenger_friendships`.*
//           FROM messenger_friendships
//           INNER JOIN users
//           ON messenger_friendships.user_two_id = users.id WHERE user_one_id = ?
//           """),

    SELECT_ALL_FRIENDS_OF_HABBO("""
            SELECT *  FROM `messenger_friendships`
            WHERE `messenger_friendships`.`user_two_id` = ?
            """),
    ;

    private final String query;

    MessengerQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
