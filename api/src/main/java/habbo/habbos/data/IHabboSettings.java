package habbo.habbos.data;

import habbo.habbos.IHabboComponent;
import org.apache.logging.log4j.Logger;

public interface IHabboSettings extends IHabboComponent {
    Logger getLogger();

    int getRespectPointsReceived();

    int getAchievementScore();

    int getRespectPointsGiven();

    int getPetRespectPointsToGive();

    int getRespectPointsToGive();

    boolean isBlockFollowing();

    boolean isBlockFriendRequests();

    boolean isBlockRoomInvites();

    boolean isPreferOldChat();

    boolean isBlockCameraFollow();

    int getGuild();

    String[] getTags();

    boolean allowTrade();

    int getClubExpireTimestamp();

    int getLoginStreak();

    int getRentedItemId();

    int getRentedTimeEnd();

    int getSystemVolume();

    int getFurnitureVolume();

    int getTraxVolume();

    int getChatColor();

    int getHofPoints();

    boolean isBlockStaffAlerts();

    int getCitizenshipLevel();

    int getHelpersLevel();

    boolean isIgnoreBots();

    boolean isIgnorePets();

    boolean isNux();

    int getMuteEndTime();

    boolean allowNameChange();

    boolean isPerkTrade();

    int getForumPostsCount();

    int getUiFlags();

    boolean isHasGottenDefaultSavedSearches();

    int getMaxFriends();

    int getMaxRooms();

    int getLastHCPayday();

    int getHcGiftsClaimed();
}
