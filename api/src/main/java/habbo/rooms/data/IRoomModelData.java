package habbo.rooms.data;

import org.apache.logging.log4j.Logger;
import utils.pathfinder.Direction;

public interface IRoomModelData {
    int getDoorX();

    int getDoorY();

    String getName();

    String getHeightMap();

    Direction getDoorDirection();

    Logger getLogger();
}
