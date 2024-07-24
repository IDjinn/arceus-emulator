package habbo.rooms.components.objects.items;

import habbo.furniture.extra.data.IExtraData;
import utils.pathfinder.Position;

public interface IRoomItemData {
    int getId();

    int getFurnitureId();

    int getOwnerId();

    Position getPosition();

    void setPosition(Position position);

    String getWallPosition();

    void setWallPosition(String wallPosition);

    int getRotation();

    void setRotation(int rotation);

    IExtraData getData();

    void setExtraData(IExtraData data);

    String getWiredData();

    void setWiredData(final String wiredData);
}