package habbo.navigator.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.rooms.IRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.IFillable;

import java.util.ArrayList;
import java.util.List;

public class NavigatorPublicCategory implements INavigatorPublicCategory, IFillable {
    private final Logger logger = LogManager.getLogger();

    private int id;
    private String name;
    private List<IRoom> rooms;
    private NavigatorDisplayMode displayMode;
    private int order;

    public NavigatorPublicCategory(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            logger.error("Failed to fill NavigatorPublicCategory", e);
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<IRoom> getRooms() {
        return this.rooms;
    }

    public void addRoom(IRoom room) {
        this.rooms.add(room);
    }

    public void removeRoom(IRoom room) {
        this.rooms.remove(room);
    }

    @Override
    public NavigatorDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.name = result.getString("name");
        this.displayMode = NavigatorDisplayMode.fromType(Integer.parseInt(result.getString("image")));
        this.order = result.getInt("order_num");
        this.rooms = new ArrayList<>();
    }
}
