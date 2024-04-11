package habbo.rooms.components.objects.items.wall;

import habbo.rooms.components.objects.items.IRoomItem;

public interface IWallItem extends IRoomItem {

    String getWallPosition();

    void setWallPosition(String position);
}
