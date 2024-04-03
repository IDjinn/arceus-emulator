package habbo.rooms.components.objects.items.wall;

import habbo.rooms.components.objects.items.IRoomItem;

public interface IWallItem extends IRoomItem {

    public void setWallPosition(String position);
    public String getWallPosition();
}
