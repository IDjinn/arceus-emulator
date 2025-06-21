package habbo.habbos.data;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.repositories.habbo.IHabboRepository;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

import java.util.Locale;

public class HabboData implements IHabboData, IFillable {
    @Getter
    private final Logger logger = LogManager.getLogger();
    private final IHabbo habbo;
    @Inject
    private IHabboRepository habboRepository;

    private int id;
    @Getter
    private String username;
    @Getter
    private String email;
    @Getter
    private String accountCreated;
    @Getter
    private String lastLogin;
    @Getter
    private String lastOnline;
    @Getter
    private String motto;
    @Getter
    private String look;
    @Getter
    private String gender;
    private int rank;
    @Getter
    private int credits;
    @Getter
    private int pixels;
    @Getter
    private int diamonds;
    @Getter
    private int seasonalPoints;
    @Getter
    private boolean isOnline;
    @Getter
    private String authTicket;
    @Getter
    private String registerIp;
    @Getter
    private String currentIp;
    @Getter
    private String machineId;
    @Getter
    private int homeRoom;

    public HabboData(IHabbo habbo, IConnectionResult data) {
        this.habbo = habbo;
        try {
            this.fill(data);
        } catch (Exception e) {
            logger.error(
                    "Failed to create HabboData from IConnectionResult: {}",
                    e.getMessage(),
                    e
            );
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public Locale getLocale() {
        return Locale.ENGLISH; // TODO THIS
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        this.habboRepository.saveHabboData(result -> {
            if (!result)
                this.logger.warn("habbo data {} was not successfully saved into database", this.getId());
        }, this);
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
