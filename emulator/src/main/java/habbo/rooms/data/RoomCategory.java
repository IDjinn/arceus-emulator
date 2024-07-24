package habbo.rooms.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

public class RoomCategory implements IRoomCategory, IFillable, Comparable<IRoomCategory> {
    private final Logger logger = LogManager.getLogger();

    private int id;
    private int minRank;
    private String caption;
    private String captionSave;
    private boolean canTrade;
    private int maxUserCount;
    private boolean official;
    private NavigatorDisplayMode displayMode;
    private int order;

    public RoomCategory(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            this.logger.error("Failed to fill room category", e);
        }
    }

    public int getId() {
        return this.id;
    }

    public int getMinRank() {
        return this.minRank;
    }

    public String getCaption() {
        return this.caption;
    }

    public String getCaptionSave() {
        return this.captionSave;
    }

    public boolean isCanTrade() {
        return this.canTrade;
    }

    public int getMaxUserCount() {
        return this.maxUserCount;
    }

    public boolean isPublic() {
        return this.official;
    }

    public NavigatorDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public int getOrder() {
        return this.order;
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.minRank = result.getInt("min_rank");
        this.caption = result.getString("caption");
        this.captionSave = result.getString("caption_save");
        this.canTrade = result.getBoolean("can_trade");
        this.maxUserCount = result.getInt("max_user_count");
        this.official = result.getString("public").equals("1");
        this.displayMode = NavigatorDisplayMode.fromType(result.getInt("list_type"));
        this.order = result.getInt("order_num");
    }

    @Override
    public int compareTo(@NotNull IRoomCategory roomCategory) {
        return roomCategory.getId() - this.getId();
    }
}
