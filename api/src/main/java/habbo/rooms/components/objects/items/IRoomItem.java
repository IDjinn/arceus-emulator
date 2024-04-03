package habbo.rooms.components.objects.items;

import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.IRoomObject;

public interface IRoomItem extends IRoomObject {
    public long getId();

    public IRoom getRoom();

    public IHabboData getOwnerData();

    public int getGroup();

    public IRoomItemData getItemData();
}
