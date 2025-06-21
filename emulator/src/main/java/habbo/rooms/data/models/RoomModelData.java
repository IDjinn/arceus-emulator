package habbo.rooms.data.models;

import habbo.rooms.data.IRoomModelData;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;
import utils.pathfinder.Direction;

@Getter
public class RoomModelData implements IRoomModelData, IFillable {
    private final Logger logger = LogManager.getLogger();

    private int doorX;
    private int doorY;
    private String name;
    private String heightMap;
    private Direction doorDirection;

    public RoomModelData(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            this.logger.error("Failed to fill room model data", e);
        }
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.doorX = result.getInt("door_x");
        this.doorY = result.getInt("door_y");
        this.name = result.getString("name");
        this.heightMap = result.getString("heightmap").replaceAll("\r", "");
        this.doorDirection = Direction.get(result.getInt("door_dir"));
    }
}
