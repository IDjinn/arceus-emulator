package habbo.habbos.data;

import habbo.habbos.IHabbo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

public class HabboData implements IHabboData, IFillable {
    private final Logger logger = LogManager.getLogger();
    private final IHabbo habbo;

    private int id;
    private String username;
    private String email;
    private String accountCreated;
    private String lastLogin;
    private String lastOnline;
    private String motto;
    private String look;
    private String gender;
    private int rank;
    private int credits;
    private int pixels;
    private int diamonds;
    private int seasonalPoints;
    private boolean isOnline;
    private String authTicket;
    private String registerIp;
    private String currentIp;
    private String machineId;
    private int homeRoom;

    public HabboData(IHabbo habbo, IConnectionResult data) {
        this.habbo = habbo;
        try {
            this.fill(data);
        } catch (Exception e) {
            this.logger.error(STR."Failed to create HabboData from IConnectionResult: \{e.getMessage()}");
        }
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAccountCreated() {
        return this.accountCreated;
    }

    public String getLastLogin() {
        return this.lastLogin;
    }

    public String getLastOnline() {
        return this.lastOnline;
    }

    public String getMotto() {
        return this.motto;
    }

    public String getLook() {
        return this.look;
    }

    public String getGender() {
        return this.gender;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getPixels() {
        return this.pixels;
    }

    public int getDiamonds() {
        return this.diamonds;
    }

    public int getSeasonalPoints() {
        return this.seasonalPoints;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public String getAuthTicket() {
        return this.authTicket;
    }

    public String getRegisterIp() {
        return this.registerIp;
    }

    public String getCurrentIp() {
        return this.currentIp;
    }

    public String getMachineId() {
        return this.machineId;
    }

    public int getHomeRoom() {
        return this.homeRoom;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void destory() {

    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.username = result.getString("username");
        this.email = result.getString("mail");
        this.accountCreated = result.getString("account_created");
        this.lastLogin = result.getString("last_login");
        this.lastOnline = result.getString("last_online");
        this.motto = result.getString("motto");
        this.look = result.getString("look");
        this.gender = result.getString("gender");
        this.rank = result.getInt("rank");
        this.credits = result.getInt("credits");
        this.isOnline = result.getBoolean("online");
        this.authTicket = result.getString("auth_ticket");
        this.registerIp = result.getString("ip_register");
        this.currentIp = result.getString("ip_current");
        this.machineId = result.getString("machine_id");
        this.homeRoom = result.getInt("home_room");
        this.pixels = 0;
        this.diamonds = 0;
        this.seasonalPoints = 0;
    }
}
