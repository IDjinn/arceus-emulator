package habbo.rooms.data;

import habbo.rooms.enums.RoomAccessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.IFillable;

import java.util.Arrays;
import java.util.List;

public class RoomData implements IRoomData, IFillable {
    private final Logger logger = LogManager.getLogger();

    private int id;
    private String name;
    private String description;
    private String model;
    private int ownerId;
    private String ownerName;
    private int maxUsers;
    private int score;
    private String password;
    private RoomAccessState state;
    private int guildId;
    private int categoryId;
    //private INavigatorCategory category; TODO
    private String paperFloor;
    private String paperWall;
    private String paperLandscape;
    private int thicknessWall;
    private int wallHeight;
    private int thicknessFloor;
    private String moodlightData;
    private List<String> tags;
    private boolean isPublic;
    private boolean isStaffPicked;
    private boolean allowPets;
    private boolean allowPetsEat;
    private boolean allowWalkthrough;
    private boolean hideWall;
    private int chatMode;
    private int chatWeight;
    private int chatSpeed;
    private int chatDistance;
    private int chatProtection;
    private boolean overrideModel;
    private int whoCanMute;
    private int whoCanKick;
    private int whoCanBan;
    private int pollId;
    private int rollerSpeed;
    private boolean isPromoted;
    private int tradeMode;
    private boolean canMoveDiagonally;
    private boolean hasJukeboxActive;
    private boolean hideWireds;
    private boolean isForSale;

    public RoomData(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            logger.error("Failed to fill room data", e);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModelName() {
        return model;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public int getScore() {
        return score;
    }

    public String getPassword() {
        return password;
    }

    public RoomAccessState getAccessState() {
        return state;
    }

    public int getGuildId() {
        return guildId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getPaperFloor() {
        return paperFloor;
    }

    public String getPaperWall() {
        return paperWall;
    }

    public String getPaperLandscape() {
        return paperLandscape;
    }

    public int getThicknessWall() {
        return thicknessWall;
    }

    public int getWallHeight() {
        return wallHeight;
    }

    public int getThicknessFloor() {
        return thicknessFloor;
    }

    public String getMoodlightData() {
        return moodlightData;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean isStaffPicked() {
        return isStaffPicked;
    }

    public boolean allowPets() {
        return allowPets;
    }

    public boolean allowPetsEat() {
        return allowPetsEat;
    }

    public boolean allowWalkthrough() {
        return allowWalkthrough;
    }

    public boolean isHideWall() {
        return hideWall;
    }

    public int getChatMode() {
        return chatMode;
    }

    public int getChatWeight() {
        return chatWeight;
    }

    public int getChatSpeed() {
        return chatSpeed;
    }

    public int getChatDistance() {
        return chatDistance;
    }

    public int getChatProtection() {
        return chatProtection;
    }

    public boolean isOverrideModel() {
        return overrideModel;
    }

    public int getWhoCanMute() {
        return whoCanMute;
    }

    public int getWhoCanKick() {
        return whoCanKick;
    }

    public int getWhoCanBan() {
        return whoCanBan;
    }

    public int getPollId() {
        return pollId;
    }

    public int getRollerSpeed() {
        return rollerSpeed;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public int getTradeMode() {
        return tradeMode;
    }

    public boolean canMoveDiagonally() {
        return canMoveDiagonally;
    }

    public boolean hasJukeboxActive() {
        return hasJukeboxActive;
    }

    public boolean isHideWireds() {
        return hideWireds;
    }

    public boolean isForSale() {
        return isForSale;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.description = result.getString("description");
        this.model = result.getString("model");
        this.ownerId = result.getInt("owner_id");
        this.ownerName = result.getString("owner_name");
        this.maxUsers = result.getInt("users_max");
        this.guildId = result.getInt("guild_id");
        this.score = result.getInt("score");
        this.password = result.getString("password");
        this.state = RoomAccessState.fromValue(result.getString("state"));
        this.categoryId = result.getInt("category");
        this.paperFloor = result.getString("paper_floor");
        this.paperWall = result.getString("paper_wall");
        this.paperLandscape = result.getString("paper_landscape");
        this.thicknessWall = result.getInt("thickness_wall");
        this.wallHeight = result.getInt("wall_height");
        this.thicknessFloor = result.getInt("thickness_floor");
        this.moodlightData = result.getString("moodlight_data");
        this.tags = Arrays.asList(result.getString("tags").split(";"));
        this.isPublic = result.getBoolean("is_public");
        this.isStaffPicked = result.getBoolean("is_staff_picked");
        this.allowPets = result.getBoolean("allow_other_pets");
        this.allowPetsEat = result.getBoolean("allow_other_pets_eat");
        this.allowWalkthrough = result.getBoolean("allow_walkthrough");
        this.hideWall = result.getBoolean("allow_hidewall");
        this.chatMode = result.getInt("chat_mode");
        this.chatWeight = result.getInt("chat_weight");
        this.chatSpeed = result.getInt("chat_speed");
        this.chatDistance = result.getInt("chat_hearing_distance");
        this.chatProtection = result.getInt("chat_protection");
        this.overrideModel = result.getBoolean("override_model");
        this.whoCanMute = result.getInt("who_can_mute");
        this.whoCanKick = result.getInt("who_can_kick");
        this.whoCanBan = result.getInt("who_can_ban");
        this.pollId = result.getInt("poll_id");
        this.rollerSpeed = result.getInt("roller_speed");
        this.isPromoted = result.getBoolean("promoted");
        this.tradeMode = result.getInt("trade_mode");
        this.canMoveDiagonally = result.getBoolean("move_diagonally");
        this.hasJukeboxActive = result.getBoolean("jukebox_active");
        this.hideWireds = result.getBoolean("hidewired");
        this.isForSale = result.getBoolean("is_forsale");
    }
}
