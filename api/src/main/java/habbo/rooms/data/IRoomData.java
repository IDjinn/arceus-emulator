package habbo.rooms.data;

import habbo.rooms.enums.RoomAccessState;

import java.util.List;

public interface IRoomData {
    int getId();

    String getName();

    String getDescription();

    String getModel();

    int getOwnerId();

    String getOwnerName();

    int getMaxUsers();

    int getScore();

    String getPassword();

    RoomAccessState getAccessState();

    int getGuildId();

    int getCategoryId();

    String getPaperFloor();

    String getPaperWall();

    String getPaperLandscape();

    int getThicknessWall();

    int getWallHeight();

    int getThicknessFloor();

    String getMoodlightData();

    List<String> getTags();

    boolean isPublic();

    boolean isStaffPicked();

    boolean allowPets();

    boolean allowPetsEat();

    boolean allowWalkthrough();

    int getChatMode();

    int getChatWeight();

    int getChatSpeed();

    int getChatDistance();

    int getChatProtection();

    int getWhoCanMute();

    int getWhoCanKick();

    int getWhoCanBan();

    int getPollId();

    int getRollerSpeed();

    boolean isPromoted();

    int getTradeMode();

    boolean canMoveDiagonally();

    boolean hasJukeboxActive();

    boolean isForSale();

    boolean isHideWall();

    boolean isOverrideModel();

    boolean isHideWireds();
}
