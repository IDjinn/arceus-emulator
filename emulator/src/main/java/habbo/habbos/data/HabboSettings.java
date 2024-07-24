package habbo.habbos.data;

import habbo.habbos.IHabbo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

import java.util.Optional;

public class HabboSettings implements IHabboSettings, IFillable {
    private static final Logger logger = LogManager.getLogger();
    private final IHabbo habbo;

    private int respectPointsReceived;
    private int achievementScore;
    private int respectPointsGiven;
    private int petRespectPointsToGive;
    private int respectPointsToGive;
    private boolean blockFollowing;
    private boolean blockFriendRequests;
    private boolean blockRoomInvites;
    private boolean preferOldChat;
    private boolean blockCameraFollow;
    private int guild;
    private String[] tags;
    private boolean allowTrade;
    private int clubExpireTimestamp;
    private int loginStreak;
    private int rentedItemId;
    private int rentedTimeEnd;
    private int systemVolume;
    private int furnitureVolume;
    private int traxVolume;
    private int chatColor;
    private int hofPoints;
    private boolean blockStaffAlerts;
    private int citizenshipLevel;
    private int helpersLevel;
    private boolean ignoreBots;
    private boolean ignorePets;
    private boolean nux;
    private int muteEndTime;
    private boolean allowNameChange;
    private boolean perkTrade;
    private int forumPostsCount;
    private int uiFlags;
    private boolean hasGottenDefaultSavedSearches;
    private int maxFriends;
    private int maxRooms;
    private int lastHCPayday;
    private int hcGiftsClaimed;

    public HabboSettings(IHabbo habbo, IConnectionResult result) {
        this.habbo = habbo;
        try {
            this.fill(result);
        } catch (Exception e) {
            logger.error(STR."Failed to create HabboSettings from IConnectionResult: \{e.getMessage()}");
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public int getRespectPointsReceived() {
        return this.respectPointsReceived;
    }

    public int getAchievementScore() {
        return this.achievementScore;
    }

    public int getRespectPointsGiven() {
        return this.respectPointsGiven;
    }

    public int getPetRespectPointsToGive() {
        return this.petRespectPointsToGive;
    }

    public int getRespectPointsToGive() {
        return this.respectPointsToGive;
    }

    public boolean isBlockFollowing() {
        return this.blockFollowing;
    }

    public boolean isBlockFriendRequests() {
        return this.blockFriendRequests;
    }

    public boolean isBlockRoomInvites() {
        return this.blockRoomInvites;
    }

    public boolean isPreferOldChat() {
        return this.preferOldChat;
    }

    public boolean isBlockCameraFollow() {
        return this.blockCameraFollow;
    }

    public int getGuild() {
        return this.guild;
    }

    public String[] getTags() {
        return this.tags;
    }

    public boolean allowTrade() {
        return this.allowTrade;
    }

    public int getClubExpireTimestamp() {
        return this.clubExpireTimestamp;
    }

    public int getLoginStreak() {
        return this.loginStreak;
    }

    public int getRentedItemId() {
        return this.rentedItemId;
    }

    public int getRentedTimeEnd() {
        return this.rentedTimeEnd;
    }

    public int getSystemVolume() {
        return this.systemVolume;
    }

    public int getFurnitureVolume() {
        return this.furnitureVolume;
    }

    public int getTraxVolume() {
        return this.traxVolume;
    }

    public int getChatColor() {
        return this.chatColor;
    }

    public int getHofPoints() {
        return this.hofPoints;
    }

    public boolean isBlockStaffAlerts() {
        return this.blockStaffAlerts;
    }

    public int getCitizenshipLevel() {
        return this.citizenshipLevel;
    }

    public int getHelpersLevel() {
        return this.helpersLevel;
    }

    public boolean isIgnoreBots() {
        return this.ignoreBots;
    }

    public boolean isIgnorePets() {
        return this.ignorePets;
    }

    public boolean isNux() {
        return this.nux;
    }

    public int getMuteEndTime() {
        return this.muteEndTime;
    }

    public boolean allowNameChange() {
        return this.allowNameChange;
    }

    public boolean isPerkTrade() {
        return this.perkTrade;
    }

    public int getForumPostsCount() {
        return this.forumPostsCount;
    }

    public int getUiFlags() {
        return this.uiFlags;
    }

    public boolean isHasGottenDefaultSavedSearches() {
        return this.hasGottenDefaultSavedSearches;
    }

    public int getMaxFriends() {
        return this.maxFriends;
    }

    public int getMaxRooms() {
        return this.maxRooms;
    }

    public int getLastHCPayday() {
        return this.lastHCPayday;
    }

    public int getHcGiftsClaimed() {
        return this.hcGiftsClaimed;
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
