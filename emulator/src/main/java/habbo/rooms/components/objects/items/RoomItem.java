package habbo.rooms.components.objects.items;

import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;

public class RoomItem implements IRoomItem {
    private final IRoomItemData itemData;
    private final IRoom room;
    private final long virtualId;

    public RoomItem(IRoomItemData itemData, IRoom room) {
        this.itemData = itemData;
        this.room = room;

        this.virtualId = this.getRoom().getObjectManager().getVirtualIdForItemId(this.getId());
    }

    @Override
    public long getVirtualId() {
        return virtualId;
    }

    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public long getId() {
        return this.getItemData().getId();
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public IHabboData getOwnerData() {
        return null;
    }

    @Override
    public int getGroup() {
        return 0;
    }

    @Override
    public IRoomItemData getItemData() {
        return this.itemData;
    }
}
