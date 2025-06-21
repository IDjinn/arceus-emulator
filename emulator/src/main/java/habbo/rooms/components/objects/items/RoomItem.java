package habbo.rooms.components.objects.items;

import habbo.GameConstants;
import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.data.IHabboData;
import habbo.rooms.IRoom;
import lombok.Getter;
import lombok.Setter;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Getter
@Setter
public abstract class RoomItem implements IRoomItem {
    private static final int TickDisabled = -1;
    private final IRoomItemData itemData;
    private final IRoom room;
    private final int virtualId;
    private final IFurniture furniture;
    private @Nullable IHabboData ownerData;
    private boolean isNeedSave;


    public RoomItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        this.itemData = itemData;
        this.room = room;
        this.furniture = furniture;

        this.virtualId = this.getId() | GameConstants.FurnitureVirtualIdMask;
    }

    @Override
    public int getId() {
        return this.getItemData().getId();
    }

    @Override
    public Optional<IHabboData> getOwnerData() {
        return Optional.ofNullable(this.ownerData);
    }

    @Override
    public int getGroup() {
        return 0;
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
    public boolean canUse() {
        return this.getFurniture().getInteractionModesCount() > 0;
    } // TODO USAGE POLICY!

    @Override
    public void toggleState(final int state) {
        this.getExtraData().setState(
                this.getExtraData().getState() + 1 < this.getFurniture().getInteractionModesCount()
                        ? this.getExtraData().getState() + 1
                        : 0
        );
        this.setNeedSave(true);
        this.sendUpdate();
    }
}
