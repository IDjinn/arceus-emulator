package habbo.rooms.data;

import habbo.rooms.enums.RoomAccessState;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

import java.util.Arrays;
import java.util.List;

@Getter
public class RoomData implements IRoomData, IFillable {

    private final Logger logger = LogManager.getLogger();
    private int id;
    private String name;
    private String description;
    private String modelName;
    private int ownerId;
    private String ownerName;
    private int maxUsers;
    private int score;
    private String password;
    private RoomAccessState accessState;
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
    @Setter
    private PathfinderMode pathfinderMode;


    public RoomData(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            this.logger.error("Failed to fill room data", e);
        }
    }

    public boolean getCanMoveDiagonally() {
        return this.canMoveDiagonally;
    }

    public boolean hasJukeboxActive() {
        return this.hasJukeboxActive;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.description = result.getString("description");
        this.modelName = result.getString("model");
        this.ownerId = result.getInt("owner_id");
        this.ownerName = result.getString("owner_name");
        this.maxUsers = result.getInt("users_max");
        this.guildId = result.getInt("guild_id");
        this.score = result.getInt("score");
        this.password = result.getString("password");
        this.accessState = RoomAccessState.fromValue(result.getString("state"));
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

        this.pathfinderMode = PathfinderMode.New3dPathfinder;
    }
}
