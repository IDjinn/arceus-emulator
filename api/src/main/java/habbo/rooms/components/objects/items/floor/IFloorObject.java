package habbo.rooms.components.objects.items.floor;

import habbo.rooms.components.objects.items.IPositionable;
import habbo.rooms.components.objects.items.IRoomItem;

public interface IFloorObject extends IRoomItem, IPositionable {
    public boolean isAtDoor();
}
