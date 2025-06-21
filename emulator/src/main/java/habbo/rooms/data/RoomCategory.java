package habbo.rooms.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

@Getter
public class RoomCategory implements IRoomCategory, IFillable, Comparable<IRoomCategory> {
    private final Logger logger = LogManager.getLogger();

    private int id;
    private int minRank;
    private String caption;
    private String captionSave;
    private boolean canTrade;
    private int maxUserCount;
    private boolean isPublic;
    private NavigatorDisplayMode displayMode;
    private int order;

    public RoomCategory(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            this.logger.error("Failed to fill room category", e);
        }
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.minRank = result.getInt("min_rank");
        this.caption = result.getString("caption");
        this.captionSave = result.getString("caption_save");
        this.canTrade = result.getBoolean("can_trade");
        this.maxUserCount = result.getInt("max_user_count");
        this.isPublic = result.getString("public").equals("1");
        this.displayMode = NavigatorDisplayMode.fromType(result.getInt("list_type"));
        this.order = result.getInt("order_num");
    }

    @Override
    public int compareTo(@NotNull IRoomCategory roomCategory) {
        return roomCategory.getId() - this.getId();
    }
}
