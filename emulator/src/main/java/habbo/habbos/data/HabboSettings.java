package habbo.habbos.data;

import habbo.habbos.IHabbo;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

import java.util.Optional;

public class HabboSettings implements IHabboSettings, IFillable {
    private static final Logger logger = LogManager.getLogger();
    private final IHabbo habbo;

    @Getter
    private int respectPointsReceived;
    @Getter
    private int achievementScore;
    @Getter
    private int respectPointsGiven;
    @Getter
    private int petRespectPointsToGive;
    @Getter
    private int respectPointsToGive;
    @Getter
    private boolean blockFollowing;
    @Getter
    private boolean blockFriendRequests;
    @Getter
    private boolean blockRoomInvites;
    @Getter
    private boolean preferOldChat;
    @Getter
    private boolean blockCameraFollow;
    @Getter
    private int guild;
    @Getter
    private String[] tags;
    private boolean allowTrade;
    @Getter
    private int clubExpireTimestamp;
    @Getter
    private int loginStreak;
    @Getter
    private int rentedItemId;
    @Getter
    private int rentedTimeEnd;
    @Getter
    private int systemVolume;
    @Getter
    private int furnitureVolume;
    @Getter
    private int traxVolume;
    @Getter
    private int chatColor;
    @Getter
    private int hofPoints;
    @Getter
    private boolean blockStaffAlerts;
    @Getter
    private int citizenshipLevel;
    @Getter
    private int helpersLevel;
    @Getter
    private boolean ignoreBots;
    @Getter
    private boolean ignorePets;
    @Getter
    private boolean nux;
    @Getter
    private int muteEndTime;
    private boolean allowNameChange;
    @Getter
    private boolean perkTrade;
    @Getter
    private int forumPostsCount;
    @Getter
    private int uiFlags;
    @Getter
    private boolean hasGottenDefaultSavedSearches;
    @Getter
    private int maxFriends;
    @Getter
    private int maxRooms;
    @Getter
    private int lastHCPayday;
    @Getter
    private int hcGiftsClaimed;

    public HabboSettings(IHabbo habbo, IConnectionResult result) {
        this.habbo = habbo;
        try {
            this.fill(result);
        } catch (Exception e) {
            logger.error(
                    "Failed to create HabboSettings from IConnectionResult: {}",
                    e.getMessage(),
                    e
            );
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public boolean allowTrade() {
        return this.allowTrade;
    }

    public boolean allowNameChange() {
        return this.allowNameChange;
    }

    @Override
    public Optional<String> getBanner() {
        return Optional.of("https://i.redd.it/8kpckawsj7j11.gif");
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.achievementScore = result.getInt("achievement_score");
        this.respectPointsReceived = result.getInt("respects_received");
        this.respectPointsGiven = result.getInt("respects_given");
        this.petRespectPointsToGive = result.getInt("daily_pet_respect_points");
        this.respectPointsToGive = result.getInt("daily_respect_points");
        this.blockFollowing = result.getBoolean("block_following");
        this.blockFriendRequests = result.getBoolean("block_friendrequests");
        this.blockRoomInvites = result.getBoolean("block_roominvites");
        this.preferOldChat = result.getBoolean("old_chat");
        this.blockCameraFollow = result.getBoolean("block_camera_follow");
        this.guild = result.getInt("guild_id");
        this.tags = result.getString("tags").split(";");
        this.allowTrade = result.getBoolean("can_trade");
        this.clubExpireTimestamp = result.getInt("club_expire_timestamp");
        this.loginStreak = result.getInt("login_streak");
        this.rentedItemId = result.getInt("rent_space_id");
        this.rentedTimeEnd = result.getInt("rent_space_endtime");
        this.systemVolume = result.getInt("volume_system");
        this.furnitureVolume = result.getInt("volume_furni");
        this.traxVolume = result.getInt("volume_trax");
        this.chatColor = result.getInt("chat_color"); // TODO: Implement chat color
        this.hofPoints = result.getInt("hof_points");
        this.blockStaffAlerts = result.getBoolean("block_alerts");
        this.citizenshipLevel = result.getInt("talent_track_citizenship_level");
        this.helpersLevel = result.getInt("talent_track_helpers_level");
        this.ignoreBots = result.getBoolean("ignore_bots");
        this.ignorePets = result.getBoolean("ignore_pets");
        this.nux = result.getBoolean("nux");
        this.muteEndTime = result.getInt("mute_end_timestamp");
        this.allowNameChange = result.getBoolean("allow_name_change");
        this.perkTrade = result.getBoolean("perk_trade");
        this.forumPostsCount = result.getInt("forums_post_count");
        this.uiFlags = result.getInt("ui_flags");
        this.hasGottenDefaultSavedSearches = result.getBoolean("has_gotten_default_saved_searches");
        this.maxFriends = result.getInt("max_friends");
        this.maxRooms = result.getInt("max_rooms");
        this.lastHCPayday = result.getInt("last_hc_payday");
        this.hcGiftsClaimed = result.getInt("hc_gifts_claimed");
    }

    @Override
    public void init() {

    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }
}
