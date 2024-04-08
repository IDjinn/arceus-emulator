package habbo.rooms.data.models;

import org.apache.logging.log4j.Logger;

public interface IRoomModelData {
    int getDoorX();

    int getDoorY();

    String getName();

    String getHeightMap();

    int getDoorDirection();

    Logger getLogger();
}
