package habbo.rooms.components.objects.items;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class RoomItem implements IRoomItem {
    private static final int TickDisabled = -1;
    private final IRoomItemData itemData;
    private final IRoom room;
    private final int virtualId;
    private final IFurniture furniture;
    private @Nullable IHabboData ownerData;
    private boolean needSave;


    public RoomItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        this.itemData = itemData;
        this.room = room;
        this.furniture = furniture;

        this.virtualId = this.getRoom().getObjectManager().getVirtualIdForItem(this);
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
    public int getId() {
        return this.getItemData().getId();
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public Optional<IHabboData> getOwnerData() {
        return Optional.ofNullable(this.ownerData);
    }

    @Override
    public void setOwnerData(IHabboData ownerData) {
        this.ownerData = ownerData;
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
    public void serializeItemIdentity(OutgoingPacket packet) {
        packet
                .appendInt(this.getVirtualId())
                .appendInt(this.getFurniture().getSpriteId());
    }

    @Override
    public IExtraData getExtraData() {
        return this.getItemData().getData();
    }

    @Override
    public boolean needSave() {
        return this.needSave;
    }

    @Override
    public void setNeedSave(final boolean needSave) {
        this.needSave = needSave;
    }

    @Override
    public boolean canUse() {
        return this.getFurniture().getInteractionModesCount() > 0;
    } // TODO USAGE POLICY!
}
