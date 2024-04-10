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

    void setData(IExtraData data);

    int getIntData();
}