package habbo.rooms.components.objects.items.floor;

import habbo.rooms.components.objects.items.IPositionable;
import habbo.rooms.components.objects.items.IRoomItem;

public interface IFloorItem extends IRoomItem, IPositionable {
    public boolean isAtDoor();

    public int getRotation();

    public String getExtraData();

    public void setExtraData(String extraData);
}
