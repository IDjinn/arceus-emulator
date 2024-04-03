package habbo.rooms.components.objects.items;

import utils.Position;

public interface IRoomItemData {
    long getId();

    int getFurnitureId();

    int getOwnerId();

    void setOwnerId(int userId);

    Position getPosition();

    void setPosition(Position position);

    String getWallPosition();

    void setWallPosition(String wallPosition);

    int getRotation();

    void setRotation(int rotation);

    String getData();

    void setData(String data);

    void setData(int data);

    ILimitedData getLimitedEdition();

    void decrementData(int i);

    int getIntData();
}