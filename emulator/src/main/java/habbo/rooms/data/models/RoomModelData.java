package habbo.rooms.data.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

public class RoomModelData implements IRoomModelData, IFillable {
    private final Logger logger = LogManager.getLogger();

    private int doorX;
    private int doorY;
    private String name;
    private String heightMap;
    private int doorDirection;

    public RoomModelData(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            logger.error("Failed to fill room model data", e);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public int getDoorX() {
        return doorX;
    }

    public int getDoorY() {
        return doorY;
    }

    public String getName() {
        return name;
    }

    public String getHeightMap() {
        return heightMap;
    }

    public int getDoorDirection() {
        return doorDirection;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.doorX = result.getInt("door_x");
        this.doorY = result.getInt("door_y");
        this.name = result.getString("name");
        this.heightMap = result.getString("heightmap").replaceAll("\r", "");
        this.doorDirection = result.getInt("door_dir");
    }
}
