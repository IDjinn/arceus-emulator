package habbo.rooms.components.objects.items;

import habbo.furniture.IFurniture;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import networking.packets.OutgoingPacket;

public abstract class RoomItem implements IRoomItem {
    private final IRoomItemData itemData;
    private final IRoom room;
    private final int virtualId;
    private final IFurniture furniture;

    public RoomItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        this.itemData = itemData;
        this.room = room;
        this.furniture = furniture;

        this.virtualId = this.getRoom().getObjectManager().getVirtualIdForItemId(this.getId());
    }

    @Override
    public int getVirtualId() {
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

    @Override
    public IFurniture getFurniture() {
        return this.furniture;
    }

    @Override
    public ILimitedData getLimitedData() {
        return this.getItemData().getLimitedEdition();
    }

    @Override
    public boolean isLimited() {
        return this.getLimitedData().getLimitedRareTotal() > 0;
    }

    @Override
    public void serialize(OutgoingPacket packet) {
        packet
                .appendInt(this.getVirtualId())
                .appendInt(this.getFurniture().getSpriteId());
    }
}
