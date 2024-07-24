package habbo.rooms.components.objects.items.wall;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.RoomItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.rooms.objects.wall.WallItemUpdateComposer;

public class DefaultWallItem extends RoomItem implements IWallItem {
    public static final String INTERACTION_NAME = "default_wall";

    public DefaultWallItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
    }

    @Override
    public void serializeItemIdentity(OutgoingPacket<U> packet) {
        packet
                .appendString(String.valueOf(this.getVirtualId()))
                .appendInt(this.getFurniture().getSpriteId());
    }

    @Override
    public OutgoingPacket<U> serializePosition(OutgoingPacket<U> packet) {
        return packet.appendString(this.getWallPosition());
    }

    @Override
    public void sendUpdate() {
        this.getRoom().broadcastMessage(new WallItemUpdateComposer(this));
        super.setNeedSave(true);
    }

    @Override
    public String getWallPosition() {
        return this.getItemData().getWallPosition();
    }

    @Override
    public void setWallPosition(String position) {
        this.getItemData().setWallPosition(position);
    }
}
