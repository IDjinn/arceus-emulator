package queries.habbo;

public enum HabboQuery {
    GET_ID_BY_AUTH_TICKET("SELECT id FROM users WHERE auth_ticket = ? LIMIT 1"),

    GET_ALL_DATA_BY_AUTH_TICKET("""
            SELECT u.id, u.username, u.mail, u.account_created,
            u.last_login, u.last_online, u.motto, u.look, u.gender, u.rank, u.credits,
            u.online, u.auth_ticket, u.ip_register, u.ip_current, u.machine_id, u.home_room,
            us.achievement_score, us.respects_received, us.respects_given, us.daily_pet_respect_points,
            us.daily_respect_points, us.block_following, us.block_friendrequests,
            us.block_roominvites, us.old_chat, us.block_camera_follow, us.guild_id, us.tags,
            us.can_trade, us.club_expire_timestamp, us.login_streak, us.rent_space_id,
            us.rent_space_endtime, us.volume_system, us.volume_furni, us.volume_trax,
            us.chat_color, us.hof_points, us.block_alerts, us.talent_track_citizenship_level, us.talent_track_helpers_level,
            us.ignore_bots, us.ignore_pets, us.nux, us.mute_end_timestamp, us.allow_name_change,
            us.perk_trade, us.forums_post_count, us.ui_flags, us.has_gotten_default_saved_searches,
            us.max_friends, us.max_rooms, us.last_hc_payday, us.hc_gifts_claimed,
            uws.x, uws.y, uws.width, uws.height, uws.open_searches
            FROM users u
            JOIN users_settings us ON us.user_id = u.id
            JOIN user_window_settings uws ON uws.user_id = u.id
            WHERE auth_ticket = ?;
    """),

    GET_ALL_ITEMS_BY_OWNER_ID("""
            SELECT * FROM `items` WHERE `user_id` = ? AND room_id = 0;
            """);

    private final String query;

    HabboQuery(String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
